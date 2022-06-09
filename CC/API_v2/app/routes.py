import os
import shutil
import numpy as np
import time

from email.policy import strict
from flask import request, jsonify
from flask_login import login_user, login_required, logout_user, current_user
from app import db, bcrypt, app
from app.statusCode import HTTP_200_OK, HTTP_201_CREATED, HTTP_400_BAD_REQUEST, HTTP_401_UNAUTHORIZED, HTTP_409_CONFLICT
from app.user import User, userSchema
from app.model import model, Model, df
from keras.utils import load_img, img_to_array

allUsers = userSchema(many=True)


@app.route('/')
def home():
    return 'hello world'


@app.route('/getUsers', methods=['GET'])
def getUsers():
    users = User.query.all()
    result = allUsers.dump(users)
    return jsonify(result)


@app.route('/login', methods=['POST'])
def login():
    if current_user.is_authenticated:
        return jsonify({
            'message': "User has logged in",

        }), HTTP_200_OK

    username = request.json.get('username', '')
    password = request.json.get('password', '')

    user = User.query.filter_by(username=username).first()

    if user:
        if bcrypt.check_password_hash(user.password, password):
            login_user(user)
            return jsonify({
                'user': {
                    'username': user.username,
                }

            }), HTTP_200_OK

    return jsonify({'error': 'Username and Password not found'}), HTTP_401_UNAUTHORIZED


@app.route('/logout', methods=['GET'])
@login_required
def logout():
    logout_user()
    return jsonify({
            'message': "User has logged out",

        }), HTTP_200_OK


@ app.route('/signup', methods=['POST'])
def signup():
    username = request.json['username']
    password = request.json['password']

    if len(password) < 8:
        return jsonify({'error': "Password must be more than 8 characters"}), HTTP_400_BAD_REQUEST

    if len(username) < 4:
        return jsonify({'error': "User must be more than 4 characters"}), HTTP_400_BAD_REQUEST

    if not username.isalnum() or " " in username:
        return jsonify({'error': "Username can't be blank"}), HTTP_400_BAD_REQUEST

    existing_user_username = User.query.filter_by(
            username=username).first()
    if existing_user_username:
        return jsonify({'error': "Username already exists"}), HTTP_409_CONFLICT
    
    hashed_password = bcrypt.generate_password_hash(password)
    new_user = User(username=username, password=hashed_password)
    db.session.add(new_user)
    db.session.commit()

    return jsonify({
            'message': "User created",
            'user': {
                'username': username
            }

        }), HTTP_201_CREATED

@app.route("/predict", methods= ["POST"])
def predict():
    shutil.rmtree('images')
    os.makedirs('images')

    get_img=request.files['images']
    filepath= os.path.join(app.config['UPLOAD_FOLDER'], get_img.filename)
    get_img.save(filepath)

    fname= "images/{}".format(os.listdir('images/')[0])
    
    img_size = (224, 224)
    dummy = load_img(fname, target_size = img_size)
    dummy = img_to_array(dummy)
    dummy = np.expand_dims(dummy, axis = 0)
    result = Model.predict(dummy)
    label = np.argmax(result[0])
    model.id = str(time.time()).replace('.', '')[3:13]
    if label == 0:
        model.name = 'Common rust'
    elif label == 1:
        model.name = 'bight'
    elif label == 2:
        model.name = 'Gray leaf spot'
    elif label == 3:
        model.name = 'corn healthy'
    model.description = df.loc[df["disease_name"] == model.name, 'description'].to_json()
    model.solution = df.loc[df["disease_name"] == model.name, 'resolve,,,,,,,,,'].to_json()
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
                'id': model.id,
                'name' : model.name,
                'description' : model.description,
                'solution' : model.solution  
            }
        })
        