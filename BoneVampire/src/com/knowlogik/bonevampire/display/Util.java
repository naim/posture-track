package com.knowlogik.bonevampire.display;

import processing.core.PApplet;
import processing.core.PVector;

public class Util {
    
    public static PVector cartesianToPolar(PVector vector) {
        PVector result = new PVector();
        result.x = vector.mag();
        if (result.x > 0) {
            result.y = -PApplet.atan2(vector.z, vector.x);
            result.z = PApplet.asin(vector.y / result.x);
        }
        else {
            result.y = 0;
            result.z = 0;
        }
        return result;
    }
}
