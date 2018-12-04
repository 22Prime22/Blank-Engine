package blankengine;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Launcher {

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Game game = new Game("tree", (int) screenSize.getWidth(), (int) screenSize.getHeight());
        game.start();
    }
    
}
