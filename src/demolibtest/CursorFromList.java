package demolibtest;

import nodebox.node.*;
import processing.core.PGraphics;
import tuio.TuioCursor;

@Description("Reads a Tuio (finger) cursor")
@Category("Tuio")


public class CursorFromList extends Node{

    public final CursorList allCursors = new CursorList(this, "cursors", Port.Direction.INPUT);

    public final IntPort index = new IntPort(this, "index", Port.Direction.INPUT);
    public final IntPort width = new IntPort(this, "width", Port.Direction.INPUT,320);
    public final IntPort height = new IntPort(this, "height", Port.Direction.INPUT,240);
    public final FloatPort pX = new FloatPort(this, "X", Port.Direction.OUTPUT);
    public final FloatPort pY = new FloatPort(this, "Y", Port.Direction.OUTPUT);
    public final FloatPort pM = new FloatPort(this, "M", Port.Direction.OUTPUT);
    public final FloatPort pA = new FloatPort(this, "A", Port.Direction.OUTPUT);


@Override
    public void execute(Context context, float time) {
            TuioCursor tcur;
            tcur = allCursors.get().get(index.get());
            pX.set(tcur.getScreenX(width.get()));
            pY.set(tcur.getScreenY(height.get()));
            pM.set(tcur.getMotionSpeed());
            pA.set(tcur.getMotionAccel());
    }

    @Override
    public void draw(PGraphics g, Context context, float v) {


    }

}
