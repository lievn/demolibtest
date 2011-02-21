package demolibtest;

import nodebox.node.*;
import processing.core.PGraphics;
import sms.Unimotion;

@Description("Reads the mac tilt sensor")
@Category("SMSarray")

public class MotionSensor extends Node {

    public final IntPort X = new IntPort(this, "X", Port.Direction.OUTPUT);
    public final IntPort Y = new IntPort(this, "Y", Port.Direction.OUTPUT);
    public final IntPort Z = new IntPort(this, "Z", Port.Direction.OUTPUT);

    @Override
    public void execute(Context context, float time) {
        int[] vals = Unimotion.getSMSArray();
        X.set(vals[0]);
        Y.set(vals[1]);
        Z.set(vals[2]);
    }

    @Override
    public void draw(PGraphics g, Context context, float v) {

    }
}