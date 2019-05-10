from abc import abstractmethod

class Connection:

	def __init__(self,uri):
		self.uri=uri

	@abstractmethod
	def connect(self):
		pass	
