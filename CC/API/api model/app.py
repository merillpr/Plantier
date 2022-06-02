from crypt import methods
import os
import json
import tensorflow as tf
import tensorflow_hub as hub
import numpy as np
import tensorflow.keras as keras
import shutil
import time
import pandas as pd

from tensorflow.keras.preprocessing import image
from flask import Flask, request, render_template, jsonify
#from workzeug.utils import secure_filename

app = Flask(__name__)
app.config['UPLOAD_FOLDER']= "images"

model = tf.keras.models.load_model("paddy_lea_disease_detection_model.h5", custom_object={'KerasLayer':hub.KerasLayer})
#model = tf.keras.model.load_model("simple_model.h5")

#server test functions
@app.route("/")
def hello():
    return 'HelloWorld'

#machine Learning Predict and
@app.route("/predict", methods= ["GET", "POST"])
def predict():
    if request.method == 'POST':
        shutil.rmtree('images')
        os.makedirs('images')
        upload_image=request.files['images']
        filepath= os.path.join(app.config['UPLOAD_FOLDER'], upload_image.filename)
        upload_image.save(filepath)