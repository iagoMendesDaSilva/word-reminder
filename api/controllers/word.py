from modelsDao import  dao
from models.user import User
from app.notification import notification
from werkzeug.exceptions import BadRequest
from app import  ObjectInvalid, CurrentUser
from flask import abort, make_response, jsonify
from models.word import word_schema, words_schema, Word

class WordController:
    def __init__(self):
        pass

    def create(self, id_user, data):
        try:
            user = dao.get_by_id(id_user, User)
            if user:
                word = Word(
                active=data['active'],
                word=data['word'],
                word_translate=data['word_translate'],
                time=data['time'],
                id_user=id_user)
                dao.add(word)
                return word_schema.dump(word)
            else:
                raise BadRequest
        except BadRequest as err:
            abort(make_response(jsonify({"response":"  Invalid parameters."}), 400))
        except Exception as err:
            abort(make_response(jsonify({"response":"Internal problem"}), 500))

    def get(self, user_id, id):
        try:
            word = dao.get_by_id(id, Word)
            if user_id == word.id_user:
                return word_schema.dump(word)
            else:
                raise CurrentUser
        except CurrentUser as err:
            abort(make_response(jsonify({"response":"Without Permission."}), 403))
        except ObjectInvalid as err:
            abort(make_response(jsonify({"response":"Invalid Word."}), 404))
        except Exception as err:
            abort(make_response(jsonify({"response":"Internal problem."}), 500))

    def getWords(self, id_user):
        try:
            return words_schema.dump(dao.get_all_by_key('id_user', id_user, Word))
        except ObjectInvalid as err:
            abort(make_response(jsonify({"response":"Invalid Word."}), 404))
        except Exception as err:
            abort(make_response(jsonify({"response":"Internal problem."}), 500))

    def delete(self,user_id,id):
        try:
            word = dao.get_by_id(id,Word)
            if user_id == word.id_user:
                dao.remove(word)
            else:
                raise CurrentUser
        except CurrentUser as err:
            abort(make_response(jsonify({"response":"Without Permission."}), 403))
        except ObjectInvalid as err:
            abort(make_response(jsonify({"response":"Invalid Word."}), 404))
        except Exception as err:
            abort(make_response(jsonify({"response":"Internal problem."}), 500))

    def update(self,user_id,data,id):
        try:
            word =dao.get_by_id(id,Word)
            if word:
                if user_id == word.id_user:
                    dao.update_many(id,data, Word)
                    return word_schema.dump(word)
                else:
                    raise CurrentUser
            else:
                raise ObjectInvalid
        except CurrentUser as err:
            abort(make_response(jsonify({"response":"Without Permission."}), 403))
        except ObjectInvalid as err:
            abort(make_response(jsonify({"response":"Invalid Word."}), 404))
        except Exception as err:
                abort(make_response(jsonify({"response":"Internal problem."}), 500))

    def verify_messages(self):
        words = dao.get_all_by_model(Word)
        for word in words:
            if word.active==True:
                user = dao.get_by_id(word.id_user,User)
                if user.token is not None and user.onesignal_playerID is not None:
                    notification.send([user.onesignal_playerID],word.word,word.word_translate, word.time)

wordController = WordController()