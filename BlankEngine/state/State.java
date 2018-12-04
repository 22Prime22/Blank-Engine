package blankengine.state;

import java.awt.Graphics;
import blankengine.Handler;
import blankengine.ui.UIManager;

public abstract class State {
    
    public static State currentState = null;
    
    public static void setState(State state){
        currentState = state;
        state.setUIManager();
        state.init();
    }
    
    //Class
    
    protected Handler handler;
    protected UIManager uiManager;
    
    public State(Handler handler){
        this.handler = handler;
        uiManager = new UIManager(handler);
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics g);
    
    private void setUIManager(){
        handler.getMouseManager().setUiManager(uiManager);
    }
    
    public abstract void init();
}

