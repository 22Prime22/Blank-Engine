package blankengine.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Display {
    
    //window
    private JFrame frame;
    private Canvas canvas;
    //dimensions
    private String title;
    private int width, height;
    private boolean fullScreen = true;
    
    public Display(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (width == screenSize.width && height == screenSize.height){
            fullScreen = true;
        } else {
            fullScreen = false;
        }   
    }
    
    private void createDisplay(){
        //create frame
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(fullScreen);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //create canvas
        canvas = new Canvas();
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        //add canvas to frame
        frame.add(canvas);
        frame.pack();
    }

    public void resize(int width, int height, boolean fullscreen){
        this.width = width;
        this.height = height;
        this.fullScreen = fullscreen;
        frame.dispose();
        createDisplay();
    }
    
    public void setTitle(String title){
        this.title = title;
        frame.setTitle(title);
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    public Canvas getCanvas(){
        return canvas;
    }
    
}
