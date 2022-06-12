import os
import shutil
import numpy as np
import time
import validators

from flask import request, jsonify
from flask_login import login_user, login_required, logout_user, current_user
from app import db, bcrypt, app
from app.statusCode import HTTP_200_OK, HTTP_201_CREATED, HTTP_400_BAD_REQUEST, HTTP_401_UNAUTHORIZED, HTTP_409_CONFLICT
from app.user import User, userSchema
from app.model import model, Model, df, modelSchema
from keras.utils import load_img, img_to_array

allUsers = userSchema(many=True)
singleUser = userSchema()
singleModel = modelSchema()


@app.route('/')
def home():
    return 'hello world'


@app.route('/getUsers', methods=['GET'])
def getUsers():
    users = User.query.all()
    result = allUsers.dump(users)
    return jsonify({
            'message': "Get all data user",
            'user' : result
        }), HTTP_200_OK


@app.route('/login', methods=['POST'])
def login():
    if current_user.is_authenticated:
        result = singleUser.dump(current_user)
        return jsonify({
            'message': "User has logged in",
            'user': result
        }), HTTP_200_OK

    username = request.json.get('username', '')
    password = request.json.get('password', '')

    user = User.query.filter_by(username=username).first()

    if user:
        if bcrypt.check_password_hash(user.password, password):
            login_user(user)
            result = singleUser.dump(user)
            return jsonify({
                'message': "User has logged in",
                'user': result

            }), HTTP_200_OK

    return jsonify({'error': 'Username and Password not found'}), HTTP_401_UNAUTHORIZED


@app.route('/logout', methods=['GET'])
@login_required
def logout():
    logout_user()
    return jsonify({
            'message': "User has logged out",

        }), HTTP_200_OK


@app.route('/signup', methods=['POST'])
def signup():
    email = request.json['email']
    username = request.json['username']
    password = request.json['password']
    id = str(time.time()).replace('.', '')[3:13]

    if len(password) < 8:
        return jsonify({'error': "Password must be more than 8 characters"}), HTTP_400_BAD_REQUEST

    if len(username) < 4:
        return jsonify({'error': "User must be more than 4 characters"}), HTTP_400_BAD_REQUEST

    if not validators.email(email):
        return jsonify({'error': "Email is not valid"}), HTTP_400_BAD_REQUEST

    existing_user_username = User.query.filter_by(
            username=username).first()
    if existing_user_username:
        return jsonify({'error': "Username already exists"}), HTTP_409_CONFLICT
    existing_user_email = User.query.filter_by(
            email=email).first()
    if existing_user_email:
        return jsonify({'error': "Email already exists"}), HTTP_409_CONFLICT
    
    hashed_password = bcrypt.generate_password_hash(password)
    new_user = User(email=email,username=username, password=hashed_password,id=id)
    db.session.add(new_user)
    db.session.commit()
    result = singleUser.dump(new_user)
    return jsonify({
            'message': "User created",
            'user': result

        }), HTTP_201_CREATED


@app.route('/user/<id>', methods=['GET'])
@login_required
def get_user(id):
    user = User.query.get(id)
    result = singleUser.dump(user)
    return jsonify({
            'message': "Get user data",
            'user' : result
        }), HTTP_200_OK


@app.route('/user/<id>', methods=['PUT'])
@login_required
def update_user(id):
    user = User.query.get(id)
    username = request.json['username']
    password = request.json['password']
    #profile_image = request.json['profile_image']
    
    if len(password) < 8:
        return jsonify({'error': "Password must be more than 8 characters"}), HTTP_400_BAD_REQUEST

    if len(username) < 4:
        return jsonify({'error': "User must be more than 4 characters"}), HTTP_400_BAD_REQUEST

    if current_user.username != username :
        existing_user_username = User.query.filter_by(
                username=username).first()
        if existing_user_username:
            return jsonify({'error': "Username already exists"}), HTTP_409_CONFLICT
    
    hashed_password = bcrypt.generate_password_hash(password)

    user.username = username
    user.password = hashed_password
    #user.profile_image = profile_image

    db.session.commit()
    result = singleUser.dump(user)
    return jsonify({
            'message': "User has updated",
            'user' : result
        }), HTTP_200_OK


@app.route('/user/<id>', methods=['DELETE'])
@login_required
def delete_user(id):
  user = User.query.get(id)
  db.session.delete(user)
  db.session.commit()

  return jsonify({
            'message': "User has deleted",
        }), HTTP_200_OK


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
        model.name = 'blight'
    elif label == 2:
        model.name = 'Gray leaf spot'
    elif label == 3:
        model.name = 'corn healthy'
    model.description = df.loc[df["disease_name"] == model.name, 'description'].to_json()
    model.solution = df.loc[df["disease_name"] == model.name, 'resolve'].to_json()
    os.remove("images/{}".format(get_img.filename))
    #return jsonify(id=id, label=label_name, description=description, solution= solution)
    result = singleModel.dump(model)
    return jsonify({
            'message': "prediction success",
            'model': result
        })
        