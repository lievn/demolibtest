package demolibtest;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import nodebox.node.Context;
import nodebox.node.Node;
import processing.core.PGraphics;

public class fftAnalysis extends Node {

    private Minim minim;
    private AudioInput in;
    private FFT fft;

    @Override
    public void initialize() {
        minim = new Minim(getScene().getApplet());
        minim.debugOn();
        in = minim.getLineIn(Minim.STEREO, 512);
        fft = new FFT(in.bufferSize(), in.sampleRate());
    }

    @Override
    public void draw(PGraphics g, Context context, float v) {
        fft.forward(in.mix);
        for (int i = 0; i < fft.specSize(); i++) {
            g.line(i, 400, i, 400 - fft.getBand(i) * 4);
        }
    }

    public void destroy() {
        in.close();
        minim.stop();
        getScene().getApplet().stop();
    }
}
