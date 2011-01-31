package demolibtest;


import blobDetection.Blob;
import blobDetection.BlobDetection;
import blobDetection.BlobTriangle;
import blobDetection.EdgeVertex;
import nodebox.node.*;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.ArrayList;

public class BlobDetect extends Node {

    public final ImagePort pImage = new ImagePort(this, "inputImage", Port.Direction.INPUT);
    public final BlobList allBlobs = new BlobList(this, "blobs", Port.Direction.OUTPUT);
    public final IntPort totalBlobs = new IntPort(this, "BlobsNb", Port.Direction.OUTPUT);
    public final EdgeList allEdges = new EdgeList(this, "edges", Port.Direction.OUTPUT);
    public final IntPort totalEdges = new IntPort(this, "EdgesNb", Port.Direction.OUTPUT);
    public final FloatPort threshold = new FloatPort(this, "threshold", Port.Direction.INPUT, 0.5f);
    public final BooleanPort discrimination = new BooleanPort(this, "posdiscr", Port.Direction.INPUT, false);
    public final BooleanPort dblobs = new BooleanPort(this, "drawblobs", Port.Direction.INPUT, true);
    public final BooleanPort dedges = new BooleanPort(this, "drawedges", Port.Direction.INPUT, false);
    public final BooleanPort dtriang = new BooleanPort(this, "drawtriangles", Port.Direction.INPUT, false);
    public final FloatPort X = new FloatPort(this, "X", Port.Direction.OUTPUT);
    public final FloatPort Y = new FloatPort(this, "Y", Port.Direction.OUTPUT);
    private BlobDetection theBlobDetection;
    Blob theBiggestBlob;
    PImage imgSmall;


    @Override
    public void execute(Context context, float time) {
        if (pImage.get() != null) {
            imgSmall = new PImage(40, 30);
            imgSmall.copy(pImage.get(), 0, 0, pImage.get().width, pImage.get().height, 0, 0, imgSmall.width, imgSmall.height);
            theBlobDetection = new BlobDetection(imgSmall.width, imgSmall.height);
            theBlobDetection.setConstants(1000, 1000, 500);
            theBlobDetection.setPosDiscrimination(discrimination.get());
            theBlobDetection.computeTriangles();
            theBlobDetection.setThreshold(threshold.get());
            theBlobDetection.computeBlobs(imgSmall.get().pixels);
            theBiggestBlob = findBiggestBlob();
            computeBlobs();
            computeEdges();

            //x and y  biggestblob
            X.set(theBiggestBlob.xMin * pImage.get().width);
            Y.set(theBiggestBlob.yMin * pImage.get().height);
        }
    }

    @Override
    public void draw(PGraphics g, Context context, float v) {
        g.image(pImage.get(), 0, 0, pImage.get().width, pImage.get().height);

        if (dblobs.get()) {
            drawBlobs(g);
        }
        if (dedges.get()) {
            drawEdges(g);
        }
        if (dtriang.get()) {
            drawTriangles(g);
        }

    }

    public void drawBlobs(PGraphics g) {
        Blob b;
        for (int n = 0; n < theBlobDetection.getBlobNb(); n++) {
            b = theBlobDetection.getBlob(n);
            if (b != null) {
                g.fill(0, 0, 0, 0);
                g.strokeWeight(1);
                g.stroke(255, 0, 0);
                g.rect(
                        b.xMin * pImage.get().width, b.yMin * pImage.get().height,
                        b.w * pImage.get().width, b.h * pImage.get().height
                );


            }
        }
    }


    public void drawEdges(PGraphics g) {
        Blob b;
        EdgeVertex eA, eB;
        for (int n = 0; n < theBlobDetection.getBlobNb(); n++) {
            b = theBlobDetection.getBlob(n);
            if (b != null) {
                g.strokeWeight(3);
                g.stroke(0, 255, 0);
                for (int m = 0; m < b.getEdgeNb(); m++) {

                    eA = b.getEdgeVertexA(m);
                    eB = b.getEdgeVertexB(m);
                    if (eA != null && eB != null)
                        g.line(
                                eA.x * pImage.get().width, eA.y * pImage.get().height,
                                eB.x * pImage.get().width, eB.y * pImage.get().height
                        );
                }
            }
        }
    }


    public void drawTriangles(PGraphics g) {

        BlobTriangle bTri;
        Blob b;
        EdgeVertex eA, eB, eC;
        for (int n = 0; n < theBlobDetection.getBlobNb(); n++) {
            b = theBlobDetection.getBlob(n);
            g.fill(0, 0, 0, 0);
            g.strokeWeight(1);
            g.stroke(0, 0, 0);
            g.beginShape(g.TRIANGLES);
            for (int t = 0; t < b.getTriangleNb(); t++) {
                bTri = b.getTriangle(t);
                eA = b.getTriangleVertexA(bTri);
                eB = b.getTriangleVertexB(bTri);
                eC = b.getTriangleVertexC(bTri);

                g.vertex(eA.x * pImage.get().width, eA.y * pImage.get().height);
                g.vertex(eB.x * pImage.get().width, eB.y * pImage.get().height);
                g.vertex(eC.x * pImage.get().width, eC.y * pImage.get().height);

            }
            g.endShape();

        }


    }


    public void computeBlobs() {
        Blob b;
        ArrayList<Blob> blobs = new ArrayList<Blob>();
        for (int n = 0; n < theBlobDetection.getBlobNb(); n++) {
            b = theBlobDetection.getBlob(n);
            if (b != null) {
                blobs.add(b);

            }
        }
        allBlobs.set(blobs);
        totalBlobs.set(theBlobDetection.getBlobNb());
    }


    public void computeEdges() {
        EdgeVertex aa, bb;
        Blob b;
        ArrayList<EdgeVertex> edges = new ArrayList<EdgeVertex>();
        int total = 0;

        for (int n = 0; n < theBlobDetection.getBlobNb(); n++) {
            b = theBlobDetection.getBlob(n);
            total+=b.getEdgeNb();
            for (int p = 0; p < b.getEdgeNb(); p++) {

                aa = b.getEdgeVertexA(p);
                bb = b.getEdgeVertexB(p);
                edges.add(aa);
                edges.add(bb);
            }
        }
        allEdges.set(edges);
        totalEdges.set(total);
    }


    Blob findBiggestBlob() {
        Blob biggestBlob = null;
        float surface = 0.0f;
        float surfaceMax = 0.0f;
        Blob b = null;
        for (int i = 0; i < theBlobDetection.getBlobNb(); i++) {
            b = theBlobDetection.getBlob(i);
            surface = b.w * b.h;
            if (surface > surfaceMax) {
                surfaceMax = surface;
                biggestBlob = b;
            }
        }

        return biggestBlob;
    }


}
