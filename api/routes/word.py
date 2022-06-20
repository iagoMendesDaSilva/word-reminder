from app.token import token
from app.applications import app
from flask import jsonify, request
from controllers import wordController

@app.route("/word", methods=["POST"])
@token
def create_word(current_user):
        word = wordController.create(current_user.id, request.get_json())
        return jsonify(word), 200

@app.route("/words", methods=["GET"])
@token
def words(current_user):
        return jsonify(wordController.getWords(current_user.id)), 200

@app.route("/word/<int:id>", methods=["GET","PUT","DELETE"])
@token
def word(current_user, id):
    if request.method == 'GET':
        word = wordController.get(current_user.id,id)
        return jsonify(word), 200
    elif request.method == 'PUT':
        word = wordController.update(current_user.id, request.get_json(),id)
        return jsonify(word), 200
    elif request.method == 'DELETE':
        wordController.delete(current_user.id,id)
        return jsonify({"response":"Deleted Word"}), 200
