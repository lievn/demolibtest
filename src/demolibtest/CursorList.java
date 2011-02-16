package demolibtest;

import nodebox.node.*;
import java.util.ArrayList;
import tuio.TuioCursor;

public class CursorList extends Port{
    private ArrayList<TuioCursor> value;

    public CursorList(Node node,String name, Direction direction){
        super(node,name,direction);
    }
    @Override
    public Object getValue(){
        return value;
    }
    @Override
    public void setValue(Object value) throws IllegalArgumentException{
             if(value instanceof ArrayList || value == null){
                     set((ArrayList<TuioCursor>) value);
             } else{
                 throw new IllegalArgumentException("Value is not an ArrayList of TuioCursors");

             }

    }

    public void set(ArrayList<TuioCursor> value){
        this.value = value;
    }
    public ArrayList<TuioCursor> get(){
        return value;
    }

}
