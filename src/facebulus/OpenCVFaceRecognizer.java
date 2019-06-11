/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebulus;

import static facebulus.Recognize.address;
import static facebulus.Recognize.birth;
import static facebulus.Recognize.career;
import static facebulus.Recognize.city;
import static facebulus.Recognize.email;
import static facebulus.Recognize.firstname;
import static facebulus.Recognize.lastname;
import static facebulus.Recognize.marital;
import static facebulus.Recognize.middle;
import static facebulus.Recognize.phone;
import static facebulus.Recognize.state;
import static facebulus.Recognize.zipcode;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.DoublePointer;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.*;
import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;


public class OpenCVFaceRecognizer {
    
    private double confidenceValue = 0;
        
    public Pair<Integer, Double> Recognizer() throws SQLException{
        
        String trainingDir = "C:\\Users\\dttri\\OneDrive\\Documents\\GitHub\\Facebulous\\capture";

        
        Mat testImage = imread("C:\\Users\\dttri\\OneDrive\\Documents\\GitHub\\Facebulous\\camera.jpg", IMREAD_GRAYSCALE);


        File root = new File(trainingDir);

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };

   
        File[] imageFiles = root.listFiles(imgFilter);
        
        MatVector images = new MatVector(imageFiles.length);

        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);

        IntBuffer labelsBuf = labels.createBuffer();
       
        int counter = 0;

        for (File image : imageFiles) {
            
            Mat img = imread(image.getAbsolutePath(), IMREAD_GRAYSCALE);

            int label = Integer.parseInt(image.getName().split("\\-")[0]);

            images.put(counter, img);

            labelsBuf.put(counter, label);

            counter++;
        }
       // FaceRecognizer faceRecognizer = FisherFaceRecognizer.create();
       //  FaceRecognizer faceRecognizer = EigenFaceRecognizer.create();
         FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();

        faceRecognizer.train(images, labels);

        IntPointer label = new IntPointer(1);
        DoublePointer confidence = new DoublePointer(1);
        faceRecognizer.predict(testImage, label, confidence);
        
        int predictedLabel = label.get(0);
        
       
        System.out.println(predictedLabel);
        System.out.println(confidence.get(0));
        
        confidenceValue = confidence.get(0);
        
        return new Pair<>(predictedLabel, confidence.get(0));
    
    }

    
    public void setText(int n) throws SQLException
    {    
    
            Connection conn = LoginConnection.getConnection();
        
            String sql = "select * from profile where id = " + n;
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                try {
                    firstname.setText(rs.getString(2));
                    lastname.setText(rs.getString(3));
                    middle.setText(rs.getString(4));
                    birth.setText(rs.getString(5));
                    phone.setText(rs.getString(6));
                    email.setText(rs.getString(7));
                    career.setText(rs.getString(8));
                    marital.setText(rs.getString(9));
                    address.setText(rs.getString(10));
                    city.setText(rs.getString(11));
                    state.setText(rs.getString(12));
                    zipcode.setText(rs.getString(13));
                } catch (SQLException ex) {
                    Logger.getLogger(OpenCVFaceRecognizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

    }
}
