package demolibtest;

import nodebox.node.*;
import processing.core.PGraphics;
import tuio.TuioObject;

@Description("Reads a Tuio object")
@Category("Tuio")


public class ObjectFromList extends Node{

    public final ObjectList allObjects = new ObjectList(this, "objects", Port.Direction.INPUT);

    public final IntPort index = new IntPort(this, "index", Port.Direction.INPUT);
    public final IntPort width = new IntPort(this, "width", Port.Direction.INPUT,320);
    public final IntPort height = new IntPort(this, "height", Port.Direction.INPUT,240);
    public final FloatPort pX = new FloatPort(this, "X", Port.Direction.OUTPUT);
    public final FloatPort pY = new FloatPort(this, "Y", Port.Direction.OUTPUT);
    //public final FloatPort pM = new FloatPort(this, "M", Port.Direction.OUTPUT);
    public final FloatPort pA = new FloatPort(this, "A", Port.Direction.OUTPUT);


@Override
    public void execute(Context context, float time) {
            TuioObject tobj;
            tobj = allObjects.get().get(index.get());
            pX.set(tobj.getScreenX(width.get()));
            pY.set(tobj.getScreenY(height.get()));
            //pM.set(tobj.getMotionSpeed());
            pA.set(tobj.getAngle());
    }

    @Override
    public void draw(PGraphics g, Context context, float v) {

    }

}
