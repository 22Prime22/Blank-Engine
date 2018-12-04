package blankengine;

import blankengine.state.MainState;
import blankengine.state.State;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import blankengine.display.Display;
import blankengine.gfx.Assets;
import blankengine.input.KeyManager;
import blankengine.input.MouseManager;

public class Game implements Runnable {

    //thread
    private Thread gameThread;
    public boolean running = false;
    //render
    private BufferStrategy bs;
    private Graphics g;
    private int trueFps, trueTps;
    //display
    private Display display;
    private int width, height;
    private String title;
    //input
    private KeyManager keyManager;
    private MouseManager mouseManager;
    //handler
    private Handler handler;
    //states
    public State mainState;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    private void init() {
        //assets
        Assets.init();
        //handler
        handler = new Handler(this);
        //display
        display = new Display(title, width, height);
        //keyboard
        keyManager = new KeyManager();
        display.getFrame().addKeyListener(keyManager);
        //mouse
        mouseManager = new MouseManager();
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getFrame().addMouseWheelListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseWheelListener(mouseManager);
        mainState = new MainState(handler);
        State.setState(mainState);
    }

    @Override
    public void run() {
        init();
        //set up 60 frames and ticks per second
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        double second = 0.0;
        long now;
        long lastTime = System.nanoTime();
        long startRender;
        long renderTime;
        int ticks = 0;
        int renders = 0;
        double framesToSkip = 0.0;
        //game loop
        while (running) {
            now = System.nanoTime();
            second += (now - lastTime);
            delta += (now - lastTime) / timePerTick;
            lastTime = now;
            if (delta >= 1) { //runs 60 times per second
                tick();
                ticks++;
                if (framesToSkip < 1) {
                    //renders
                    startRender = System.nanoTime();
                    render();
                    renderTime = System.nanoTime() - startRender;
                    //calculates frames to skip to keep close to 60tps
                    framesToSkip += renderTime / timePerTick;
                    renders++;
                } else {
                    //skips frames
                    framesToSkip--;
                }
                delta--;
                if (second >= 1000000000) {
                    second -= 1000000000;
                    trueFps = renders;
                    trueTps = ticks;
                    ticks = 0;
                    renders = 0;
                }
            }
        }
        stop();
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        try {
            gameThread.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void tick() {
        //input
        keyManager.tick();
        State.currentState.tick();
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //clear screen
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        //draw
        State.currentState.render(g);
        g.setColor(Color.yellow);
        g.setFont(new Font("monospaced", 1, 16));
        g.drawString(trueFps + "fps " + trueTps + "tps", 10, g.getFontMetrics().getHeight());
        //end draw
        bs.show();
        g.dispose();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferStrategy getBs() {
        return bs;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

}
