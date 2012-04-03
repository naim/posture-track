package com.knowlogik.bonevampire.display;

import java.awt.Dimension;

import com.knowlogik.bonevampire.BoneVampire;
import com.knowlogik.bonevampire.model.BoneCapture;
import com.knowlogik.bonevampire.model.BoneFrame;

import processing.core.PApplet;
import processing.core.PVector;

public class Display extends PApplet {
    
    private static final long serialVersionUID = 1L;
    
    private static final int FRAMERATE = 30;
    
    private static final int INIT_WIDTH = 1200;
    private static final int INIT_HEIGHT = 800;
    
    private static final int INIT_ZOOM = 450;
    private static final int MIN_ZOOM = 200;
    private static final int MAX_ZOOM = 1200;
    
    private static final int X_ORIGIN_OFFSET = 0;
    private static final int Y_ORIGIN_OFFSET = 30;
    private static final int Z_ORIGIN_OFFSET = 800;
    
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
            testFrame = new BoneFrame("1333051071,(HEAD,0,0.518809,215.925,867.755),(NECK,0,15.8988,43.4482,992.853),(TORSO,0,15.9471,-168.375,991.344),(L-SHLDR,0,-129.656,43.4301,990.801),(R-SHLDR,0,161.453,43.4662,994.905)");
        
        size(INIT_WIDTH, INIT_HEIGHT, P3D);
        background(0);
        
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
    private int headRadius = 50;
    private float headLength = 1.15f;
    private float headWidth = 1.0f;
    private float headDepth = 1.0f;
    
    private int frameCount = 0;
    
    public void draw() {
        
        BoneFrame frame;
        if (bcap == null)
            frame = testFrame;
        else
            frame = bcap.frames.get(frameCount);
        
        // Wipe background
        background(0);
        
        // Lights
        // NOTE No ambient light for now. Looks pretty "blah".
        // lights();
        pointLight(245, 245, 245, X_ORIGIN_OFFSET, Y_ORIGIN_OFFSET, Z_ORIGIN_OFFSET - 200);
        pointLight(130, 130, 130, X_ORIGIN_OFFSET, Y_ORIGIN_OFFSET, Z_ORIGIN_OFFSET + 2000);
        
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
        point(frame.getHeadPosition().getX(), frame.getHeadPosition().getY(), frame.getHeadPosition().getZ());
        point(frame.getNeckPosition().getX(), frame.getNeckPosition().getY(), frame.getNeckPosition().getZ());
        point(frame.getTorsoPosition().getX(), frame.getTorsoPosition().getY(), frame.getTorsoPosition().getZ());
        point(frame.getLeftShoulderPosition().getX(), frame.getLeftShoulderPosition().getY(), frame.getLeftShoulderPosition().getZ());
        point(frame.getRightShoulderPosition().getX(), frame.getRightShoulderPosition().getY(), frame.getRightShoulderPosition().getZ());
        
        // Draw joint connectors
        strokeWeight(6);
        stroke(100, 0, 100);
        line(frame.getHeadPosition().getX(), frame.getHeadPosition().getY() - (headLength * 80), frame.getHeadPosition().getZ() + headRadius,
                frame.getNeckPosition().getX(), frame.getNeckPosition().getY(), frame.getNeckPosition().getZ());
        line(frame.getNeckPosition().getX(), frame.getNeckPosition().getY(), frame.getNeckPosition().getZ(),
                frame.getTorsoPosition().getX(), frame.getTorsoPosition().getY(), frame.getTorsoPosition().getZ());
        line(frame.getNeckPosition().getX(), frame.getNeckPosition().getY(), frame.getNeckPosition().getZ(),
                frame.getLeftShoulderPosition().getX(), frame.getLeftShoulderPosition().getY(), frame.getLeftShoulderPosition().getZ());
        line(frame.getNeckPosition().getX(), frame.getNeckPosition().getY(), frame.getNeckPosition().getZ(),
                frame.getRightShoulderPosition().getX(), frame.getRightShoulderPosition().getY(), frame.getRightShoulderPosition().getZ());
        line(frame.getTorsoPosition().getX(), frame.getTorsoPosition().getY(), frame.getTorsoPosition().getZ(),
                frame.getLeftShoulderPosition().getX(), frame.getLeftShoulderPosition().getY(), frame.getLeftShoulderPosition().getZ());
        line(frame.getTorsoPosition().getX(), frame.getTorsoPosition().getY(), frame.getTorsoPosition().getZ(),
                frame.getRightShoulderPosition().getX(), frame.getRightShoulderPosition().getY(), frame.getRightShoulderPosition().getZ());
        
        // Set style for primitives
        noStroke();
        fill(255, 160);
        
        // Head
        pushMatrix();
        translate(frame.getHeadPosition().getX(), frame.getHeadPosition().getY() - (headLength * 40), frame.getHeadPosition().getZ() + headRadius + 5);
        scale(headWidth, headLength, headDepth);
        sphere(headRadius);
        popMatrix();
        
        // Neck
        drawBoxAlongLine(frame.getHeadPosition().getX(), frame.getHeadPosition().getY() - (headLength * 80), frame.getHeadPosition().getZ() + headRadius,
                frame.getNeckPosition().getX(), frame.getNeckPosition().getY(), frame.getNeckPosition().getZ(), 30);
        
        // Left Shoulder
        drawBoxAlongLine(frame.getLeftShoulderPosition().getX(), frame.getLeftShoulderPosition().getY(), frame.getLeftShoulderPosition().getZ(),
                frame.getNeckPosition().getX(), frame.getNeckPosition().getY(), frame.getNeckPosition().getZ(), 30);
        drawBoxAlongLine(frame.getLeftShoulderPosition().getX(), frame.getLeftShoulderPosition().getY(), frame.getLeftShoulderPosition().getZ(),
                frame.getTorsoPosition().getX(), frame.getTorsoPosition().getY(), frame.getTorsoPosition().getZ(), 30);
        
        // Right Shoulder
        drawBoxAlongLine(frame.getRightShoulderPosition().getX(), frame.getRightShoulderPosition().getY(), frame.getRightShoulderPosition().getZ(),
                frame.getNeckPosition().getX(), frame.getNeckPosition().getY(), frame.getNeckPosition().getZ(), 30);
        drawBoxAlongLine(frame.getRightShoulderPosition().getX(), frame.getRightShoulderPosition().getY(), frame.getRightShoulderPosition().getZ(),
                frame.getTorsoPosition().getX(), frame.getTorsoPosition().getY(), frame.getTorsoPosition().getZ(), 30);
        
        // Torso
        drawSphereAlongLine(frame.getNeckPosition().getX(), frame.getNeckPosition().getY(), frame.getNeckPosition().getZ(),
                frame.getTorsoPosition().getX(), frame.getTorsoPosition().getY(), frame.getTorsoPosition().getZ(), 100);
        
        // Increment the counter; reset if needed (just loop things for now)
        // TODO Eventually some sort of time scrubbing or speed control is needed
        frameCount++;
        if (bcap != null && frameCount >= bcap.frames.size())
            frameCount = 0;
    }
    
    void drawBoxAlongLine(float x1, float y1, float z1, float x2, float y2, float z2, float weight)
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
        box(weight, weight, (p1.dist(p2) * 0.6f));
        popMatrix();
    }
    
    void drawSphereAlongLine(float x1, float y1, float z1, float x2, float y2, float z2, float radius)
    {
        PVector v1 = new PVector(x2 - x1, y2 - y1, z2 - z1);
        float rho = sqrt(pow(v1.x, 2) + pow(v1.y, 2) + pow(v1.z, 2));
        float phi = acos(v1.z / rho);
        float the = atan2(v1.y, v1.x);
        v1.mult(0.5f);
        
        pushMatrix();
        translate(x1, y1, z1);
        translate(v1.x, v1.y, v1.z);
        // rotateZ(the);
        // rotateY(phi);
        sphere(radius);
        popMatrix();
    }
    
    void resizeFrame() {
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        cam.reset();
    }
}
