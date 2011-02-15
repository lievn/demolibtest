package demolibtest;

import java.util.Random;
import msafluid.MSAFluidSolver2D;
import nodebox.util.ProcessingSupport;


public class Force {
  private MSAFluidSolver2D fluidSolver;
  final static float MOMENTUM = .5f;
  final static float FLUID_FORCE = .1f;
  public float x,y,xs,vx,vy,mass,width,height,invWidth,invHeight;


  void init() {
    Random seed = new Random();
    x = 100+ProcessingSupport.random(0,400,seed.nextInt());
    y = 100+ProcessingSupport.random(0,400,seed.nextInt());
    vx = 0;
    vy = 0;
    mass = ProcessingSupport.random(20,100,seed.nextInt())/50.0f;
    xs=2+ProcessingSupport.random(0,20,seed.nextInt());
      width=500.0f;
      height=500.0f;
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
    ///*
    if(x<0) {
      x = 10;
      vx *= -1;
    }
    else if(x > width) {
      x = width-10;
      vx *= -1;
    }

    if(y<0) {
      y = 10;
      vy *= -1;
    }
    else if(y > height) {
      y = height-10;
      vy *= -1;
    } //*/
  }
}
