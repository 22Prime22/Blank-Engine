package blankengine.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import blankengine.Handler;

public class UIManager {

    private Handler handler;
    private ArrayList<UIObject> objects, edit;

    public UIManager(Handler handler) {
        this.handler = handler;
        objects = new ArrayList<>();
        edit = objects;
    }

    public void tick() {
        objects = (ArrayList<UIObject>) edit.clone();
        for (UIObject o : objects) {
            if (o.active) {
                o.tick();
            }
        }
    }

    public void render(Graphics g) {
        ArrayList<UIObject> temp = objects;
        for (UIObject o : temp) {
            if (o.active) {
                o.render(g);
            }
        }
    }

    public void onMouseMove(MouseEvent me) {
        try {
            for (UIObject o : objects) {
                o.onMouseMove(me);
            }
        } catch (Exception ex) {
        }
    }

    public void onMouseRelease(MouseEvent me) {
        try {
            for (UIObject o : objects) {
                o.onMouseRelease(me);
            }
        } catch (Exception ex) {
        }
    }
    
    public void onScroll(int scroll){
        try {
            for (UIObject o : objects) {
                o.scroll(scroll);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeAll() {
        edit.removeAll(edit);
    }

    public void addUIObject(UIObject object) {
        edit.add(object);
    }

    public void removeUIObject(UIObject object) {
        edit.remove(object);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<UIObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<UIObject> objects) {
        this.objects = objects;
    }

}
