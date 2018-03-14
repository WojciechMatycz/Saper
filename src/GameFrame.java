import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameFrame extends JFrame {
    GameComponent gC;
    private JPanel topPanel;
    private JComboBox<String> levelList;

    public GameFrame() {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        levelList = new JComboBox<>();
        levelList.addItem("Easy");
        levelList.addItem("Medium");
        levelList.addItem("Expert");

        gC = new GameComponent(8,8,10);
        JButton button = new JButton("RESET");
        button.addActionListener(e -> {
            gC.reset(gC.getX(), gC.getY(),gC.getCurrentBombsAmount());
            validate();
            repaint();
        });

        levelList.addActionListener(e -> {
            switch (levelList.getItemAt(levelList.getSelectedIndex())){
                case "Easy":
                    gC.reset(8, 8,10);
                    validate();
                    resize();
                    repaint();
                    System.out.println(gC.grid.getySize() + " , " + gC.grid.getxSize());
                    break;
                case "Medium":
                    gC.reset(16, 16,40);
                    validate();
                    resize();
                    repaint();
                    System.out.println(gC.grid.getySize() + " , " + gC.grid.getxSize());
                    break;
                case "Expert":
                    gC.reset(16, 32,99);
                    validate();
                    resize();
                    repaint();
                    System.out.println(gC.grid.getySize() + " , " + gC.grid.getxSize());
                    break;
            }
        });


        setLayout(new BorderLayout());
        topPanel.add(levelList);
        topPanel.add(button);
        add(topPanel, BorderLayout.NORTH);
        add(gC.panel,BorderLayout.CENTER);

        resize();
        setResizable(false);
    }

    private void resize()
    {
        if(gC.grid.getySize()>gC.grid.getxSize())
            setSize((gC.grid.getxSize()*10)*4,gC.grid.getySize()*10);
        else
            setSize((gC.grid.getxSize()*30),gC.grid.getySize()*30);
    }


}
