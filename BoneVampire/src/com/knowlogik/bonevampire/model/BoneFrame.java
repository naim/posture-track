package com.knowlogik.bonevampire.model;

public class BoneFrame {
    
    private long timestamp;
    private JointPosition[] joints = new JointPosition[5];
    
    public BoneFrame(String bFrameStr) {
        
        // Negative lookahead with possessive quantifier FTW
        String[] bFrame = bFrameStr.split(",(?![^\\(\\)]*+\\))");
        
        // Frame string ordering:
        // timestamp .. [0]
        // (HEAD) ..... [1]
        // (NECK) ..... [2]
        // (TORSO) .... [3]
        // (L-SHLDR) .. [4]
        // (R-SHLDR) .. [5]
        
        timestamp = Long.parseLong(bFrame[0]);
        for (int i = 1; i < bFrame.length; i++)
            joints[i - 1] = new JointPosition(bFrame[i].substring(1, bFrame[i].length() - 1));
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public JointPosition getHeadPosition() {
        return joints[0];
    }
    
    public JointPosition getNeckPosition() {
        return joints[1];
    }
    
    public JointPosition getTorsoPosition() {
        return joints[2];
    }
    
    public JointPosition getLeftShoulderPosition() {
        return joints[3];
    }
    
    public JointPosition getRightShoulderPosition() {
        return joints[4];
    }
}
