package com.knowlogik.bonevampire.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BoneCapture {
    
    public List<BoneFrame> frames;
    
    public BoneCapture() {
        
        frames = new LinkedList<BoneFrame>();
    }
    
    public boolean loadFromFile(String filepath) {
        
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = null;
            while ((line = reader.readLine()) != null) {
                frames.add(new BoneFrame(line));
            }
        }
        catch (IOException x) {
            System.err.println(x);
            return false;
        }
        
        System.out.format("Loaded %d BoneFrames", frames.size());
        System.out.println();
        
        return true;
    }
}
