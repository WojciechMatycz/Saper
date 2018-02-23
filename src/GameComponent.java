import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;

public class GameComponent extends JComponent {
    Grid grid;
    ButtonList buttons;

    public GameComponent(){
        grid = new Grid(8,8,10);
        buttons = new ButtonList();
        //setLayout(new BorderLayout());

        setLayout(new GridLayout(grid.getxSize(), grid.getySize()));


        for (int i = 0; i < grid.getxSize(); i++)
            for(int j=0; j<grid.getySize(); j++)
                addFieldButton(i,j);

        cheat();

    }

    private void cheat() {
        for (int i = 0; i < grid.getxSize(); i++)
            for (int j = 0; j < grid.getySize(); j++) {
                if (grid.getMap()[i][j].isBombPlanted()) {
                    Image img = (new ImageIcon("bomb.png")).getImage();
                    Image newimg = img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
                    buttons.findButton(i, j).setIcon(new ImageIcon(newimg));
                }
            }
    }

    public void addFieldButton(int x, int y)
    {
        FieldButton button = new FieldButton(x,y);
        button.setSize(10,10);
        button.addMouseListener(new MouseAdapter() {
            boolean pressed;

            @Override
            public void mousePressed(MouseEvent e) {
                button.getModel().setArmed(true);
                button.getModel().setPressed(true);
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.getModel().setArmed(false);
                button.getModel().setPressed(false);
                if(pressed)
                {
                    if(SwingUtilities.isRightMouseButton(e))
                    {
                        if(!grid.getMap()[button.getPositionX()][button.getPositionY()].isFlagged()) {
                            Image img = (new ImageIcon("flag.png")).getImage();
                            Image newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                            button.setIcon(new ImageIcon( newimg ));
                        }
                        else
                        {
                            button.setIcon(null);
                        }
                        grid.getMap()[button.getPositionX()][button.getPositionY()].changeFlag();
                    }
                    else if(SwingUtilities.isLeftMouseButton(e))
                    {
                        if(!grid.getMap()[button.getPositionX()][button.getPositionY()].isFlagged())
                        {
                            if(grid.getMap()[button.getPositionX()][button.getPositionY()].isBombPlanted()) {
                                revealAllBombs(button.getWidth()-5, button.getHeight()-5);
                            }
                            else if(grid.getMap()[button.getPositionX()][button.getPositionY()].isEmpty())
                            {
                                emptyClicked(button, button.getPositionX(), button.getPositionY());
                            }
                            else
                                reveal(button, button.getPositionX(), button.getPositionY());

                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
        button.setBackground(new Color(216,216,216));
        add(button);
        buttons.add(button);
    }

    private void revealAllBombs(int width, int height) {
        for(int i=0; i<grid.getxSize(); i++)
            for(int j=0; j<grid.getySize(); j++)
            {
                if(grid.getMap()[i][j].isBombPlanted()) {
                    Image img = (new ImageIcon("bomb.png")).getImage();
                    Image newimg = img.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH );
                    buttons.findButton(i, j).setIcon(new ImageIcon( newimg ));
                }
                else
                {
                    buttons.findButton(i,j).setEnabled(false);
                }
            }
    }

    private void emptyClicked(FieldButton button, int positionX, int positionY) {
        button.setEnabled(false);
        button.setBackground(Color.white);

        Stack stack = new Stack();

        while()

        if(positionX==0 && positionY==0)
        {
            if(grid.getMap()[positionX][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY+1),positionX,positionY+1);
            else if(!grid.getMap()[positionX][positionY+1].isBombPlanted() && !grid.getMap()[positionX][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX,positionY+1),positionX,positionY+1);
            if(grid.getMap()[positionX+1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY),positionX+1,positionY);
            else if(!grid.getMap()[positionX+1][positionY].isBombPlanted() && !grid.getMap()[positionX+1][positionY].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY),positionX+1,positionY);
            if(grid.getMap()[positionX+1][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY+1),positionX+1,positionY+1);
            else if(!grid.getMap()[positionX+1][positionY+1].isBombPlanted() && !grid.getMap()[positionX+1][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY+1),positionX+1,positionY+1);
        }
        else if(positionX ==0 && positionY == grid.getySize()-1)
        {
            if(grid.getMap()[positionX][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY-1),positionX,positionY-1);
            else if(!grid.getMap()[positionX][positionY-1].isBombPlanted() && !grid.getMap()[positionX][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX,positionY-1),positionX,positionY-1);
            if(grid.getMap()[positionX+1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY),positionX+1,positionY);
            else if(!grid.getMap()[positionX+1][positionY].isBombPlanted() && !grid.getMap()[positionX+1][positionY].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY),positionX+1,positionY);
            if(grid.getMap()[positionX+1][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY-1),positionX+1,positionY-1);
            else if(!grid.getMap()[positionX+1][positionY-1].isBombPlanted() && !grid.getMap()[positionX+1][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY-1),positionX+1,positionY-1);
        }
        else if(positionX == grid.getxSize()-1 && positionY==0)
        {
            if(grid.getMap()[positionX][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY+1),positionX,positionY+1);
            else if(!grid.getMap()[positionX][positionY+1].isBombPlanted() && !grid.getMap()[positionX][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX,positionY+1),positionX,positionY+1);
            if(grid.getMap()[positionX-1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY),positionX-1,positionY);
            else if(!grid.getMap()[positionX-1][positionY].isBombPlanted() && !grid.getMap()[positionX-1][positionY].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY),positionX-1,positionY);
            if(grid.getMap()[positionX-1][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY+1),positionX-1,positionY+1);
            else if(!grid.getMap()[positionX-1][positionY+1].isBombPlanted() && !grid.getMap()[positionX-1][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY+1),positionX-1,positionY+1);
        }
        else if(positionX==grid.getxSize()-1 && positionY==grid.getySize()-1)
        {
            if(grid.getMap()[positionX][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY-1),positionX,positionY-1);
            else if(!grid.getMap()[positionX][positionY-1].isBombPlanted() && !grid.getMap()[positionX][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX,positionY-1),positionX,positionY-1);
            if(grid.getMap()[positionX-1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY),positionX-1,positionY);
            else if(!grid.getMap()[positionX-1][positionY].isBombPlanted() && !grid.getMap()[positionX-1][positionY].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY),positionX-1,positionY);
            if(grid.getMap()[positionX-1][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY-1),positionX-1,positionY-1);
            else if(!grid.getMap()[positionX-1][positionY-1].isBombPlanted() && !grid.getMap()[positionX-1][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY-1),positionX-1,positionY-1);
        }
        else if(positionX==0 && positionY>0 && positionY<grid.getySize()-1)
        {
            if(grid.getMap()[positionX][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY+1),positionX,positionY+1);
            else if(!grid.getMap()[positionX][positionY+1].isBombPlanted() && !grid.getMap()[positionX][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX,positionY+1),positionX,positionY+1);

            if(grid.getMap()[positionX+1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY),positionX+1,positionY);
            else if(!grid.getMap()[positionX+1][positionY].isBombPlanted() && !grid.getMap()[positionX+1][positionY].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY),positionX+1,positionY);

            if(grid.getMap()[positionX+1][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY+1),positionX+1,positionY+1);
            else if(!grid.getMap()[positionX+1][positionY+1].isBombPlanted() && !grid.getMap()[positionX+1][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY+1),positionX+1,positionY+1);

            if(grid.getMap()[positionX][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY-1),positionX,positionY-1);
            else if(!grid.getMap()[positionX][positionY-1].isBombPlanted() && !grid.getMap()[positionX][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX,positionY-1),positionX,positionY-1);

            if(grid.getMap()[positionX+1][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY-1),positionX+1,positionY-1);
            else if(!grid.getMap()[positionX+1][positionY-1].isBombPlanted() && !grid.getMap()[positionX+1][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY-1),positionX+1,positionY-1);

        }
        else if(positionX==grid.getxSize()-1 && positionY>0 && positionY<grid.getySize()-1)
        {
            if(grid.getMap()[positionX-1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY),positionX-1,positionY);
            else if(!grid.getMap()[positionX-1][positionY].isBombPlanted() && !grid.getMap()[positionX-1][positionY].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY),positionX-1,positionY);

            if(grid.getMap()[positionX-1][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY-1),positionX-1,positionY-1);
            else if(!grid.getMap()[positionX-1][positionY-1].isBombPlanted() && !grid.getMap()[positionX-1][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY-1),positionX-1,positionY-1);

            if(grid.getMap()[positionX][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY-1),positionX,positionY-1);
            else if(!grid.getMap()[positionX][positionY-1].isBombPlanted() && !grid.getMap()[positionX][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX,positionY-1),positionX,positionY-1);

            if(grid.getMap()[positionX-1][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY+1),positionX-1,positionY+1);
            else if(!grid.getMap()[positionX-1][positionY+1].isBombPlanted() && !grid.getMap()[positionX-1][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY+1),positionX-1,positionY+1);

            if(grid.getMap()[positionX][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY+1),positionX,positionY+1);
            else if(!grid.getMap()[positionX][positionY+1].isBombPlanted() && !grid.getMap()[positionX][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX,positionY+1),positionX,positionY+1);
        }
        else if(positionY ==0 && positionX>0 && positionX<grid.getxSize()-1)
        {
            if(grid.getMap()[positionX][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY+1),positionX,positionY+1);
            else if(!grid.getMap()[positionX][positionY+1].isBombPlanted() && !grid.getMap()[positionX][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX,positionY+1),positionX,positionY+1);

            if(grid.getMap()[positionX+1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY),positionX+1,positionY);
            else if(!grid.getMap()[positionX+1][positionY].isBombPlanted() && !grid.getMap()[positionX+1][positionY].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY),positionX+1,positionY);

            if(grid.getMap()[positionX+1][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY+1),positionX+1,positionY+1);
            else if(!grid.getMap()[positionX+1][positionY+1].isBombPlanted() && !grid.getMap()[positionX+1][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY+1),positionX+1,positionY+1);

            if(grid.getMap()[positionX-1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY),positionX-1,positionY);
            else if(!grid.getMap()[positionX-1][positionY].isBombPlanted() && !grid.getMap()[positionX-1][positionY].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY),positionX-1,positionY);

            if(grid.getMap()[positionX-1][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY+1),positionX-1,positionY+1);
            else if(!grid.getMap()[positionX-1][positionY+1].isBombPlanted() && !grid.getMap()[positionX-1][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY+1),positionX-1,positionY+1);
        }
        else if(positionY==grid.getySize()-1 && positionX>0 && positionX<grid.getxSize()-1)
        {
            if(grid.getMap()[positionX][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY-1),positionX,positionY-1);
            else if(!grid.getMap()[positionX][positionY-1].isBombPlanted() && !grid.getMap()[positionX][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX,positionY-1),positionX,positionY-1);

            if(grid.getMap()[positionX+1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY),positionX+1,positionY);
            else if(!grid.getMap()[positionX+1][positionY].isBombPlanted() && !grid.getMap()[positionX+1][positionY].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY),positionX+1,positionY);

            if(grid.getMap()[positionX+1][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY-1),positionX+1,positionY-1);
            else if(!grid.getMap()[positionX+1][positionY-1].isBombPlanted() && !grid.getMap()[positionX+1][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY-1),positionX+1,positionY-1);

            if(grid.getMap()[positionX-1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY),positionX-1,positionY);
            else if(!grid.getMap()[positionX-1][positionY].isBombPlanted() && !grid.getMap()[positionX-1][positionY].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY),positionX-1,positionY);

            if(grid.getMap()[positionX-1][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY-1),positionX-1,positionY-1);
            else if(!grid.getMap()[positionX-1][positionY-1].isBombPlanted() && !grid.getMap()[positionX-1][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY-1),positionX-1,positionY-1);
        }
        else
        {
            if(grid.getMap()[positionX][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY-1),positionX,positionY-1);
            else if(!grid.getMap()[positionX][positionY-1].isBombPlanted() && !grid.getMap()[positionX][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX,positionY-1),positionX,positionY-1);

            if(grid.getMap()[positionX+1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY),positionX+1,positionY);
            else if(!grid.getMap()[positionX+1][positionY].isBombPlanted() && !grid.getMap()[positionX+1][positionY].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY),positionX+1,positionY);

            if(grid.getMap()[positionX+1][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY-1),positionX+1,positionY-1);
            else if(!grid.getMap()[positionX+1][positionY-1].isBombPlanted() && !grid.getMap()[positionX+1][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY-1),positionX+1,positionY-1);

            if(grid.getMap()[positionX-1][positionY].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY),positionX-1,positionY);
            else if(!grid.getMap()[positionX-1][positionY].isBombPlanted() && !grid.getMap()[positionX-1][positionY].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY),positionX-1,positionY);

            if(grid.getMap()[positionX-1][positionY-1].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY-1),positionX-1,positionY-1);
            else if(!grid.getMap()[positionX-1][positionY-1].isBombPlanted() && !grid.getMap()[positionX-1][positionY-1].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY-1),positionX-1,positionY-1);

            if(grid.getMap()[positionX][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX,positionY+1),positionX,positionY+1);
            else if(!grid.getMap()[positionX][positionY+1].isBombPlanted() && !grid.getMap()[positionX][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX,positionY+1),positionX,positionY+1);

            if(grid.getMap()[positionX-1][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX-1,positionY+1),positionX-1,positionY+1);
            else if(!grid.getMap()[positionX-1][positionY+1].isBombPlanted() && !grid.getMap()[positionX-1][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX-1,positionY+1),positionX-1,positionY+1);

            if(grid.getMap()[positionX+1][positionY+1].isEmpty())
                emptyClicked(buttons.findButton(positionX+1,positionY+1),positionX+1,positionY+1);
            else if(!grid.getMap()[positionX+1][positionY+1].isBombPlanted() && !grid.getMap()[positionX+1][positionY+1].isFlagged())
                reveal(buttons.findButton(positionX+1,positionY+1),positionX+1,positionY+1);
        }
    }

    private void reveal(FieldButton button, int positionX, int positionY) {
        Image img;
        Image newimg;
        switch(grid.getMap()[button.getPositionX()][button.getPositionY()].getBombsNearby())
        {
            case 1:
                img = (new ImageIcon("one.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setEnabled(false);
                break;
            case 2:
                img = (new ImageIcon("one.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setEnabled(false);
                break;
            case 3:
                img = (new ImageIcon("one.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setEnabled(false);
                break;
            case 4:
                img = (new ImageIcon("one.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setEnabled(false);
                break;
            case 5:
                img = (new ImageIcon("one.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setEnabled(false);
                break;
            case 6:
                img = (new ImageIcon("one.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setEnabled(false);
                break;
            case 7:
                img = (new ImageIcon("one.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setEnabled(false);
                break;
            case 8:
                img = (new ImageIcon("one.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setEnabled(false);
                break;

        }
    }


}
