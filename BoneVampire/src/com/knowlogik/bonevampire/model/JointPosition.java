package com.knowlogik.bonevampire.model;

import processing.core.PVector;

public class JointPosition {
    
    private String name;
    private int category;
    private float xPos;
    private float yPos;
    private float zPos;
    
    public JointPosition(String jointPosStr) {
        
        // Joint position string ordering:
        // name ...... [0]
        // category .. [1]
        // x pos ..... [2]
        // y pos ..... [3]
        // z pos ..... [4]
        
        String[] jointPos = jointPosStr.split(",");
        
        name = jointPos[0];
        category = Integer.parseInt(jointPos[1]);
        xPos = Float.parseFloat(jointPos[2]);
        yPos = Float.parseFloat(jointPos[3]);
        zPos = Float.parseFloat(jointPos[4]);
    }
    
    public String getName() {
        return name;
    }
    
    public int getCategory() {
        return category;
    }
    
    public float getX() {
        return xPos;
    }
    
    public float getY() {
        return yPos;
    }
    
    public float getZ() {
        return zPos;
    }
    
    public PVector getPVector() {
        
        return new PVector(xPos, yPos, zPos);
    }
}
