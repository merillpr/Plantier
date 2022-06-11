allUsers = userSchema(many=True)


#GET by ID
@app.route('/user/<id>', methods=['GET'])
def get_user(id):
  user = User.query.get(id)
  return userSchema.jsonify(user)

#PUT 
@app.route('/user/<id>', methods=['PUT'])
def update_user(id):
  user = User.query.get(id)

  username = request.json['username']
  email = request.json['email']
  password = request.json['password']
  profile_image = request.json['profile_image']
 

  user.username = username
  user.email = email
  user.password = password
  user.profile_image = profile_image


  db.session.commit()

  return userSchema.jsonify(user)

#DELETE
@app.route('/user/<id>', methods=['DELETE'])
def delete_user(id):
  user = User.query.get(id)
  db.session.delete(user)
  db.session.commit()

  return userSchema.jsonify(user)

if __name__ == '__main__':
    app.run(debug=True)
