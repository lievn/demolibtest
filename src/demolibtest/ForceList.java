package demolibtest;

import msafluid.MSAFluidSolver2D;
import nodebox.node.*;
import java.util.ArrayList;


public class ForceList extends Port{
    //private MSAFluidSolver2D fluidSolver;
    private ArrayList<Force> value;


    public ForceList(Node node,String name, Direction direction){
        super(node,name,direction);
    }
    @Override
    public Object getValue(){
        return value;
    }
    @Override
    public void setValue(Object value) throws IllegalArgumentException{
             if(value instanceof ArrayList || value == null){
                     set((ArrayList<Force>) value);
             } else{
                 throw new IllegalArgumentException("Value is not an ArrayList of forces");
             }
    }

    public void set(ArrayList<Force> value){
        this.value = value;
    }
    public ArrayList<Force> get(){
        return value;
    }

}
