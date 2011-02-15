package demolibtest;

import java.util.Random;
import msafluid.MSAFluidSolver2D;
import nodebox.util.ProcessingSupport;


public class Force {
  private MSAFluidSolver2D fluidSolver;
  final static float MOMENTUM = .5f;
  final static float FLUID_FORCE = .2f;
  public float x,y,xs,vx,vy,mass,width,height,invWidth,invHeight;


  void init() {
    Random seed = new Random();
    x = 100+ProcessingSupport.random(0,300,seed.nextInt());
    y = 100+ProcessingSupport.random(0,300,seed.nextInt());
    vx = 0;
    vy = 0;
    mass = ProcessingSupport.random(1,200,seed.nextInt()/100);
    xs=20+ProcessingSupport.random(0,20,seed.nextInt());
      width=500;
      height=500;
       invWidth = 1.0f/width;
  invHeight = 1.0f/height;

      //update();
  }

  void update(MSAFluidSolver2D send) {
      fluidSolver = send;
    int fluidIndex = fluidSolver.getIndexForNormalizedPosition(x * invWidth, y * invHeight);
    vx = fluidSolver.u[fluidIndex] * width * mass * FLUID_FORCE + vx * MOMENTUM;
    vy = fluidSolver.v[fluidIndex] * height * mass * FLUID_FORCE + vy * MOMENTUM;

    x += vx;
    y += vy;

    if(x<0) {
      x = 0;
      vx *= -1;
    }
    else if(x > 500) {
      x = 500;
      vx *= -1;
    }

    if(y<0) {
      y = 0;
      vy *= -1;
    }
    else if(y > 500) {
      y = 500;
      vy *= -1;
    }
  }
}
