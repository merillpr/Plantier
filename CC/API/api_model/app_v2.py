import os
import json
import tensorflow as tf
import tensorflow_hub as hub
import numpy as np
import shutil
import time
import pandas as pd

from keras.utils import load_img, img_to_array
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
Model = tf.keras.models.load_model("../../../ML/model_v4-2.h5", custom_objects={'KerasLayer':hub.KerasLayer})

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
    df = pd.read_csv("../../../ML/description_v2.csv", sep = ";")
    
    #read the image
    img_size = (224, 224)
    dummy = load_img(fname, target_size = img_size)
    dummy = img_to_array(dummy)
    dummy = np.expand_dims(dummy, axis = 0)
    result = Model.predict(dummy)
    label = np.argmax(result[0])
    id = str(time.time()).replace('.', '')[3:13]
    if label == 0:
        label_name = 'Common rust'
    elif label == 1:
        label_name = 'bight'
    elif label == 2:
        label_name = 'Gray leaf spot'
    elif label == 3:
        label_name = 'corn healthy'
    description = df.loc[df["disease_name"] == label_name, 'description']
    solution = df.loc[df["disease_name"] == label_name, 'resolve,,,,,,,,,']
    os.remove("images/{}".format(get_img.filename))
    #return jsonify(id=id, label=label_name, description=description, solution= solution)
    return jsonify({
            'message': "prediction success",
            'user': {
                'result1' : str(result[0][0]),
                'result2' : str(result[0][1]),
                'result3' : str(result[0][2]),
                'result4' : str(result[0][3]),
                'label' : str(label),
                'id': id,
                'name' : label_name,
                'description' : description.to_json(),
                'solution' : solution.to_json()
                
            }

        })
        

# Function for Paddy Dictionary
@app.route("/dictionary")
def dictionary():
    f = open('label.json')
    data = json.load(f)
    return data

if __name__ == '__main__':
    app.run(debug=True)