from models.word import WordSchema
from app.applications import database, serializer

class User(database.Model):
    token = database.Column(database.Text)
    recovery = database.Column(database.Integer)
    recovery_time = database.Column(database.DateTime)
    onesignal_playerID = database.Column(database.String(40))
    password = database.Column(database.Text, nullable=False)
    verified = database.Column(database.Boolean, nullable=False)
    email = database.Column(database.String(200), nullable=False, unique=True)
    id = database.Column(database.Integer, primary_key=True, nullable=False, autoincrement=True)

    words = database.relationship('Word', backref='words', cascade="all, delete")

class UserSchema(serializer.SQLAlchemyAutoSchema):
    words = serializer.Nested(WordSchema, many=True)

    class Meta:
        model = User     

user_schema = UserSchema(only=("token","id","onesignal_playerID","email"))
users_schema = UserSchema(many=True)