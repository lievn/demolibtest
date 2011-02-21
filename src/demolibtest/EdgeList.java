package demolibtest;

import nodebox.node.*;
import blobDetection.EdgeVertex;
import java.util.ArrayList;

public class EdgeList extends Port{
    private ArrayList<EdgeVertex> value;

    public EdgeList(Node node,String name, Direction direction){
        super(node,name,direction);
    }
    @Override
    public Object getValue(){
        return value;
    }
    @Override
    public void setValue(Object value) throws IllegalArgumentException{
             if(value instanceof ArrayList || value == null){
                     set((ArrayList<EdgeVertex>) value);
             } else{
                 throw new IllegalArgumentException("Value is not an ArrayList of edgevertexes");

             }

    }

    public void set(ArrayList<EdgeVertex> value){
        this.value = value;
    }
    public ArrayList<EdgeVertex> get(){
        return value;
    }

}
