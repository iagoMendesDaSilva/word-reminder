import json
import requests
 
class Notification:
    def __init__(self):
        self.app_id = "9ced6a78-cf5b-44c5-a952-620c5367f8da"
        self.header = {"Content-Type": "application/json; charset=utf-8"}
    
    def create_payload(self, player_id, word, transalte,time):
        return {
            "app_id": self.app_id,
            "delivery_time_of_day":  time.strftime("%H:%M"),
            "delayed_option":'timezone',
            "include_player_ids": player_id,
            "contents": {"en": "What does \"{}\" mean?".format(word)},
            "buttons": [{"id": "answer", "text": "It's  \"{}\"".format(transalte)}]
               }

    def send(self, player_id, word, transalte,time):
        payload = self.create_payload(player_id, word, transalte,time)
        requests.post("https://onesignal.com/api/v1/notifications", headers=self.header, data=json.dumps(payload))

notification = Notification()