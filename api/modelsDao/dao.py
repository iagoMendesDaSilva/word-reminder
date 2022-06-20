from models import *
from flask import abort
from app.applications import database
from app.exceptions import ObjectInvalid
from sqlalchemy.exc import IntegrityError

class DaoDefault:

    def __init__(self):
        pass

    def add(self, object):
        self.commit(object, "add")

    def add_all(self, objects):
        self.commit(objects, "add_all")

    def remove(self,object) :
        self.commit(object, "delete")

    def update(self, id, key, value, model):
        object = self.get_by_id(id,model)
        setattr(object, key, value)
        self.commit()

    def update_many(self,id,data,model):
        object = self.get_by_id(id,model)
        for key in data:
            if hasattr(object, key):
                setattr(object, key, data[key])
        self.commit()
   
    def get_by_key(self, key, value, model):
            key = self.get_key_formated(key, model)
            object =  model.query.filter(key == value).first()
            if  not object:
                raise ObjectInvalid
            return object

    def get_by_id(self, id, model):
        object =  model.query.filter(model.id == id).first()
        if not object:
            raise ObjectInvalid
        return object

    def get_all_by_key(self,key, value, model):
            key = self.get_key_formated(key, model)
            return model.query.filter(key == value).all()

    def get_all_by_model(self, model):
            return model.query.all()

    def exist_by_key(self, key, value, model):
        key = self.get_key_formated(key, model)
        return  model.query.filter(key == value).first()

    def get_key_formated(self, key, model):
        return eval(model.__tablename__.title() + f".{key}")

    def commit(self, object=None, method=None):
        try: 
            if(method=="add"):
                database.session.add(object)
            elif(method=="add_all"):
                database.session.add_all(object)
            elif(method=="delete"):
                database.session.delete(object)
            database.session.commit() 
        except IntegrityError as err:
            database.session.remove()
            abort(409, err.args)
        except Exception as err:
            database.session.remove()
            abort(400, err.args)

dao = DaoDefault()
    