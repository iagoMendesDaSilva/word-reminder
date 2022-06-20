import json
import requests

r = requests.post("YOUR URL", data=json.dumps({}))
print(r)