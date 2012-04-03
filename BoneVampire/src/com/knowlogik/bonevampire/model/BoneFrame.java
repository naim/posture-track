package com.knowlogik.bonevampire.model;

public class BoneFrame {
    
    private long timestamp;
    private JointPosition[] joints;
    
    public BoneFrame(String bFrameStr) {
        
        // Negative lookahead with possessive quantifier FTW
        String[] bFrame = bFrameStr.split(",(?![^\\(\\)]*+\\))");
        
        joints = new JointPosition[bFrame.length - 1];
        
        // Frame string ordering:
        // timestamp ... [0]
        // (HEAD) ...... [1]
        // (NECK) ...... [2]
        // (TORSO) ..... [3]
        // (L-SHLDR) ... [4]
        // (R-SHLDR) ... [5]
        // (L-ELBOW) ... [6]
        // (R-ELBOW) ... [7]
        // (L-HAND) .... [8]
        // (R-HAND) .... [9]
        // (L-HIP) .... [10]
        // (R-HIP) .... [11]
        
        timestamp = Long.parseLong(bFrame[0]);
        for (int i = 1; i < bFrame.length; i++)
            joints[i - 1] = new JointPosition(bFrame[i].substring(1, bFrame[i].length() - 1));
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public JointPosition getHead() {
        return joints[0];
    }
    
    public JointPosition getNeck() {
        return joints[1];
    }
    
    public JointPosition getTorso() {
        return joints[2];
    }
    
    public JointPosition getLeftShoulder() {
        return joints[3];
    }
    
    public JointPosition getRightShoulder() {
        return joints[4];
    }
    
    public JointPosition getLeftElbow() {
        return joints[5];
    }
    
    public JointPosition getRightElbow() {
        return joints[6];
    }
    
    public JointPosition getLeftHand() {
        return joints[7];
    }
    
    public JointPosition getRightHand() {
        return joints[8];
    }
    
    public JointPosition getLeftHip() {
        return joints[9];
    }
    
    public JointPosition getRightHip() {
        return joints[10];
    }
}
