import os
import json
import tensorflow as tf
import tensorflow_hub as hub
import numpy as np
import shutil
import time
import pandas as pd

from flask import Flask, request, jsonify
#from workzeug.utils import secure_filename

app = Flask(__name__)
app.config['UPLOAD_FOLDER']= "images"

#tf.keras.models.load_model(
    # "paddy_lea_disease_detection_model.h5", 
    #filepath,
    #custom_objects={'KerasLayer':hub.KerasLayer},
    #compile = True,
    #options = None
    #)
model = tf.keras.models.load_model("../../../ML/model_v4.h5", custom_objects={'KerasLayer':hub.KerasLayer})

#test
@app.route("/")
def hello():
    return 'HelloWorld'

#ML Model Predict 
@app.route("/predict", methods= ["POST"])
def predict():
    #Make Temporary Directory
    shutil.rmtree('images')
    os.makedirs('images')

    #Get Image Request
    get_img=request.files['images']
    filepath= os.path.join(app.config['UPLOAD_FOLDER'], get_img.filename)
    get_img.save(filepath)

    #Path the Image
    fname= "images/{}".format(os.listdir('images/')[0])

    #Read Label Model
    df = pd.read_csv("model/label.csv", sep = ";")
    def return_label(array):
        largest = 0
        for x in range(0, len(array)):
            if(array[x] > largest):
                largest = array[x]
                y=x
                return y
    #read the image
    img_size = (244, 244)
    dummy = image.load_img(fname, target_size - img_size)
    dummy = image.img_to_array(dummy)
    dummy = np.expand_dims(dummy, axis = 0)
    result = model.predict(dummy)
    label = return_label(result[0])
    if label == 0:
        id = int(str(time.time()).replace('.', '')[3:13])
        label_name = 'Blight'
        description = df.loc[df["Label"] == label_name]["Description"][0]
        solution = df.loc[df["Label"] == label_name]["Solution"][0]
    elif label == 1:
        id = int(str(time.time()).replace('.', '')[3:13])
        label_name = 'Common Rust'
        description = df.loc[df["Label"] == label_name]["Description"][1]
        solution = df.loc[df["Label"] == label_name]["Solution"][1]
    elif label == 3:
        id = int(str(time.time()).replace('.', '')[3:13])
        label_name = 'Gray Leaf Spot'
        description = df.loc[df["Label"] == label_name]["Description"][2]
        solution = df.loc[df["Label"] == label_name]["Solution"][2]
    elif label == 3:
        id = int(str(time.time()).replace('.', '')[3:13])
        label_name = 'Healthy'
        description = df.loc[df["Label"] == label_name]["Description"][3]
        solution = df.loc[df["Label"] == label_name]["Solution"][3]
    os.os.remove("images/{}".format(get_img.filename))
    return jsonify(id=id, label=label_name, description=description, solution= solution)
        

# Function for Paddy Dictionary
@app.route("/dictionary")
def dictionary():
    f = open('label.json')
    data = json.load(f)
    return data

if __name__ == '__main__':
    app.run(debug=True)