package demolibtest;

import blobDetection.EdgeVertex;
import nodebox.node.*;
import processing.core.PGraphics;


@Description("Select and Edge from the edgelist")
@Category("Blobdetection")

public class EdgeFromList extends Node{

    public final EdgeList allEdges = new EdgeList(this, "edges", Port.Direction.INPUT);
    public final IntPort index = new IntPort(this, "index", Port.Direction.INPUT);
    public final FloatPort width = new FloatPort(this, "width", Port.Direction.INPUT,320.0f);
    public final FloatPort height = new FloatPort(this, "height", Port.Direction.INPUT,240.0f);
    public final FloatPort X = new FloatPort(this, "X", Port.Direction.OUTPUT);
    public final FloatPort Y = new FloatPort(this, "Y", Port.Direction.OUTPUT);


@Override
    public void execute(Context context, float time) {
            EdgeVertex a,b;
            b = allEdges.get().get(index.get());
            X.set(b.x * width.get());
            Y.set(b.y * height.get());
    }


    @Override
    public void draw(PGraphics g, Context context, float v) {


    }

}
