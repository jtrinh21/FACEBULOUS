/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebulus;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author trinh
 */
public class Capture extends javax.swing.JFrame {
    
    Statement stmt = null;
    
    ResultSet rs =null;
    
    Connection conn = LoginConnection.getConnection();
    
    PreparedStatement ps = null;
    
    private DaemonThread dThread = null;
            
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
                            
                            Graphics g = panel.getGraphics();
                            
                            // detects objects of different sizes in the input image
                            faceDetector.detectMultiScale(frame, faceDetections, 1.2, 5, 0, new Size(20, 20));
                            
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
                            
            
                            // put the frame into the buffer using the imencode funcion
                            Imgcodecs.imencode(".bmp", frame, mem);
                            
                            // once we filled the buffer, we have to stream it into an Image 
                            // using ByArrayInputStream
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            
                            // convert Image to BufferedImage
                            BufferedImage buff = (BufferedImage) im;

                            if (g.drawImage(buff, 0, 0, panel.getWidth(), 
                                    panel.getHeight(), 0, 0, buff.getWidth(), 
                                    buff.getHeight(), null)) 
                            {
                                if (runnable == false) 
                                {
                                    System.out.println("Pause...");
                                    
                                    Imgcodecs.imwrite("camera.jpg", tempMat);
                                    
                                    Mat newFrame = Imgcodecs.imread("camera.jpg");
        
                                    Image img = MatToBufferedImage(newFrame);
        
                                    ImageIcon icon = new ImageIcon(img.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), jLabel1.getWidth()));
        
                                    jLabel1.setIcon(icon);                                                                     

                                    for(int i = 1; i <= 15; i++)
                                    {
                                        
                                        Mat temp = new Mat(tempMat, rectCrop);
                                        Imgcodecs.imwrite("C:\\Users\\dttri\\OneDrive\\Documents\\GitHub\\Facebulous\\capture\\" + getID() + "-"  + firstname.getText() + "-" + lastname.getText() + "_" + i + ".jpg", temp);
                                        System.out.println("Capture " + i);
                                    }
                                   //this.wait();
                                }
                            }
                        }
                    

                    } catch (Exception ex){
                                    
                        System.out.println("Error");
                                    
                    }
                }
            }
        }
    }
        
    public Capture() {
        initComponents();
        activateCam();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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
        capture = new javax.swing.JLabel();
        back = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Face Capturer");
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panel.setPreferredSize(new java.awt.Dimension(675, 394));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLayeredPane2.setBackground(new java.awt.Color(255, 255, 255));
        jLayeredPane2.setOpaque(true);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(118, 97, 83));
        jLabel7.setText("Personal Info");

        firstname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        firstname.setForeground(new java.awt.Color(118, 97, 83));
        firstname.setText("First Name");
        firstname.setBorder(null);
        firstname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firstnameMouseClicked(evt);
            }
        });

        career.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        career.setForeground(new java.awt.Color(118, 97, 83));
        career.setText("Career");
        career.setBorder(null);
        career.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                careerMouseClicked(evt);
            }
        });

        jSeparator8.setForeground(new java.awt.Color(118, 97, 83));

        jSeparator9.setForeground(new java.awt.Color(118, 97, 83));

        jSeparator16.setForeground(new java.awt.Color(118, 97, 83));

        jSeparator17.setForeground(new java.awt.Color(118, 97, 83));

        jSeparator18.setForeground(new java.awt.Color(118, 97, 83));

        phone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        phone.setForeground(new java.awt.Color(118, 97, 83));
        phone.setText("Phone Number");
        phone.setBorder(null);
        phone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                phoneMouseClicked(evt);
            }
        });

        email.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        email.setForeground(new java.awt.Color(118, 97, 83));
        email.setText("Email Address");
        email.setBorder(null);
        email.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emailMouseClicked(evt);
            }
        });

        jSeparator19.setForeground(new java.awt.Color(118, 97, 83));

        birth.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        birth.setForeground(new java.awt.Color(118, 97, 83));
        birth.setText("Date of Birth");
        birth.setBorder(null);
        birth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                birthMouseClicked(evt);
            }
        });

        lastname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lastname.setForeground(new java.awt.Color(118, 97, 83));
        lastname.setText("Last Name");
        lastname.setBorder(null);
        lastname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lastnameMouseClicked(evt);
            }
        });

        address.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        address.setForeground(new java.awt.Color(118, 97, 83));
        address.setText("Address");
        address.setBorder(null);
        address.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addressMouseClicked(evt);
            }
        });

        jSeparator20.setForeground(new java.awt.Color(118, 97, 83));

        city.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        city.setForeground(new java.awt.Color(118, 97, 83));
        city.setText("City");
        city.setBorder(null);
        city.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cityMouseClicked(evt);
            }
        });

        jSeparator21.setForeground(new java.awt.Color(118, 97, 83));

        state.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        state.setForeground(new java.awt.Color(118, 97, 83));
        state.setText("State");
        state.setBorder(null);
        state.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stateMouseClicked(evt);
            }
        });

        jSeparator22.setForeground(new java.awt.Color(118, 97, 83));

        zipcode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        zipcode.setForeground(new java.awt.Color(118, 97, 83));
        zipcode.setText("Zip Code");
        zipcode.setBorder(null);
        zipcode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                zipcodeMouseClicked(evt);
            }
        });

        jSeparator23.setForeground(new java.awt.Color(118, 97, 83));

        middle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        middle.setForeground(new java.awt.Color(118, 97, 83));
        middle.setText("Middle Initial");
        middle.setBorder(null);
        middle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                middleMouseClicked(evt);
            }
        });

        jSeparator24.setForeground(new java.awt.Color(118, 97, 83));

        marital.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        marital.setForeground(new java.awt.Color(118, 97, 83));
        marital.setText("Marital Status");
        marital.setBorder(null);
        marital.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                maritalMouseClicked(evt);
            }
        });

        jSeparator25.setForeground(new java.awt.Color(118, 97, 83));

        capture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        capture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button (4).png"))); // NOI18N
        capture.setToolTipText("");
        capture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                captureMouseClicked(evt);
            }
        });

        back.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back (2).png"))); // NOI18N
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });

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
        jLayeredPane2.setLayer(capture, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(back, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(back))
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
                                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                                .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20))
                                            .addComponent(jSeparator20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                                    .addComponent(state, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(20, 20, 20))
                                                .addComponent(jSeparator22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                .addGap(114, 114, 114)
                                .addComponent(capture)))
                        .addGap(0, 34, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(capture, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLayeredPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
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
        dThread = new DaemonThread();
        
        Thread thread = new Thread(dThread);
        
        thread.setDaemon(true);
        
        dThread.runnable = true;
     
        thread.start();
    }
    
    private void emptyField(JTextField t)
    {
        t.setText("");
    }
    
    private void firstnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_firstnameMouseClicked
        // TODO add your handling code here:
        
        emptyField(firstname);
    }//GEN-LAST:event_firstnameMouseClicked

    private void careerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_careerMouseClicked
        // TODO add your handling code here:
        
        emptyField(career);
    }//GEN-LAST:event_careerMouseClicked

    private void phoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phoneMouseClicked
        // TODO add your handling code here:
        
        emptyField(phone);
    }//GEN-LAST:event_phoneMouseClicked

    private void emailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emailMouseClicked
        // TODO add your handling code here:
        
        emptyField(email);
    }//GEN-LAST:event_emailMouseClicked

    private void birthMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_birthMouseClicked
        // TODO add your handling code here:
        
        emptyField(birth);
    }//GEN-LAST:event_birthMouseClicked

    private void lastnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lastnameMouseClicked
        // TODO add your handling code here:
        
        emptyField(lastname);
    }//GEN-LAST:event_lastnameMouseClicked

    private void middleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_middleMouseClicked
        // TODO add your handling code here:
        
        emptyField(middle);
    }//GEN-LAST:event_middleMouseClicked

    private void maritalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_maritalMouseClicked
        // TODO add your handling code here:
        
        emptyField(marital);
    }//GEN-LAST:event_maritalMouseClicked

    private void captureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_captureMouseClicked
        // TODO add your handling code here:
        
        dThread.runnable = false;

        ProgressBar progressBarFrame = new ProgressBar();
        
        progressBarFrame.setUpLoading();
        
        progressBarFrame.setVisible(true);

        try {
            setProfile();
        } catch (SQLException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        camera.release();
    }//GEN-LAST:event_captureMouseClicked

    private Image MatToBufferedImage(Mat m) {

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);  
        return image;
    }    
        
        
    private void cityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cityMouseClicked
        // TODO add your handling code here:
        
        emptyField(city);
    }//GEN-LAST:event_cityMouseClicked

    private void addressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addressMouseClicked
        // TODO add your handling code here:
        
        emptyField(address);
    }//GEN-LAST:event_addressMouseClicked

    private void stateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stateMouseClicked
        // TODO add your handling code here:
        
        emptyField(state);
    }//GEN-LAST:event_stateMouseClicked

    private void zipcodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zipcodeMouseClicked
        // TODO add your handling code here:
        
        emptyField(zipcode);
    }//GEN-LAST:event_zipcodeMouseClicked

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
        // TODO add your handling code here:
        
        Options op = new Options();
        
        op.setVisible(true);
        
        setVisible(false);
    }//GEN-LAST:event_backMouseClicked

    
    private void setProfile() throws SQLException
    {

            String query = "INSERT INTO PROFILE ("
                      + " id,"
                        + " firstname,"
                        + " lastname,"
                        + " middle,"
                        + " birth,"
                        + " phone,"
                        + " email,"
                        + " career,"
                        + " marital,"
                        + " address,"
                        + " city,"
                        + " state,"
                        + " zipcode ) VALUES ("
                        + "null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
         //   String sql = "INSERT INTO `profile` (`id`, `firstname`, `lastname`, `middle`, `birth`, `phone`, `email`, `career`, `marital`, `address`, `city`, `state`, `zipcode`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            
            ps.setString(1, firstname.getText());
            ps.setString(2, lastname.getText());
            ps.setString(3, middle.getText());
            ps.setString(4, birth.getText());
            ps.setString(5, phone.getText());
            ps.setString(6, email.getText());
            ps.setString(7, career.getText());
            ps.setString(8, marital.getText());
            ps.setString(9, address.getText());
            ps.setString(10, city.getText());
            ps.setString(11, state.getText());
            ps.setString(12, zipcode.getText());
 
            ps.executeUpdate();
                        
            ps.clearParameters();
            
        
    }

    public  int getID() throws SQLException
    {     
        try {
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery("select id from profile where firstname = '" + firstname.getText()
                    + "' and lastname = '" + lastname.getText()
                    + "' and career = '" + career.getText()
                    + "' and birth = '" + birth.getText()
                    + "' and phone = '" + phone.getText()
                    + "' and email = '" + email.getText() + "'");
        
            if(rs.next())
            {
                System.out.println("OK");
            }
          
                      
        } catch (SQLException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        return rs.getInt(1);      
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Capture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField address;
    private javax.swing.JLabel back;
    public static javax.swing.JTextField birth;
    private javax.swing.JLabel capture;
    public static javax.swing.JTextField career;
    public static javax.swing.JTextField city;
    public static javax.swing.JTextField email;
    public static javax.swing.JTextField firstname;
    private javax.swing.JLabel jLabel1;
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
