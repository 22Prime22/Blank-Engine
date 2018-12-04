package blankengine.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public abstract class UIObject {
    
    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;
    protected boolean hovering, active;
    
    public UIObject(float x, float y, int width, int height, boolean active){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int) x, (int) y, width, height);
        this.active = active;
    }

    public abstract void tick();
    
    public abstract void render(Graphics g);
    
    protected abstract void onClick();
    
    public void onMouseMove(MouseEvent me){
        //check for hovering
        if (bounds.contains(me.getPoint()) && active){
            hovering = true;
        } else {
            hovering = false;
        }
    }
    
    public void onMouseRelease(MouseEvent me){
        //trigger on click
        if (me.getButton() == MouseEvent.BUTTON1){
            if (hovering && active){
                onClick();
            }
        }
    }
    
    //getters and setters
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        bounds.x = (int) x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        bounds.y = (int) y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        bounds.width = (int) width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        bounds.height = (int) height;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void scroll(int scroll) {}
    
}
