import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameFrame extends JFrame {
    GameComponent gC;

    public GameFrame() {
        gC = new GameComponent();
        add(gC);
        setSize(gC.grid.getxSize()*40+40,gC.grid.getySize()*40+40);
        setResizable(false);
    }


}
