import tensorflow as tf
import tensorflow_hub as hub
import pandas as pd

from flask_marshmallow import Marshmallow
from app import db, app
from tensorflow.keras.models import load_model

ma = Marshmallow(app)
class modelSchema(ma.Schema):
  class Meta:
    fields = ('id', 'name', 'description', 'solution')


class model(db.Model):
    id = db.Column(db.String(10), primary_key=True)
    name = db.Column(db.String(20))
    description = db.Column(db.String(80))
    solution = db.Column(db.String(80))

    def __repr__(self) -> str:
        return 'model>>> {self.name}'

Model =  load_model("../../ML/model_v4.h5", custom_objects={'KerasLayer':hub.KerasLayer} )

df = pd.read_csv("../../ML/description.csv", sep = ";")
