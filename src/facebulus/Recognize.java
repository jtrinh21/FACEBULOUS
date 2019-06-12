/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebulus;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import org.jfree.ui.RefineryUtilities;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author trinh
 */
public class Recognize extends javax.swing.JFrame {

    private DaemonThread dThread = null;       
    
    OpenCVFaceRecognizer recognizer = new OpenCVFaceRecognizer();
    
    // create VideoCapture object to get video from the webcam
    VideoCapture camera = null;
    
    // create a frame to store image
    Mat frame = new Mat();
    
    // create a buffer to store the Mat
    MatOfByte mem = new MatOfByte();
    
    CascadeClassifier faceDetector = new CascadeClassifier("C:\\Users\\dttri\\OneDrive\\Documents\\GitHub\\opencv\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
    
    // a frame to display the rectangular
    MatOfRect faceDetections = new MatOfRect();
    
    
    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;
        
        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    try {
                        if (camera.grab()) {
                            
                            camera.retrieve(frame);
                            
                            // instantiate graphics class to draw on images
                            Graphics g = panel.getGraphics();
                            
                            // detects objects of different sizes in the input image
                            faceDetector.detectMultiScale(frame, faceDetections);
                            
                            Mat tempMat = frame.clone(); 
                            // put the result in an array of Rects and draw them on the frame
                            
                            Rect rectCrop = null;
                            
                            for(Rect rect: faceDetections.toArray())
                            {
                                    Imgproc.rectangle(frame, 
                                        new Point(rect.x, rect.y), 
                                             new Point(rect.x+rect.width, rect.y+rect.height),
                                                    new Scalar(0, 255,0 ));
                                    
                                    rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
                                    
                            }
                            
                                        
                            Mat temp = new Mat(tempMat, rectCrop);
                                        
                            // put the frame into the buffer using the imencode funcion
                            Imgcodecs.imencode(".bmp", frame, mem);
                            
                            // once we filled the buffer, we have to stream it into an Image 
                            // using ByArrayInputStream
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            
                            // convert Image to BufferedImage
                            BufferedImage buff = (BufferedImage) im;
                            
                            // capture an image and name it as camera.jpg
                            Imgcodecs.imwrite("camera.jpg", temp);
                             
                            recognizer.setText(recognizer.Recognizer().getPredictedLabel());
                            
                            if (g.drawImage(buff, 0, 0, panel.getWidth(), 
                                    panel.getHeight(), 0, 0, buff.getWidth(), 
                                    buff.getHeight(), null)) 
                            {
                                if (runnable == false) 
                                {                                    
                                   this.wait();
                                }
                            }
                        }

                    } catch (Exception ex){
                                    
                        System.out.println("Error");
                        firstname.setText("");
                        lastname.setText("");
                        middle.setText("");
                        birth.setText("");
                        phone.setText("");
                        email.setText("");
                        career.setText("");
                        marital.setText("");
                        address.setText("");
                        city.setText("");
                        state.setText("");
                        zipcode.setText(""); 
                                    
                    }
                }
            }
        }
    }
    
    
    public Recognize() {
        initComponents();
        activateCam();      
     //   setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel7 = new javax.swing.JLabel();
        firstname = new javax.swing.JTextField();
        career = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        phone = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        jSeparator19 = new javax.swing.JSeparator();
        birth = new javax.swing.JTextField();
        lastname = new javax.swing.JTextField();
        address = new javax.swing.JTextField();
        jSeparator20 = new javax.swing.JSeparator();
        city = new javax.swing.JTextField();
        jSeparator21 = new javax.swing.JSeparator();
        state = new javax.swing.JTextField();
        jSeparator22 = new javax.swing.JSeparator();
        zipcode = new javax.swing.JTextField();
        jSeparator23 = new javax.swing.JSeparator();
        middle = new javax.swing.JTextField();
        jSeparator24 = new javax.swing.JSeparator();
        marital = new javax.swing.JTextField();
        jSeparator25 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        chartButton = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Face Recognizer");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLayeredPane2.setBackground(new java.awt.Color(255, 255, 255));
        jLayeredPane2.setOpaque(true);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(118, 97, 83));
        jLabel7.setText("Information Found");

        firstname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        firstname.setForeground(new java.awt.Color(118, 97, 83));
        firstname.setText("First Name");
        firstname.setBorder(null);

        career.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        career.setForeground(new java.awt.Color(118, 97, 83));
        career.setText("Career");
        career.setBorder(null);

        jSeparator8.setForeground(new java.awt.Color(118, 97, 83));

        jSeparator9.setForeground(new java.awt.Color(118, 97, 83));

        jSeparator16.setForeground(new java.awt.Color(118, 97, 83));

        jSeparator17.setForeground(new java.awt.Color(118, 97, 83));

        jSeparator18.setForeground(new java.awt.Color(118, 97, 83));

        phone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        phone.setForeground(new java.awt.Color(118, 97, 83));
        phone.setText("Phone Number");
        phone.setBorder(null);

        email.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        email.setForeground(new java.awt.Color(118, 97, 83));
        email.setText("Email Address");
        email.setBorder(null);

        jSeparator19.setForeground(new java.awt.Color(118, 97, 83));

        birth.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        birth.setForeground(new java.awt.Color(118, 97, 83));
        birth.setText("Date of Birth");
        birth.setBorder(null);

        lastname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lastname.setForeground(new java.awt.Color(118, 97, 83));
        lastname.setText("Last Name");
        lastname.setBorder(null);

        address.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        address.setForeground(new java.awt.Color(118, 97, 83));
        address.setText("Address");
        address.setBorder(null);

        jSeparator20.setForeground(new java.awt.Color(118, 97, 83));

        city.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        city.setForeground(new java.awt.Color(118, 97, 83));
        city.setText("City");
        city.setBorder(null);

        jSeparator21.setForeground(new java.awt.Color(118, 97, 83));

        state.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        state.setForeground(new java.awt.Color(118, 97, 83));
        state.setText("State");
        state.setBorder(null);

        jSeparator22.setForeground(new java.awt.Color(118, 97, 83));

        zipcode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        zipcode.setForeground(new java.awt.Color(118, 97, 83));
        zipcode.setText("Zip Code");
        zipcode.setBorder(null);

        jSeparator23.setForeground(new java.awt.Color(118, 97, 83));

        middle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        middle.setForeground(new java.awt.Color(118, 97, 83));
        middle.setText("Middle Initial");
        middle.setBorder(null);

        jSeparator24.setForeground(new java.awt.Color(118, 97, 83));

        marital.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        marital.setForeground(new java.awt.Color(118, 97, 83));
        marital.setText("Marital Status");
        marital.setBorder(null);

        jSeparator25.setForeground(new java.awt.Color(118, 97, 83));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(118, 97, 83));
        jLabel1.setText("To View Chart");

        back.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back (2).png"))); // NOI18N
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });

        chartButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/graph (2).png"))); // NOI18N
        chartButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chartButtonMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(118, 97, 83));
        jLabel3.setText("Click");

        jLayeredPane2.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(firstname, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(career, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator18, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(phone, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(email, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator19, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(birth, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(lastname, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(address, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator20, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(city, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator21, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(state, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator22, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(zipcode, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator23, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(middle, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator24, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(marital, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jSeparator25, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(back, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(chartButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(firstname, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(phone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(79, 79, 79))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSeparator18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSeparator20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                    .addComponent(jSeparator22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(state, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                        .addComponent(career, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20))
                                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                        .addComponent(middle, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20))
                                    .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(59, 59, 59)))
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator17)
                            .addComponent(jSeparator16)
                            .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(birth, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                    .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20))
                                .addComponent(jSeparator21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                    .addComponent(zipcode, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20))
                                .addComponent(jSeparator23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addComponent(marital, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
                            .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(back)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel7)))
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(117, 117, 117))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(lastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(birth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(marital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(firstname, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(middle, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(career, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(state, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(zipcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chartButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panel.setPreferredSize(new java.awt.Dimension(675, 394));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 351, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
            .addComponent(jLayeredPane2)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void activateCam()
    {
        // video capture from default camera
        camera = new VideoCapture(0); 
        
        // create object of thread class
        dThread = new Recognize.DaemonThread();
        
        Thread thread = new Thread(dThread);
        
        thread.setDaemon(true);
        
        dThread.runnable = true;
     
        thread.start();
    }
    
   
        
        
    public void setText() throws SQLException
    {
        Statement stmt = null;
    
        ResultSet rs =null;
    
        Connection conn = LoginConnection.getConnection();
    
        PreparedStatement ps = null;
    
            String sql = "select * from profile where id = " + recognizer.Recognizer();
            
            ps = conn.prepareStatement(sql);
            
            if(rs.next())
            {
                try {
                    firstname.setText(rs.getString(1));
                    lastname.setText(rs.getString(2));
                    middle.setText(rs.getString(3));
                    birth.setText(rs.getString(4));
                    phone.setText(rs.getString(5));
                    email.setText(rs.getString(6));
                    career.setText(rs.getString(7));
                    marital.setText(rs.getString(8));
                    address.setText(rs.getString(9));
                    city.setText(rs.getString(10));
                    state.setText(rs.getString(11));
                    zipcode.setText(rs.getString(12));
                } catch (SQLException ex) {
                    Logger.getLogger(OpenCVFaceRecognizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    
    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
        // TODO add your handling code here:
        
        Options op = new Options();
        
        op.setVisible(true);       
        
        setVisible(false);
    }//GEN-LAST:event_backMouseClicked

    private void chartButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chartButtonMouseClicked
        // TODO add your handling code here:
                       
        Chart lineChart = new Chart("Confidence Value and Time Chart");
        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
    }//GEN-LAST:event_chartButtonMouseClicked


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Recognize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Recognize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Recognize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Recognize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Recognize().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField address;
    private javax.swing.JLabel back;
    public static javax.swing.JTextField birth;
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JTextField career;
    private javax.swing.JLabel chartButton;
    public static javax.swing.JTextField city;
    public static javax.swing.JTextField email;
    public static javax.swing.JTextField firstname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public static javax.swing.JTextField lastname;
    public static javax.swing.JTextField marital;
    public static javax.swing.JTextField middle;
    private javax.swing.JPanel panel;
    public static javax.swing.JTextField phone;
    public static javax.swing.JTextField state;
    public static javax.swing.JTextField zipcode;
    // End of variables declaration//GEN-END:variables
}
