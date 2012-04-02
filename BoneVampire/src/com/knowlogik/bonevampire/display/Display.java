package com.knowlogik.bonevampire.display;

import java.awt.Dimension;

import com.knowlogik.bonevampire.BoneVampire;
import com.knowlogik.bonevampire.model.BoneCapture;
import com.knowlogik.bonevampire.model.BoneFrame;

import processing.core.PApplet;

public class Display extends PApplet {
    
    private static final long serialVersionUID = 1L;
    
    private static final int FRAMERATE = 30;
    
    private static final int INIT_WIDTH = 1200;
    private static final int INIT_HEIGHT = 800;
    
    private static final int INIT_ZOOM = 450;
    private static final int MIN_ZOOM = 200;
    private static final int MAX_ZOOM = 1000;
    
    private static final int X_ORIGIN_OFFSET = 0;
    private static final int Y_ORIGIN_OFFSET = 30;
    private static final int Z_ORIGIN_OFFSET = 800;
    
    private static final int AXES_BRIGHTNESS = 150;
    private static final int AXES_LENGTH = 100;
    
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
        pointLight(30, 30, 30, X_ORIGIN_OFFSET, Y_ORIGIN_OFFSET, Z_ORIGIN_OFFSET + 2000);
        
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
        strokeWeight(13);
        stroke(AXES_BRIGHTNESS, 0, 0);
        point(AXES_LENGTH + X_ORIGIN_OFFSET, 0 + Y_ORIGIN_OFFSET, 0 + Z_ORIGIN_OFFSET);
        stroke(0, AXES_BRIGHTNESS, 0);
        point(0 + X_ORIGIN_OFFSET, AXES_LENGTH + Y_ORIGIN_OFFSET, 0 + Z_ORIGIN_OFFSET);
        stroke(0, 0, AXES_BRIGHTNESS);
        point(0 + X_ORIGIN_OFFSET, 0 + Y_ORIGIN_OFFSET, AXES_LENGTH + Z_ORIGIN_OFFSET);
        
        // Draw origin
        strokeWeight(20);
        stroke(AXES_BRIGHTNESS);
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
        
        // Head
        fill(255, 200);
        noStroke();
        translate(frame.getHeadPosition().getX(), frame.getHeadPosition().getY(), frame.getHeadPosition().getZ() + headRadius + 5);
        scale(headWidth, headLength, headDepth);
        sphere(headRadius);
        
        // Increment the counter; reset if needed (just loop things for now)
        // TODO Eventually some sort of time scrubbing or speed control is needed
        frameCount++;
        if (bcap != null && frameCount >= bcap.frames.size())
            frameCount = 0;
    }
    
    void resizeFrame() {
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        cam.reset();
    }
    
//    private PGraphics buffer = createGraphics(width, height, P3D);
//    float angle;
//    float cosine;
//    float val = 0;
//    
//    public void draw() {
//        
//        // Fade everything
//        // NOTE This doesn't really work using P3D. Need to figure out a way to fill a volume.
//        noStroke();
//        fill(0, 0, 0, 10);
//        rect(0, 0, width, height);
//        
//        // Draw ellipses
//        val += 10;
//        stroke(196, 5, 23); // Stroke Color of the ellipse and rectangle/red
//        noFill(); // No fill color for the ellipse
//        println(mouseX); // Print in the ellipse at mouseX position
//        
//        pushMatrix();
//        translate(mouseX, mouseY); // Translate the center of ellipse around mouseX, mouseY
//        ellipseMode(CENTER);
//        rotate(val); // Rotate ellipse around the rising value of 10
//        ellipse(0, 0, 100, 200); // size and position of ellipse
//        popMatrix();
//        
//        // Draw rectangles in a buffer so they don't get faded
//        if (second() % 2 == 0) {
//            cosine = cos(val);
//            
//            buffer.beginDraw();
//            buffer.stroke(196, 5, 23);
//            buffer.noFill();
//            buffer.translate(mouseX, mouseY);
//            buffer.rotate(cosine);
//            buffer.translate(width / 2, height / 2);
//            // Translating the the width and height, distance the rect. spins away from ellipse.
//            buffer.rectMode(CENTER);
//            buffer.rect(0, 0, 115, 115); // Rect. size and position
//            buffer.endDraw();
//        }
//        
//        image(buffer, 0, 0);
//    }
}
