
from connect import Connection
from pymongo import MongoClient

class MongoConnection(Connection):

	#def _init_(self,uri):
		#super()._init_(uri)

	def connect(self):
		self.obj=MongoClient(self.uri);


