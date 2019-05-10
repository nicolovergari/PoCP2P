from flask import Flask, request, jsonify, Response
from flask_restful import reqparse, Resource, Api
import connect
import MongoConnect
from bson import json_util
import json
import datetime

db=MongoConnect.MongoConnection("mongodb+srv://Api_app:swe2019@clusterdiprova-prsqj.mongodb.net/test?retryWrites=true")
db.connect()


app = Flask(__name__)
api = Api(app)

class ListofCarsinRange(Resource):

	#parser = reqparse.RequestParser()
	#parser.add_argument()

	def get(self):
		return [{"funzia":"si"}]

class UserCars(Resource):

	def get(self,userId):
		query={"proprietarioID":userId}
		filter={"dataIm":0,"proprietarioID":0}
		return Response(json.dumps({"data":list(db.obj["maincontainer"]["cars"].find(query,filter))},default=json_util.default),mimetype="application/json")

class UserActiveCars(Resource):

	def get(self,userId,x,y):
		#query={"proprietarioID":userId,"periodoSharing":{"$exists":"true"},"inuso":"false","posizione":{"$near":{"$geometry":{"type":"Point","coordinates":[float(x),float(y)]}}}}
		query={"inuso":{"$ne":"true"},"proprietarioID":userId,"periodoSharing":{"$exists":"true"},"posizione":{"$near":{"$geometry":{"type":"Point","coordinates":[float(x),float(y)]}}}}
		filter={"proprietarioID":0}
		return Response(json.dumps({"data":list(db.obj["maincontainer"]["cars"].find(query,filter))},default=json_util.default),mimetype="application/json")

class DateActiveCars(Resource):

	def get(self,date,x,y):
		cdate = datetime.datetime.strptime(date,"%Y-%m-%d")
		ddate = datetime.datetime.strptime("1999-7-7","%Y-%m-%d")
		query={"inuso":{"$ne":"true"},"periodoSharing":{"$gte":cdate,"$lte":cdate},"posizione":{"$near":{"$geometry":{"type":"Point","coordinates":[float(x),float(y)]}}}}
                filter={"periodoSharing":0}
                return Response(json.dumps({"data":list(db.obj["maincontainer"]["cars"].find(query,filter))},default=json_util.default),mimetype="application/json")

api.add_resource(ListofCarsinRange,"/cars")
api.add_resource(UserCars,"/cars/<string:userId>")
api.add_resource(UserActiveCars,"/cars/activebyuser/<string:userId>/<x>/<y>")
api.add_resource(DateActiveCars,"/cars/activebydate/<string:date>/<x>/<y>")

if __name__ == '__main__':
	app.run(threaded=True,host="0.0.0.0")
