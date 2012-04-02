package com.knowlogik.bonevampire.display;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


import processing.core.PApplet;

public class Display extends PApplet {
    
    private static final long serialVersionUID = 1L;
    
    private static final int INIT_WIDTH = 1200;
    private static final int INIT_HEIGHT = 800;
    private int canvasWidth = INIT_WIDTH;
    private int canvasHeight = INIT_HEIGHT;
    
    private PeasyCamPlus cam;
    
    public void setup() {
        
        size(INIT_WIDTH, INIT_HEIGHT, P3D);
        background(0);
        
        // smooth();
        frameRate(30);
        
        // Camera
        cam = new PeasyCamPlus(this, 0, 0, 0, 400);
        cam.setMinimumDistance(200);
        cam.setMaximumDistance(2000);
        // Would be nice to rotate a *little* around the y-axis... This locks it in place completely.
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
    
    public void draw() {
        
        // Wipe bg
        background(0);
        
        // Lights
        lights();
        pointLight(205, 205, 205, 120, 120, -200);
        
        // Draw primitives
        // fill(200);
        // noStroke();
        // box(100, 20, 200);
        
        // Draw.. things
        fill(255);
        strokeWeight(4);
        stroke(200, 0, 200);
        beginShape();
        vertex(100, 100, 100);
        vertex(200, 100, 100);
        vertex(200, 200, 100);
        vertex(100, 200, 100);
        vertex(100, 100, 100);
        endShape();
        
        // Draw axes
        strokeWeight(2);
        stroke(255, 0, 0);
        line(-200, 0, 0, 200, 0, 0);
        stroke(0, 255, 0);
        line(0, -200, 0, 0, 200, 0);
        stroke(0, 0, 255);
        line(0, 0, -200, 0, 0, 200);
        
        // Draw orientation blobs
        strokeWeight(13);
        stroke(255, 0, 0);
        point(200, 0, 0);
        stroke(0, 255, 0);
        point(0, 200, 0);
        stroke(0, 0, 255);
        point(0, 0, 200);
        strokeWeight(20);
        stroke(255);
        point(0, 0, 0);
        
        // Meh
        if (mousePressed) {
            // line(mouseX, mouseY, pmouseX, pmouseY);
        }
    }
    
    void resizeFrame() {
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        cam.reset();
    }
    
    // private PGraphics buffer = createGraphics(width, height, P3D);
    // float angle;
    // float cosine;
    // float val = 0;
    
    // public void draw2() {
    //
    // // Fade everything
    // // NOTE This doesn't really work using P3D.. Need to figure out a way to fill a volume.
    // noStroke();
    // fill(0, 0, 0, 10);
    // rect(0, 0, width, height);
    //
    // // Draw ellipses
    // val += 10;
    // stroke(196, 5, 23); // Stroke Color of the ellipse and rectangle/red
    // noFill(); // No fill color for the ellipse
    // println(mouseX); // Print in the ellipse at mouseX position
    //
    // pushMatrix();
    // translate(mouseX, mouseY); // Translate the center of ellipse around mouseX, mouseY
    // ellipseMode(CENTER);
    // rotate(val); // Rotate ellipse around the rising value of 10
    // ellipse(0, 0, 100, 200); // size and position of ellipse
    // popMatrix();
    //
    // // Draw rectangles in a buffer so they don't get faded
    // if (second() % 2 == 0) {
    // cosine = cos(val);
    //
    // buffer.beginDraw();
    // buffer.stroke(196, 5, 23);
    // buffer.noFill();
    // buffer.translate(mouseX, mouseY);
    // buffer.rotate(cosine);
    // buffer.translate(width / 2, height / 2);
    // // Translating the the width and height, distance the rect. spins away from ellipse.
    // buffer.rectMode(CENTER);
    // buffer.rect(0, 0, 115, 115); // Rect. size and position
    // buffer.endDraw();
    // }
    //
    // image(buffer, 0, 0);
    // }
}
