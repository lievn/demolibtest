package demolibtest;

import blobDetection.EdgeVertex;
import nodebox.node.*;
import processing.core.PGraphics;


public class EdgeFromList extends Node{

    //public final BlobList allBlobs = new BlobList(this, "blobs", Port.Direction.INPUT);
    public final EdgeList allEdges = new EdgeList(this, "edges", Port.Direction.INPUT);
    public final IntPort index = new IntPort(this, "index", Port.Direction.INPUT);
    public final FloatPort width = new FloatPort(this, "width", Port.Direction.INPUT);
    public final FloatPort height = new FloatPort(this, "height", Port.Direction.INPUT);
    public final FloatPort X = new FloatPort(this, "X", Port.Direction.OUTPUT);
    public final FloatPort Y = new FloatPort(this, "Y", Port.Direction.OUTPUT);
    //public final FloatPort W = new FloatPort(this, "W", Port.Direction.OUTPUT);
    //public final FloatPort H = new FloatPort(this, "H", Port.Direction.OUTPUT);


@Override
    public void execute(Context context, float time) {
            EdgeVertex a,b;
            b = allEdges.get().get(index.get());
            //a = allEdges.get().get(index.get());
            X.set(b.x * width.get());
            Y.set(b.y * height.get());
            //W.set(b.w * width.get());
            //H.set(b.h * height.get());
    }


    @Override
    public void draw(PGraphics g, Context context, float v) {


    }

}
