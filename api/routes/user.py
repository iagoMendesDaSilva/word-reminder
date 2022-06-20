from app.token import token
from app.applications import app
from flask import jsonify, request
from controllers import userController

@app.route("/user/onesignal-player-id", methods=["PUT"])
@token
def edit_player_id(current_user):
    userController.edit_player_id(request.get_json(), current_user.id)
    return jsonify({"response":"Updated OneSignal Player ID"}), 200