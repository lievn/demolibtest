package demolibtest;


import blobDetection.Blob;
import blobDetection.BlobDetection;
import blobDetection.EdgeVertex;
import blobDetection.BlobTriangle;
import nodebox.node.*;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.ArrayList;

public class BlobDetect extends Node {

    public final ImagePort pImage = new ImagePort(this, "inputImage", Port.Direction.INPUT);
    public final BlobList theList = new BlobList(this, "thelist", Port.Direction.OUTPUT);
    public final FloatPort threshold = new FloatPort(this, "threshold", Port.Direction.INPUT, 0.5f);
    public final BooleanPort discrimination = new BooleanPort(this, "posdiscr", Port.Direction.INPUT, false);
    public final BooleanPort dblobs = new BooleanPort(this, "drawblobs", Port.Direction.INPUT, true);
    public final BooleanPort dedges = new BooleanPort(this, "drawedges", Port.Direction.INPUT, false);
    public final BooleanPort dtriang = new BooleanPort(this, "drawtriangles", Port.Direction.INPUT, false);
    public final IntPort blur = new IntPort(this, "blur", Port.Direction.INPUT, 2);
    public final FloatPort X = new FloatPort(this, "X", Port.Direction.OUTPUT);
    public final FloatPort Y = new FloatPort(this, "Y", Port.Direction.OUTPUT);
    private BlobDetection theBlobDetection;
    Blob theBiggestBlob;
    PImage imgSmall;


    @Override
    public void execute(Context context, float time) {
        if (pImage.get() != null) {
            imgSmall = new PImage(40,30);
            imgSmall.copy(pImage.get(), 0, 0, pImage.get().width, pImage.get().height, 0, 0, imgSmall.width, imgSmall.height);

            //theBlobDetection = new BlobDetection(pImage.get().width, pImage.get().height);
            theBlobDetection = new BlobDetection(imgSmall.width,imgSmall.height);
            theBlobDetection.setConstants(1000,1000,500);
            theBlobDetection.setPosDiscrimination(discrimination.get());
            theBlobDetection.computeTriangles();
            theBlobDetection.setThreshold(threshold.get());
            //theBlobDetection.setConstants(1000,1000,750);
            theBlobDetection.computeBlobs(imgSmall.get().pixels);
            theBiggestBlob = findBiggestBlob();

        //test for sending xy biggestblob
        X.set(theBiggestBlob.xMin*pImage.get().width);
        Y.set(theBiggestBlob.yMin*pImage.get().height);
        }
    }

    @Override
    public void draw(PGraphics g, Context context, float v) {
        g.image(pImage.get(), 0, 0, pImage.get().width, pImage.get().height);
        fastblur(blur.get());
        //theBlobDetection.computeBlobs(imgSmall.get().pixels);

        //System.out.print(theBiggestBlob.xMin);

        //computeBlobs(g);
        if(dblobs.get()){
        drawBlobs(g);
        }
        if(dedges.get()){
        drawEdges(g);
        }
        if(dtriang.get()){
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


    public void drawTriangles(PGraphics g){

         BlobTriangle bTri;
                Blob b;
        EdgeVertex eA, eB, eC;
        for (int n = 0; n < theBlobDetection.getBlobNb(); n++) {
            b = theBlobDetection.getBlob(n);
              g.fill(0, 0, 0, 0);
                g.strokeWeight(1);
                g.stroke(0, 0, 0);

    //fill(random(255),0,255);
    //noStroke();stroke(0);
    g.beginShape(g.TRIANGLES);
    for (int t=0;t<b.getTriangleNb();t++)
    {
      bTri = b.getTriangle(t);
      eA = b.getTriangleVertexA(bTri);
      eB = b.getTriangleVertexB(bTri);
      eC = b.getTriangleVertexC(bTri);

      g.vertex(eA.x*pImage.get().width, eA.y*pImage.get().height);
      g.vertex(eB.x*pImage.get().width, eB.y*pImage.get().height);
      g.vertex(eC.x*pImage.get().width, eC.y*pImage.get().height);

    }
    g.endShape();

        }



    }


    public void computeBlobs(PGraphics g) {
        Blob b;
        ArrayList<Blob> blobs = new ArrayList<Blob>();
        for (int n = 0; n < theBlobDetection.getBlobNb(); n++) {
            b = theBlobDetection.getBlob(n);
            if (b != null) {
                blobs.add(b);

            }
        }
        theList.set(blobs);
        System.out.println(blobs);
    }


    Blob findBiggestBlob()
{
  Blob biggestBlob = null;
  float surface = 0.0f;
  float surfaceMax = 0.0f;
  Blob b=null;
  for (int i=0;i<theBlobDetection.getBlobNb();i++)
  {
    b = theBlobDetection.getBlob(i);
    surface = b.w * b.h;
    if (surface > surfaceMax)
    {
      surfaceMax=surface;
      biggestBlob = b;
    }
  }

  return biggestBlob;
}


    public void fastblur(int radius) {
        if (radius < 1) {
            return;
        }
        int w = imgSmall.width;
        int h = imgSmall.height;
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;
        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, p1, p2, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];
        int vmax[] = new int[Math.max(w, h)];
        int[] pix = imgSmall.get().pixels;
        int dv[] = new int[256 * div];
        for (i = 0; i < 256 * div; i++) {
            dv[i] = (i / div);
        }

        yw = yi = 0;

        for (y = 0; y < h; y++) {
            rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                rsum += (p & 0xff0000) >> 16;
                gsum += (p & 0x00ff00) >> 8;
                bsum += p & 0x0000ff;
            }
            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                    vmax[x] = Math.max(x - radius, 0);
                }
                p1 = pix[yw + vmin[x]];
                p2 = pix[yw + vmax[x]];

                rsum += ((p1 & 0xff0000) - (p2 & 0xff0000)) >> 16;
                gsum += ((p1 & 0x00ff00) - (p2 & 0x00ff00)) >> 8;
                bsum += (p1 & 0x0000ff) - (p2 & 0x0000ff);
                yi++;
            }
            yw += w;
        }

        for (x = 0; x < w; x++) {
            rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;
                rsum += r[yi];
                gsum += g[yi];
                bsum += b[yi];
                yp += w;
            }
            yi = x;
            for (y = 0; y < h; y++) {
                pix[yi] = 0xff000000 | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];
                if (x == 0) {
                    vmin[y] = Math.min(y + radius + 1, hm) * w;
                    vmax[y] = Math.max(y - radius, 0) * w;
                }
                p1 = x + vmin[y];
                p2 = x + vmax[y];

                rsum += r[p1] - r[p2];
                gsum += g[p1] - g[p2];
                bsum += b[p1] - b[p2];

                yi += w;
            }
        }

    }

}
