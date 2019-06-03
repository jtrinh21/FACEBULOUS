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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.DoublePointer;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.*;
import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;

/**
 * I couldn't find any tutorial on how to perform face recognition using OpenCV and Java,
 * so I decided to share a viable solution here. The solution is very inefficient in its
 * current form as the training model is built at each run, however it shows what's needed
 * to make it work.
 *
 * The class below takes two arguments: The path to the directory containing the training
 * faces and the path to the image you want to classify. Not that all images has to be of
 * the same size and that the faces already has to be cropped out of their original images
 * (Take a look here http://fivedots.coe.psu.ac.th/~ad/jg/nui07/index.html if you haven't
 * done the face detection yet).
 *
 * For the simplicity of this post, the class also requires that the training images have
 * filename format: <label>-rest_of_filename.png. For example:
 *
 * 1-jon_doe_1.png
 * 1-jon_doe_2.png
 * 2-jane_doe_1.png
 * 2-jane_doe_2.png
 * ...and so on.
 *
 * Source: http://pcbje.com/2012/12/doing-face-recognition-with-javacv/
 *
 * @author Petter Christian Bjelland
 */
public class OpenCVFaceRecognizer {
    

        
    public  int Recognizer() throws SQLException{
        
        String trainingDir = "C:\\Users\\trinh\\Documents\\GitHub\\Facebulous\\capture";

        
        Mat testImage = imread("C:\\Users\\trinh\\Documents\\GitHub\\Facebulous\\camera.jpg", IMREAD_GRAYSCALE);


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
      
        return predictedLabel;
    
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
