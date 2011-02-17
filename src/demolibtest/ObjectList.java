package demolibtest;

import nodebox.node.*;
import java.util.ArrayList;
import tuio.TuioObject;

public class ObjectList extends Port{
    private ArrayList<TuioObject> value;

    public ObjectList(Node node,String name, Direction direction){
        super(node,name,direction);
    }
    @Override
    public Object getValue(){
        return value;
    }
    @Override
    public void setValue(Object value) throws IllegalArgumentException{
             if(value instanceof ArrayList || value == null){
                     set((ArrayList<TuioObject>) value);
             } else{
                 throw new IllegalArgumentException("Value is not an ArrayList of TuioObjects");

             }

    }

    public void set(ArrayList<TuioObject> value){
        this.value = value;
    }
    public ArrayList<TuioObject> get(){
        return value;
    }

}
