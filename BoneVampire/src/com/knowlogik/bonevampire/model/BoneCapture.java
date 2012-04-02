package com.knowlogik.bonevampire.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoneCapture {
    
    public List<BoneFrame> frames;
    
    public BoneCapture() {
        
        // NOTE
        // If I were controlling the render loop and not Processing, a LinkedList would be better here. As it is
        // it makes more sense just to use a frame counter and use a more random access structure here, even though
        // access will just be sequential for now.
        // frames = new LinkedList<BoneFrame>();
        frames = new ArrayList<BoneFrame>();
    }
    
    public boolean loadFromFile(String filepath) {
        
        System.out.format("Loading %s ...", filepath);
        System.out.flush();
        
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = null;
            while ((line = reader.readLine()) != null) {
                frames.add(new BoneFrame(line));
                if ((frames.size() % 100) == 0)
                    System.out.print(".");
            }
        }
        catch (IOException x) {
            System.err.println();
            System.err.println(x);
            return false;
        }
        
        System.out.println(" done");
        
        System.out.format("Loaded %d BoneFrames", frames.size());
        System.out.println();
        System.out.flush();
        
        return true;
    }
}
