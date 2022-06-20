from app.token import token
from app.applications import app
from flask import jsonify, request
from controllers import userController

@app.route("/auth/login", methods=["POST"])
def login():
    user =  userController.login(request.get_json())
    return jsonify(user), 200

@app.route("/auth/logout", methods=["GET"])
@token
def logout(current_user):
    userController.logout(current_user.id)
    return jsonify({"response":"Logged Out"}), 200

@app.route("/auth/verify-email", methods=["POST"])
def verify_email():
    id =  userController.verify_email(request.get_json())
    return jsonify({'id': id}), 200

@app.route("/auth/register", methods=["POST"])
def register():
    userController.register(request.get_json())
    return jsonify({"response":"Email Sent"}), 200

@app.route("/auth/unregister", methods=["POST"])
@token
def unregister(current_user):
    userController.unregister(current_user.id)
    return jsonify({"response":"Account deleted"}), 200

@app.route("/confirm_email/<token>", methods=["GET"])
def verify_account(token):
    return userController.create_account(token)

@app.route("/auth/confirm-email", methods=["POST"])
def confirm_email():
    id =  userController.confirm_email(request.get_json())
    return jsonify({'id': id}), 200

@app.route("/auth/confirm-verification-code/<int:id>", methods=["POST"])
def confirm_verification_code(id):
        user =userController.confirm_verification_code(id,  request.get_json())
        return jsonify({'token': user.token}), 200

@app.route("/auth/recovery", methods=["POST"])
@token
def recovery(current_user):
    user = userController.recovery(current_user.id,request.get_json())
    return jsonify(user), 200
