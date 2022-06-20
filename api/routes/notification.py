from app import app
from flask import jsonify
from controllers import wordController

@app.route("/notify", methods=["POST"])
def notify():
        wordController.verify_messages()
        return jsonify({"response":"Words Sent"}), 200