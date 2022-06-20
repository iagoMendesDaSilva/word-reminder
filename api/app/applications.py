from flask import Flask
from flask_mail import Mail
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
from itsdangerous import URLSafeTimedSerializer


app = Flask(__name__)
app.config["SECRET_KEY"] = "YOUR KEY"
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
app.config["SQLALCHEMY_DATABASE_URI"] = "YOUR DATABASE URI"
app.config['MAIL_SERVER']='smtp.gmail.com'
app.config['MAIL_PORT'] = 587
app.config['MAIL_USERNAME'] = "YOUR EMAIL"
app.config['MAIL_PASSWORD'] = "YOUR PASSWORD"
app.config['MAIL_USE_TLS'] = True
app.config['MAIL_USE_SSL'] = False

mail = Mail(app)
database = SQLAlchemy(app)
serializer = Marshmallow(app)
urlSafe = URLSafeTimedSerializer("YOUR KEY")