package com.knowlogik.bonevampire.camera;

import peasy.CameraState;
import peasy.PeasyCam;
import processing.core.PApplet;

public class PeasyCamPlus extends PeasyCam {
    
    public PeasyCamPlus(PApplet parent, double distance) {
        super(parent, distance);
    }
    
    public PeasyCamPlus(PApplet parent, double lookAtX, double lookAtY, double lookAtZ, double distance) {
        super(parent, lookAtX, lookAtY, lookAtZ, distance);
    }
    
    private long resetAnimationDurationInMillis = 500L;
    private CameraState defaultCameraState = null;
    
    public void setDefaultCameraState() {
        this.defaultCameraState = this.getState();
    }
    
    public void setResetAnimationDuration(long resetAnimationDurationInMillis) {
        this.resetAnimationDurationInMillis = resetAnimationDurationInMillis;
    }
    
    @Override
    public void reset() {
        if (defaultCameraState != null)
            reset(resetAnimationDurationInMillis, defaultCameraState);
        else
            reset(resetAnimationDurationInMillis);
    }
    
    public void reset(long animationTimeInMillis, CameraState state) {
        setState(state, animationTimeInMillis);
    }
}
