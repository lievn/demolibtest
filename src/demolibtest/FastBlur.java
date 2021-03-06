package demolibtest;

import nodebox.node.*;
import processing.core.PGraphics;
import processing.core.PImage;


public class FastBlur extends Node {

    public final ImagePort pImage = new ImagePort(this, "input", Port.Direction.INPUT);
    public final IntPort blur = new IntPort(this, "blur", Port.Direction.INPUT, 10);
    public final ImagePort pImageO = new ImagePort(this, "output", Port.Direction.OUTPUT);

    PImage imgSmall;

    public void execute(Context context, float time) {

        imgSmall = new PImage(320, 240);
        imgSmall.copy(pImage.get(), 0, 0, pImage.get().width, pImage.get().height, 0, 0, imgSmall.width, imgSmall.height);
        fastblur(imgSmall,blur.get());
        pImageO.set(imgSmall);
    }

    @Override
    public void draw(PGraphics g, Context context, float v) {
        if (pImage.get() != null) {
           g.image(imgSmall, 0, 0, pImage.get().width, pImage.get().height);
        }
    }

    public void fastblur(PImage img ,int radius) {
        if (radius < 1) {
            return;
        }
        int w = img.width;
        int h = img.height;
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
        int[] pix = img.pixels;
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
