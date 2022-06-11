import tensorflow as tf
import tensorflow_hub as hub
import pandas as pd

from app import db

class model(db.Model):
    id = db.Column(db.String(10), primary_key=True)
    name = db.Column(db.String(20))
    description = db.Column(db.String(80))
    solution = db.Column(db.String(80))

    def __repr__(self) -> str:
        return 'model>>> {self.name}'

Model = tf.keras.models.load_model("../../ML/model_v4.h5", custom_objects={'KerasLayer':hub.KerasLayer})

df = pd.read_csv("../../ML/description_v2.csv", sep = ";")