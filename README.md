# FACEBULOUS


![facebulous](https://github.com/jtrinh21/FACEBULOUS/blob/master/src/images/Screenshot%20(2).png)


## Overview

An application is capable of detecting, capturing different human faces as well as identifying and verifying a person by comparing his/her face with the faces captured in the database.

## Set-up

* *Xampp*: PHP developer environment [download](https://www.apachefriends.org/index.html)

![SetUpXampp](https://github.com/jtrinh21/HotelBookingSystem/blob/master/build/classes/Image/SetupXampp.gif)

## Features

* Secure login credentials using the phpMyAdmin administration tool.
* Grab images from the camera.
* Detect human faces and capture them using OpenCV library.
* Store personal information and present it after recognizing a matching face.
* Draw a dynamic line chart of time versus confidence values.


## Explore (click on the images to see the full effects).

#### Login Frame
* Login Form using phpMyAdmin administration tool to control and FreeTTS API to welcome the users.

 [![image](https://github.com/jtrinh21/FACEBULOUS/blob/master/gif/LoginForm.gif)](https://www.youtube.com/watch?v=d8h5IL9N3pE&feature=youtu.be)
 
 #### Face Capturer
* Users are required to input their information including first - middle - last name, 
date of birth, phone number, email address...
* These information will be stored  and manipulated using the phpMyAdmin administration tool.
* Face detection: a rectangle will be drawn around the face detected.
* Capture button to save all the captured images in a folder. These images and information (labels)
 will be used to train a classifier. 
 
  [![image](https://github.com/jtrinh21/FACEBULOUS/blob/master/gif/FaceCapture.png)](https://www.youtube.com/watch?v=UgoLVreS2Aw&list=PLHYjzSFjyyZbkPA2gw46AxITSUdRWC9Ft&index=4&t=0s)

#### Face Recognizer
* Webcam will be turned on and the recognize method will be called to match and verify 
a face of a person sitting in front of the camera and the faces stored in the database
(what a classifier learned).
* Once a matching face has been found, the information related to the person will be shown.

  [![image](https://github.com/jtrinh21/FACEBULOUS/blob/master/gif/FaceRegconize.png)](https://www.youtube.com/watch?v=rfuolGRhg3U&feature=youtu.be)

#### Chart
* When comparing the real-time images and the images saved in the database, there is a value produced
called confidence, which is the distance of the recognized face from the original model. As a result,
a score of 0 signifies an exact match.
* The chart will be drawn based on these confidence scores.

#### Data
* All the datas stored in the database will be presented here.

  [![image](https://github.com/jtrinh21/FACEBULOUS/blob/master/gif/Data.png)](https://www.youtube.com/watch?v=XmrRz5kyWm8&feature=youtu.be)
