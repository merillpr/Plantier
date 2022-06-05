from flask_login import UserMixin
from app import app, db, login_manager
from flask_marshmallow import Marshmallow

ma = Marshmallow(app)


@login_manager.user_loader
def load_user(user_id):
    return User.query.get(int(user_id))


class userSchema(ma.Schema):
  class Meta:
    fields = ('id', 'username')


class User(db.Model, UserMixin):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(20), nullable=False, unique=True)
    password = db.Column(db.String(80), nullable=False)
    profile_image = db.Column(db.String(20), nullable=False, default='default.jpg')

    def __repr__(self) -> str:
        return 'User>>> {self.username}'