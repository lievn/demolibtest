package demolibtest;

import nodebox.node.*;
//import nodebox.graphics.Point;
import blobDetection.Blob;
import java.util.ArrayList;

public class PointList extends Port{
    private ArrayList<Blob> value;

    public PointList(Node node,String name, Direction direction){
        super(node,name,direction);
    }
    @Override
    public Object getValue(){
        return value;
    }
    @Override
    public void setValue(Object value) throws IllegalArgumentException{
             if(value instanceof ArrayList || value == null){
                     set((ArrayList<Blob>) value);
             } else{
                 throw new IllegalArgumentException("Value is not an ArrayList of blobs");

             }

    }

    public void set(ArrayList<Blob> value){
        this.value = value;
    }
    public ArrayList<Blob> get(){
        return value;
    }

}

/*
  import nodebox.graphics.Point;

*/