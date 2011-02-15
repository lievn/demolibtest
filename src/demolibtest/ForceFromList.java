package demolibtest;


import nodebox.node.*;
import processing.core.PGraphics;


public class ForceFromList extends Node{

    public final ForceList allForces = new ForceList(this, "forces", Port.Direction.INPUT);
    public final IntPort index = new IntPort(this, "index", Port.Direction.INPUT);
    public final FloatPort X = new FloatPort(this, "X", Port.Direction.OUTPUT);
    public final FloatPort Y = new FloatPort(this, "Y", Port.Direction.OUTPUT);


@Override
    public void execute(Context context, float time) {
            Force f;
            f = allForces.get().get(index.get());
            X.set(f.x);
            Y.set(f.y);
    }


    @Override
    public void draw(PGraphics g, Context context, float v) {


    }

}
