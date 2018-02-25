import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameFrame extends JFrame {
    GameComponent gC;

    public GameFrame() {
        gC = new GameComponent();
        JButton button = new JButton("RESET");
        /*button.addActionListener(e -> gC.reset());
        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);*/
        add(gC.panel,BorderLayout.CENTER);
        if(gC.grid.getySize()>gC.grid.getxSize())
            setSize((gC.grid.getxSize()*10)*4,gC.grid.getySize()*10);
        else
            setSize((gC.grid.getxSize()*10),gC.grid.getySize()*10);
        setResizable(false);
    }


}
