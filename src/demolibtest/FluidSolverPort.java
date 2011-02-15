package demolibtest;

import nodebox.node.*;
import msafluid.MSAFluidSolver2D;


public class FluidSolverPort extends Port{

    private MSAFluidSolver2D value;

    public FluidSolverPort(Node node,String name, Direction direction){
        super(node,name,direction);
    }
    @Override
    public Object getValue(){
        return value;
    }
    @Override
    public void setValue(Object value) throws IllegalArgumentException{
             if(value instanceof MSAFluidSolver2D || value == null){
                     set((MSAFluidSolver2D) value);
             } else{
                 throw new IllegalArgumentException("Value is not an Fluid Solver");

             }
    }

    public void set(MSAFluidSolver2D value){
        this.value = value;
    }
    public MSAFluidSolver2D get(){
        return value;
    }

}