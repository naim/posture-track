package com.knowlogik.bonevampire;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
    
    // private final String CONTEXT_DEF = "config/OpenNI-Config.xml";
    private final String TEST_KCAP = "data/2012-03-29-15-49.kcap";
    
    private Display display = null;
    
    // private Context context = null;
    // private OutArg<ScriptNode> scriptNode = new OutArg<ScriptNode>();
    // private UserGenerator userGen = null;
    
    public BoneVampire() {
        
        super("BoneVampire");
        
        // Init OpenNI
        // try {
        // context = Context.createFromXmlFile(CONTEXT_DEF, scriptNode);
        // userGen = UserGenerator.create(context);
        // }
        // catch (GeneralException e) {
        // System.err.print(e);
        // }
        
        // Init window
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
        
        display.init();
        setVisible(true);
    }
    
    private BoneCapture loadBoneCaptureFile(String filepath) {
        
        return new BoneCapture();
    }
    
    public static void main(String[] args) {
        
        // Let's rock.
        new BoneVampire();
    }
}
