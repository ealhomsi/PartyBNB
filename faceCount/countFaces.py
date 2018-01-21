#!/usr/bin/env python3
import cv2
import sys
import os
import logging as log
import datetime as dt
from time import sleep

cascPath = "haarcascade_frontalface_default.xml"
faceCascade = cv2.CascadeClassifier(cascPath)
log.basicConfig(filename='webcam.log',level=log.INFO)

ext=os.path.splitext(sys.argv[1])[1]
if ext=='.jpg' or ext=='.gif' or ext=='.jpeg' or ext=='.png':
    image=cv2.imread(sys.argv[1],0)
    faces = faceCascade.detectMultiScale(
        image,
        scaleFactor=1.1,
        minNeighbors=5,
        minSize=(5, 5)
    )
    res=len(faces)
    print(res)
    exit(res)

video_capture = cv2.VideoCapture(sys.argv[1])
anterior = 0

sum=0
total_frames=0

while True:
    if not video_capture.isOpened():
        print('Unable to load camera.')
        sleep(5)
        pass

    ret, frame = video_capture.read()
    total_frames+=1
    if total_frames%5!=0:
        continue

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    faces = faceCascade.detectMultiScale(
        gray,
        scaleFactor=1.1,
        minNeighbors=2,
        minSize=(5, 5)
    )

    sum+=len(faces)
    for (x, y, w, h) in faces:
        cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)

    if anterior != len(faces):
        anterior = len(faces)
        log.info("faces: "+str(len(faces))+" at "+str(dt.datetime.now()))


    cv2.imshow('Video', frame)


    if cv2.waitKey(1) & 0xFF == ord('q'):
        break


res=int((sum*5)/total_frames)

print(res)

## release objects
video_capture.release()
cv2.destroyAllWindows()

exit(res)