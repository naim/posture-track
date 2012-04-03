package com.knowlogik.bonevampire;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.knowlogik.bonevampire.display.Display;
import com.knowlogik.bonevampire.model.BoneCapture;

// import org.OpenNI.Context;
// import org.OpenNI.GeneralException;
// import org.OpenNI.OutArg;
// import org.OpenNI.ScriptNode;
// import org.OpenNI.UserGenerator;

public class BoneVampire extends Frame {
    
    private static final long serialVersionUID = 1L;
    
//    private final String CONTEXT_DEF = "config/OpenNI-Config.xml";
    //private final String TEST_BONE_CAP = "data/2012-03-29-15-49.bcap";
    private final String TEST_BONE_CAP = "data/2012-04-03-11-10.bcap";
    
    private Display display;
    private BoneCapture boneCap;
    
//    private Context context = null;
//    private OutArg<ScriptNode> scriptNode = new OutArg<ScriptNode>();
//    private UserGenerator userGen = null;
    
    public BoneVampire() {
        
        super("BoneVampire");
        
        // Init OpenNI
        // TODO Would be nice to have a real-time mode as well. For now use my bonetracker prog to record bcap files.
//        try {
//            context = Context.createFromXmlFile(CONTEXT_DEF, scriptNode);
//            userGen = UserGenerator.create(context);
//        }
//        catch (GeneralException e) {
//            System.err.println(e);
//        }
        
        // Load bonecap file
        boneCap = new BoneCapture();
        boneCap.loadFromFile(TEST_BONE_CAP);
        
        // NOTE
        // Alright, so, for now since I'm just loading a bcap file and I already have all the data I can just
        // pass off this object and let the Processing render loop handle everything. But when real-time support
        // is added that won't work. Capture will happen in the main thread, and there will need to be a sync
        // block to feed new data to the render thread. Won't be hard, just not something I have time for now.
        
        // Init display/window
        display = new Display();
        
        setSize(1200, 800);
        setResizable(true);
        setLayout(new BorderLayout());
        add(display, BorderLayout.CENTER);
        
        addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                Window window = e.getWindow();
                window.setVisible(false);
                window.dispose();
                System.exit(0);
            }
        });
        
        display.init(this);
        setVisible(true);
    }
    
    public BoneCapture getBoneCap() {
        return boneCap;
    }
    
    public static void main(String[] args) {
        
        // Let's rock.
        new BoneVampire();
    }
}
