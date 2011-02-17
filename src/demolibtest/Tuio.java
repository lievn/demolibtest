package demolibtest;


import nodebox.node.*;
import processing.core.PGraphics;
import tuio.*;
import java.util.ArrayList;

@Description("TuioClient")
@Category("Tuio")

public class Tuio extends Node {

    public final CursorList allCursors = new CursorList(this, "cursors", Port.Direction.OUTPUT);
    public final IntPort totalCursors = new IntPort(this, "cursorsNb", Port.Direction.OUTPUT);
    public final ObjectList allObjects = new ObjectList(this, "objects", Port.Direction.OUTPUT);
    public final IntPort totalObjects = new IntPort(this, "objectsNb", Port.Direction.OUTPUT);

    TuioClient tuioClient;


    @Override
    public void initialize() {

    }

    @Override
    public void execute(Context context, float time) {
        if(tuioClient == null){
        tuioClient  = new TuioClient(getScene().getApplet());
        }
        computeCursors();
        computeObjects();

    }

    @Override
    public void draw(PGraphics g, Context context, float v) {

    }



    public void computeCursors() {
        TuioCursor tcur;
        ArrayList<TuioCursor> cursors = new ArrayList<TuioCursor>();
        for (int n = 0; n < tuioClient.getTuioCursors().length; n++) {
            tcur = tuioClient.getTuioCursors()[n];
                cursors.add(tcur);
        }
        allCursors.set(cursors);
        totalCursors.set(tuioClient.getTuioCursors().length);
    }

    public void computeObjects() {
        TuioObject tobj;
        ArrayList<TuioObject> objects = new ArrayList<TuioObject>();
        for (int n = 0; n < tuioClient.getTuioObjects().length; n++) {
            tobj = tuioClient.getTuioObjects()[n];
                objects.add(tobj);
        }
        allObjects.set(objects);
        totalObjects.set(tuioClient.getTuioObjects().length);
    }



void addTuioObject(TuioObject tobj) {
  System.out.print("add object "+tobj.getFiducialID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());
}

void removeTuioObject(TuioObject tobj) {
  System.out.print("remove object "+tobj.getFiducialID()+" ("+tobj.getSessionID()+")");
}

void updateTuioObject (TuioObject tobj) {
  System.out.print("update object "+tobj.getFiducialID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()
          +" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel());
}

void addTuioCursor(TuioCursor tcur) {
  System.out.print("add cursor "+tcur.getFingerID()+" ("+tcur.getSessionID()+ ") " +tcur.getX()+" "+tcur.getY());
}

void updateTuioCursor (TuioCursor tcur) {
  System.out.print("update cursor "+tcur.getFingerID()+" ("+tcur.getSessionID()+ ") " +tcur.getX()+" "+tcur.getY()
          +" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel());
}

void removeTuioCursor(TuioCursor tcur) {
  System.out.print("remove cursor "+tcur.getFingerID()+" ("+tcur.getSessionID()+")");
}


void refresh(long timestamp) {
  //redraw();
}


}
