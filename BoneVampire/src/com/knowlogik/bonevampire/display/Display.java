package com.knowlogik.bonevampire.display;

import java.awt.Dimension;

import com.knowlogik.bonevampire.BoneVampire;
import com.knowlogik.bonevampire.model.BoneCapture;
import com.knowlogik.bonevampire.model.BoneFrame;

import processing.core.PApplet;
import processing.core.PVector;

public class Display extends PApplet {
    
    private static final long serialVersionUID = 1L;
    
    private static final int INIT_WIDTH = 1200;
    private static final int INIT_HEIGHT = 800;
    
    private static final int BACKGROUND = 40;
    
    private static final int FRAMERATE = 30;
    
    private static final int INIT_ZOOM = 450;
    private static final int MIN_ZOOM = 200;
    private static final int MAX_ZOOM = 1200;
    
    private static final int X_ORIGIN_OFFSET = 0;
    private static final int Y_ORIGIN_OFFSET = 30;
    private static final int Z_ORIGIN_OFFSET = 1000;
    
    private static final int AXES_BRIGHTNESS = 150;
    private static final int AXES_LENGTH = 50;
    
    private BoneCapture bcap;
    private BoneFrame testFrame;
    
    private int canvasWidth = INIT_WIDTH;
    private int canvasHeight = INIT_HEIGHT;
    
    private PeasyCamPlus cam;
    
    public void init(BoneVampire bvApp) {
        
        bcap = bvApp.getBoneCap();
        super.init();
    }
    
    public void setup() {
        
        // For testing purposes
        if (bcap == null)
            testFrame = new BoneFrame("1333464900,(HEAD,0,-19.4021,414.436,1345.27),(NECK,0,-31.7123,201.76,1455.54),(TORSO,0,-26.4751,31.5478,1460.85),(L-SHLDR,0,-228.629,196.01,1465.45),(R-SHLDR,0,165.204,207.509,1445.63),(L-ELBOW,0,-318.873,-7.48296,1544.35),(R-ELBOW,0,268.666,14.9624,1535.07),(L-HAND,0,-308.394,-198.886,1406.39),(R-HAND,0,287.179,-182.998,1407.61),(L-HIP,0,-173.851,-143.12,1473.84),(R-HIP,0,131.376,-134.208,1458.48)");
        
        size(INIT_WIDTH, INIT_HEIGHT, P3D);
        background(BACKGROUND);
        
        // NOTE Alas, no smooth rendering with P3D...
        // smooth();
        frameRate(FRAMERATE);
        
        // Camera -- now enhanced!
        cam = new PeasyCamPlus(this, X_ORIGIN_OFFSET, Y_ORIGIN_OFFSET, Z_ORIGIN_OFFSET, INIT_ZOOM);
        cam.setMinimumDistance(MIN_ZOOM);
        cam.setMaximumDistance(MAX_ZOOM);
        // This locks the camera around the y-axis completely. Would be nice if I could specify min/max angles...
        // cam.setYawRotationMode();
        
        // Get reoriented
        cam.rotateX(PI);
        
        // This sets our current view as the default camera for a reset event
        cam.setDefaultCameraState();
        
        // Resize help
        registerPre(this);
    }
    
    public void pre() {
        if (width != canvasWidth || height != canvasHeight) {
            resizeFrame();
        }
    }
    
    // Anatomical properties
    private int headRadius = 85;
    private float headLength = 1.18f;
    private float headWidth = 1.0f;
    private float headDepth = 1.1f;
    private int shoulderRadius = 40;
    private int elbowRadius = 25;
    private int handRadius = 30;
    
    private int frameCount = 0;
    
    public void draw() {
        
        BoneFrame frame;
        if (bcap == null)
            frame = testFrame;
        else
            frame = bcap.frames.get(frameCount);
        
        // Wipe buffer
        background(BACKGROUND);
        
        // Lights
        pointLight(245, 245, 245, X_ORIGIN_OFFSET, Y_ORIGIN_OFFSET, Z_ORIGIN_OFFSET - 200);
        pointLight(100, 100, 100, X_ORIGIN_OFFSET, Y_ORIGIN_OFFSET, Z_ORIGIN_OFFSET + 2000);
        
        // NOTE Translating this when the camera moves is kind of a pain; maybe it's not a great idea.
        // spotLight(255, 0, 0, mouseX - (canvasWidth / 2), -mouseY + (canvasHeight / 2), Z_ORIGIN_OFFSET, 0, 0, 1, PI / 10, 2);
        
        // Draw axes
        strokeWeight(2);
        stroke(AXES_BRIGHTNESS, 0, 0);
        line(-AXES_LENGTH + X_ORIGIN_OFFSET, 0 + Y_ORIGIN_OFFSET, 0 + Z_ORIGIN_OFFSET,
                AXES_LENGTH + X_ORIGIN_OFFSET, 0 + Y_ORIGIN_OFFSET, 0 + Z_ORIGIN_OFFSET);
        stroke(0, AXES_BRIGHTNESS, 0);
        line(0 + X_ORIGIN_OFFSET, -AXES_LENGTH + Y_ORIGIN_OFFSET, 0 + Z_ORIGIN_OFFSET,
                0 + X_ORIGIN_OFFSET, AXES_LENGTH + Y_ORIGIN_OFFSET, 0 + Z_ORIGIN_OFFSET);
        stroke(0, 0, AXES_BRIGHTNESS);
        line(0 + X_ORIGIN_OFFSET, 0 + Y_ORIGIN_OFFSET, -AXES_LENGTH + Z_ORIGIN_OFFSET,
                0 + X_ORIGIN_OFFSET, 0 + Y_ORIGIN_OFFSET, AXES_LENGTH + Z_ORIGIN_OFFSET);
        
        // Draw orientation blobs
        strokeWeight(8);
        stroke(AXES_BRIGHTNESS, 0, 0);
        point(AXES_LENGTH + X_ORIGIN_OFFSET, 0 + Y_ORIGIN_OFFSET, 0 + Z_ORIGIN_OFFSET);
        stroke(0, AXES_BRIGHTNESS, 0);
        point(0 + X_ORIGIN_OFFSET, AXES_LENGTH + Y_ORIGIN_OFFSET, 0 + Z_ORIGIN_OFFSET);
        stroke(0, 0, AXES_BRIGHTNESS);
        point(0 + X_ORIGIN_OFFSET, 0 + Y_ORIGIN_OFFSET, AXES_LENGTH + Z_ORIGIN_OFFSET);
        
        // Draw origin
        strokeWeight(13);
        stroke(AXES_BRIGHTNESS + 50);
        point(0 + X_ORIGIN_OFFSET, 0 + Y_ORIGIN_OFFSET, 0 + Z_ORIGIN_OFFSET);
        
//        // Draw.. things
//        fill(255, 200);
//        strokeWeight(2);
//        stroke(255);
//        beginShape();
//        vertex(100, 100, 1000);
//        vertex(200, 100, 1000);
//        vertex(200, 200, 1000);
//        vertex(100, 200, 1000);
//        vertex(100, 100, 1000);
//        endShape();
        
        // Draw joints
        strokeWeight(20);
        stroke(200, 0, 200);
        point(frame.getHead().getX(), frame.getHead().getY(), frame.getHead().getZ());
        point(frame.getNeck().getX(), frame.getNeck().getY(), frame.getNeck().getZ());
        point(frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ());
        point(frame.getLeftShoulder().getX(), frame.getLeftShoulder().getY(), frame.getLeftShoulder().getZ());
        point(frame.getRightShoulder().getX(), frame.getRightShoulder().getY(), frame.getRightShoulder().getZ());
        point(frame.getLeftElbow().getX(), frame.getLeftElbow().getY(), frame.getLeftElbow().getZ());
        point(frame.getRightElbow().getX(), frame.getRightElbow().getY(), frame.getRightElbow().getZ());
        point(frame.getLeftHand().getX(), frame.getLeftHand().getY(), frame.getLeftHand().getZ());
        point(frame.getRightHand().getX(), frame.getRightHand().getY(), frame.getRightHand().getZ());
        point(frame.getLeftHip().getX(), frame.getLeftHip().getY(), frame.getLeftHip().getZ());
        point(frame.getRightHip().getX(), frame.getRightHip().getY(), frame.getRightHip().getZ());
        
        // Draw joint connectors
        strokeWeight(6);
        stroke(100, 0, 100);
        line(frame.getHead().getX(), frame.getHead().getY() - (headLength * 80), frame.getHead().getZ() + (headRadius / 4),
                frame.getNeck().getX(), frame.getNeck().getY(), frame.getNeck().getZ());
        line(frame.getNeck().getX(), frame.getNeck().getY(), frame.getNeck().getZ(),
                frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ());
        line(frame.getNeck().getX(), frame.getNeck().getY(), frame.getNeck().getZ(),
                frame.getLeftShoulder().getX(), frame.getLeftShoulder().getY(), frame.getLeftShoulder().getZ());
        line(frame.getNeck().getX(), frame.getNeck().getY(), frame.getNeck().getZ(),
                frame.getRightShoulder().getX(), frame.getRightShoulder().getY(), frame.getRightShoulder().getZ());
        line(frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(),
                frame.getLeftShoulder().getX(), frame.getLeftShoulder().getY(), frame.getLeftShoulder().getZ());
        line(frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(),
                frame.getRightShoulder().getX(), frame.getRightShoulder().getY(), frame.getRightShoulder().getZ());
        line(frame.getLeftShoulder().getX(), frame.getLeftShoulder().getY(), frame.getLeftShoulder().getZ(),
                frame.getLeftElbow().getX(), frame.getLeftElbow().getY(), frame.getLeftElbow().getZ());
        line(frame.getRightShoulder().getX(), frame.getRightShoulder().getY(), frame.getRightShoulder().getZ(),
                frame.getRightElbow().getX(), frame.getRightElbow().getY(), frame.getRightElbow().getZ());
        line(frame.getLeftElbow().getX(), frame.getLeftElbow().getY(), frame.getLeftElbow().getZ(),
                frame.getLeftHand().getX(), frame.getLeftHand().getY(), frame.getLeftHand().getZ());
        line(frame.getRightElbow().getX(), frame.getRightElbow().getY(), frame.getRightElbow().getZ(),
                frame.getRightHand().getX(), frame.getRightHand().getY(), frame.getRightHand().getZ());
        line(frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(),
                frame.getLeftHip().getX(), frame.getLeftHip().getY(), frame.getLeftHip().getZ());
        line(frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(),
                frame.getRightHip().getX(), frame.getRightHip().getY(), frame.getRightHip().getZ());
        line(frame.getLeftHip().getX(), frame.getLeftHip().getY(), frame.getLeftHip().getZ(),
                frame.getRightHip().getX(), frame.getRightHip().getY(), frame.getRightHip().getZ());
        stroke(70, 0, 70);
        line(frame.getLeftShoulder().getX(), frame.getLeftShoulder().getY(), frame.getLeftShoulder().getZ(),
                frame.getLeftHip().getX(), frame.getLeftHip().getY(), frame.getLeftHip().getZ());
        line(frame.getRightShoulder().getX(), frame.getRightShoulder().getY(), frame.getRightShoulder().getZ(),
                frame.getRightHip().getX(), frame.getRightHip().getY(), frame.getRightHip().getZ());
        
        // Set style for primitives
        noStroke();
        fill(200, 150);
        
        // Neck
        pushMatrix();
        translate(frame.getNeck().getX(), frame.getNeck().getY(), frame.getNeck().getZ());
        scale(1, 0.6f, 0.6f);
        sphere(35);
        popMatrix();
        drawBoxAlongLine(frame.getHead().getX(), frame.getHead().getY() - (headLength * 80), frame.getHead().getZ() + (headRadius / 4),
                frame.getNeck().getX(), frame.getNeck().getY(), frame.getNeck().getZ(), 30);
        
        // Head
        pushMatrix();
        translate(frame.getHead().getX(), frame.getHead().getY() - (headLength * 40), frame.getHead().getZ() + (headRadius / 4));
        scale(headWidth, headLength, headDepth);
        sphere(headRadius);
        popMatrix();
        
        // Left Shoulder
        pushMatrix();
        translate(frame.getLeftShoulder().getX(), frame.getLeftShoulder().getY(), frame.getLeftShoulder().getZ());
        sphere(shoulderRadius);
        popMatrix();
        drawBoxAlongLine(frame.getLeftShoulder().getX(), frame.getLeftShoulder().getY(), frame.getLeftShoulder().getZ(),
                frame.getNeck().getX(), frame.getNeck().getY(), frame.getNeck().getZ(), 30);
        drawBoxAlongLine(frame.getLeftShoulder().getX(), frame.getLeftShoulder().getY(), frame.getLeftShoulder().getZ(),
                frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(), 30);
        
        // Right Shoulder
        pushMatrix();
        translate(frame.getRightShoulder().getX(), frame.getRightShoulder().getY(), frame.getRightShoulder().getZ());
        sphere(shoulderRadius);
        popMatrix();
        drawBoxAlongLine(frame.getRightShoulder().getX(), frame.getRightShoulder().getY(), frame.getRightShoulder().getZ(),
                frame.getNeck().getX(), frame.getNeck().getY(), frame.getNeck().getZ(), 30);
        drawBoxAlongLine(frame.getRightShoulder().getX(), frame.getRightShoulder().getY(), frame.getRightShoulder().getZ(),
                frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(), 30);
        
        // Torso
        drawBoxAlongLine(frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(),
                frame.getLeftHip().getX(), frame.getLeftHip().getY(), frame.getLeftHip().getZ(), 45);
        drawBoxAlongLine(frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(),
                frame.getRightHip().getX(), frame.getRightHip().getY(), frame.getRightHip().getZ(), 45);
        drawBoxAlongLine(frame.getLeftHip().getX(), frame.getLeftHip().getY(), frame.getLeftHip().getZ(),
                frame.getRightHip().getX(), frame.getRightHip().getY(), frame.getRightHip().getZ(), 45);
        pushMatrix();
        translate(frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ());
        scale(0.9f, 1, 0.5f);
        sphere(160);
        popMatrix();
//        drawSphereAlongLine(frame.getNeck().getX(), frame.getNeck().getY(), frame.getNeck().getZ(),
//                frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(), 100, 0.9f, 1, 0.5f);
//        drawSphereAlongLine(frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(),
//                frame.getLeftHip().getX(), frame.getLeftHip().getY(), frame.getLeftHip().getZ(), 70, 0.9f, 1, 0.5f);
//        drawSphereAlongLine(frame.getTorso().getX(), frame.getTorso().getY(), frame.getTorso().getZ(),
//                frame.getRightHip().getX(), frame.getRightHip().getY(), frame.getRightHip().getZ(), 70, 0.9f, 1, 0.5f);
        
        // Left Arm
        drawBoxAlongLine(frame.getLeftShoulder().getX(), frame.getLeftShoulder().getY(), frame.getLeftShoulder().getZ(),
                frame.getLeftElbow().getX(), frame.getLeftElbow().getY(), frame.getLeftElbow().getZ(), 30);
        drawBoxAlongLine(frame.getLeftElbow().getX(), frame.getLeftElbow().getY(), frame.getLeftElbow().getZ(),
                frame.getLeftHand().getX(), frame.getLeftHand().getY(), frame.getLeftHand().getZ(), 30);
        pushMatrix();
        translate(frame.getLeftElbow().getX(), frame.getLeftElbow().getY(), frame.getLeftElbow().getZ());
        sphere(elbowRadius);
        popMatrix();
        pushMatrix();
        translate(frame.getLeftHand().getX(), frame.getLeftHand().getY(), frame.getLeftHand().getZ());
        sphere(handRadius);
        popMatrix();
        
        // Right Arm
        drawBoxAlongLine(frame.getRightShoulder().getX(), frame.getRightShoulder().getY(), frame.getRightShoulder().getZ(),
                frame.getRightElbow().getX(), frame.getRightElbow().getY(), frame.getRightElbow().getZ(), 30);
        drawBoxAlongLine(frame.getRightElbow().getX(), frame.getRightElbow().getY(), frame.getRightElbow().getZ(),
                frame.getRightHand().getX(), frame.getRightHand().getY(), frame.getRightHand().getZ(), 30);
        pushMatrix();
        translate(frame.getRightElbow().getX(), frame.getRightElbow().getY(), frame.getRightElbow().getZ());
        sphere(elbowRadius);
        popMatrix();
        pushMatrix();
        translate(frame.getRightHand().getX(), frame.getRightHand().getY(), frame.getRightHand().getZ());
        sphere(handRadius);
        popMatrix();
        
        // Increment the counter; reset if needed (just loop things for now)
        // TODO Eventually some sort of time scrubbing or speed control is needed
        frameCount++;
        if (bcap != null && frameCount >= bcap.frames.size())
            frameCount = 0;
    }
    
    private void drawBoxAlongLine(float x1, float y1, float z1, float x2, float y2, float z2, float weight)
    {
        PVector p1 = new PVector(x1, y1, z1);
        PVector p2 = new PVector(x2, y2, z2);
        PVector v1 = new PVector(x2 - x1, y2 - y1, z2 - z1);
        float rho = sqrt(pow(v1.x, 2) + pow(v1.y, 2) + pow(v1.z, 2));
        float phi = acos(v1.z / rho);
        float the = atan2(v1.y, v1.x);
        v1.mult(0.5f);
        
        pushMatrix();
        translate(x1, y1, z1);
        translate(v1.x, v1.y, v1.z);
        rotateZ(the);
        rotateY(phi);
        box(weight, weight, (p1.dist(p2) * 0.7f));
        popMatrix();
    }
    
    private void drawSphereAlongLine(float x1, float y1, float z1, float x2, float y2, float z2, float radius, float sx, float sy, float sz)
    {
        PVector v1 = new PVector(x2 - x1, y2 - y1, z2 - z1);
        v1.mult(0.5f);
        
        pushMatrix();
        translate(x1, y1, z1);
        translate(v1.x, v1.y, v1.z);
        scale(sx, sy, sz);
        sphere(radius);
        popMatrix();
    }
    
    private void resizeFrame() {
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        cam.reset();
    }
}
