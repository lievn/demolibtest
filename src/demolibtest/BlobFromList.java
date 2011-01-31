package demolibtest;

import blobDetection.Blob;
import nodebox.node.*;
import processing.core.PGraphics;


public class BlobFromList extends Node{

    public final BlobList allBlobs = new BlobList(this, "blobs", Port.Direction.INPUT);
    public final IntPort index = new IntPort(this, "index", Port.Direction.INPUT);
    public final FloatPort width = new FloatPort(this, "width", Port.Direction.INPUT);
    public final FloatPort height = new FloatPort(this, "height", Port.Direction.INPUT);
    public final FloatPort X = new FloatPort(this, "X", Port.Direction.OUTPUT);
    public final FloatPort Y = new FloatPort(this, "Y", Port.Direction.OUTPUT);
    public final FloatPort W = new FloatPort(this, "W", Port.Direction.OUTPUT);
    public final FloatPort H = new FloatPort(this, "H", Port.Direction.OUTPUT);


@Override
    public void execute(Context context, float time) {
            Blob b;
            b = allBlobs.get().get(index.get());
            X.set(b.xMin * width.get());
            Y.set(b.yMin * height.get());
            W.set(b.w * width.get());
            H.set(b.h * height.get());
    }


    @Override
    public void draw(PGraphics g, Context context, float v) {


    }

}
