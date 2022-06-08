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

tf.keras.models.load_model(
    # "paddy_lea_disease_detection_model.h5", 
    filepath,
    custom_objects={'KerasLayer':hub.KerasLayer},
    compile = True,
    options = None
    )
#model = tf.keras.model.load_model("simple_model.h5")

#server test functions
@app.route("/")
def hello():
    return 'HelloWorld'

#machine Learning Predict and
@app.route("/predict", methods= ["POST"])
def predict():
    shutil.rmtree('images')
    os.makedirs('images')
    upload_image=request.files['images']
    filepath= os.path.join(app.config['UPLOAD_FOLDER'], upload_image.filename)
    upload_image.save(filepath)

    #path ke gambar + nama filenya
    fname= "images/{}".format(os.listdir('images/')[0])
#
    df = pd.read_csv("model/label.csv", sep = ";")
    def return_label(array):
        largest = 0
        for x in range(0, len(array)):
            if(array[x] > largest):
                largest = array[x]
                y=x
                return y
    #read the image
    image_size = (244, 244)
    test_image = image.load_img(fname, target_size - image_size)
    test_image = image.img_to_array(test_image)
    test_image = np.expand_dims(test_image, axis = 0)
    result = model.predict(test_image)
    label = return_label(result[0])
    if label == 0:
        id = int(str(time.time()).replace('.', '')[3:13])
        label_name = 'Brown Spot'
        description = df.loc[df["Label"] == label_name]["Description"][0]
        solution = df.loc[df["Label"] == label_name]["Solution"][0]
    elif label == 1:
        id = int(str(time.time()).replace('.', '')[3:13])
        label_name = 'Healthy'
        description = df.loc[df["Label"] == label_name]["Description"][1]
        solution = df.loc[df["Label"] == label_name]["Solution"][1]
    elif label == 3:
        id = int(str(time.time()).replace('.', '')[3:13])
        label_name = 'Hispa'
        description = df.loc[df["Label"] == label_name]["Description"][2]
        solution = df.loc[df["Label"] == label_name]["Solution"][2]
    elif label == 3:
        id = int(str(time.time()).replace('.', '')[3:13])
        label_name = 'Leaf Blast'
        description = df.loc[df["Label"] == label_name]["Description"][3]
        solution = df.loc[df["Label"] == label_name]["Solution"][3]
    os.os.remove("images/{}".format(upload_image.filename))
    return jsonify(id=id, label=label_name, description=description, solution= solution)
        

# Function for Paddy Dictionary
@app.route("/dictionary")
def dictionary():
    f = open('label.json')
    data = json.load(f)
    return data

if _name__ == '__main__':
    app.run(host='0.0.0.0', port=80)