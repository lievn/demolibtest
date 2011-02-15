package demolibtest;

import blobDetection.Blob;
import msafluid.MSAFluidSolver2D;
import nodebox.node.*;
import processing.core.PGraphics;

import java.util.ArrayList;

public class Fluid extends Node {

    private static final int width = 500;
    private static final int height = 500;
    private static final float FLUID_WIDTH = 20;
    private MSAFluidSolver2D fluidSolver;
    private static float invWidth, invHeight;
    private static int number = 50;

    public final FloatPort pX = new FloatPort(this, "x", Port.Direction.INPUT);
    public final FloatPort pY = new FloatPort(this, "y", Port.Direction.INPUT);
    public final FloatPort pPreviousX = new FloatPort(this, "previousX", Port.Direction.INPUT);
    public final FloatPort pPreviousY = new FloatPort(this, "previousY", Port.Direction.INPUT);
    public final IntPort pamount = new IntPort(this, "amount", Port.Direction.INPUT,50);
    public final FluidSolverPort pFluidSolver = new FluidSolverPort(this, "fluid", Port.Direction.OUTPUT);

    public final ForceList allForces = new ForceList(this, "forces", Port.Direction.OUTPUT);

    ArrayList<Force> forces = new ArrayList<Force>();

    @Override
    public void initialize() {
        fluidSolver = new MSAFluidSolver2D((int)(FLUID_WIDTH), (int)(FLUID_WIDTH * height/width));
          invWidth = 1.0f/width;
          invHeight = 1.0f/height;
        createForces();
         
    }
    @Override
    public void execute(Context context, float time) {
        float mouseNormX = pX.get() * invWidth;
        float mouseNormY = pY.get() * invHeight;
        float mouseVelX = (pX.get() - pPreviousX.get()) * invWidth;
        float mouseVelY = (pY.get() - pPreviousY.get()) * invHeight;
        addForce(mouseNormX, mouseNormY, mouseVelX, mouseVelY);
        fluidSolver.update();
        if (pamount.get()!=number){
         createForces();
            number=pamount.get();
        }
        updateForces();
        pFluidSolver.set(fluidSolver);
    }

    @Override
    public void draw(PGraphics g, Context context, float v) {

    }



    public void createForces() {
        Force f;
        for (int n = 0; n < pamount.get(); n++) {
            f = new Force();
            f.init();
            if (f != null) {
                forces.add(f);

            }
        }
         allForces.set(forces);
    }


    public void updateForces() {
        Force f;
        for (int n = 0; n < pamount.get(); n++) {
            f = forces.get(n);
            f.update(fluidSolver);
        }
        allForces.set(forces);
    }

  public void addForce(float x, float y, float dx, float dy) {
  float speed = dx * dx  + dy * dy;
  if(speed > 0) {
    if(x<0) x = 0;
    else if(x>1) x = 1;
    if(y<0) y = 0;
    else if(y>1) y = 1;
    float velocityMult = 20.0f;
    int index = fluidSolver.getIndexForNormalizedPosition(x, y);
    fluidSolver.uOld[index] += dx * velocityMult;
    fluidSolver.vOld[index] += dy * velocityMult;
  }
}

}
