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
    JPanel panel;

    public GameComponent(){
        grid = new Grid(16,32,99);
        buttons = new ButtonList();
        panel = new JPanel();
        //setLayout(new BorderLayout());

        panel.setLayout(new GridLayout(grid.getxSize(), grid.getySize()));


        for (int i = 0; i < grid.getxSize(); i++)
            for(int j=0; j<grid.getySize(); j++)
                addFieldButton(i,j);

        //cheat();

    }

    public void reset()
    {
        panel.removeAll();
        buttons.removeAll();
        panel.setLayout(new GridLayout(grid.getxSize(), grid.getySize()));
        for (int i = 0; i < grid.getxSize(); i++)
            for(int j=0; j<grid.getySize(); j++)
                addFieldButton(i,j);
        panel.repaint();
    }

    private void cheat() {
        for (int i = 0; i < grid.getxSize(); i++)
            for (int j = 0; j < grid.getySize(); j++) {
                if (grid.getMap()[i][j].isBombPlanted()) {
                    Image img = (new ImageIcon("images/bomb.png")).getImage();
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
                            Image img = (new ImageIcon("images/flag.png")).getImage();
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
                        if(!grid.getMap()[button.getPositionX()][button.getPositionY()].isFlagged() && button.isEnabled())
                        {
                            if(grid.getMap()[button.getPositionX()][button.getPositionY()].isBombPlanted()) {
                                revealAllBombs(button.getWidth()-5, button.getHeight()-5);
                            }
                            else if(grid.getMap()[button.getPositionX()][button.getPositionY()].isEmpty())
                            {
                                emptyClicked(button);
                            }
                            else
                                reveal(button);

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
        panel.add(button);
        buttons.add(button);
    }

    private void revealAllBombs(int width, int height) {
        for(int i=0; i<grid.getxSize(); i++)
            for(int j=0; j<grid.getySize(); j++)
            {
                if(grid.getMap()[i][j].isBombPlanted()) {
                    Image img = (new ImageIcon("images/bomb.png")).getImage();
                    Image newimg = img.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH );
                    buttons.findButton(i, j).setIcon(new ImageIcon( newimg ));
                }
                else
                {
                    buttons.findButton(i,j).setEnabled(false);
                }
            }
    }

    private void emptyClicked(FieldButton button) {

        Stack<FieldButton> stack = new Stack();
        stack.push(button);

        while (!stack.isEmpty()) {

            FieldButton b = stack.pop();
            int positionX = b.getPositionX();
            int positionY = b.getPositionY();
            if (grid.getMap()[b.getPositionX()][b.getPositionY()].isEmpty()) {
                b.setEnabled(false);
                b.setBackground(Color.white);
                grid.getMap()[positionX][positionY].setRevealed(true);
            } else {
                reveal(b);
                grid.getMap()[positionX][positionY].setRevealed(true);
                continue;
            }

            if (positionX == 0 && positionY == 0) {
                if (!grid.getMap()[positionX][positionY + 1].isRevealed() && !grid.getMap()[positionX][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY + 1))) {
                    if (!grid.getMap()[positionX][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY + 1));
                }if (!grid.getMap()[positionX+1][positionY + 1].isRevealed() && !grid.getMap()[positionX + 1][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY + 1))) {
                    if (!grid.getMap()[positionX + 1][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY + 1));
                } if (!grid.getMap()[positionX+1][positionY].isRevealed() && !grid.getMap()[positionX + 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY))) {
                    if (!grid.getMap()[positionX + 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY));
                }
            } else if (positionX == 0 && positionY == grid.getySize() - 1) {
                if (!grid.getMap()[positionX][positionY - 1].isRevealed() && !grid.getMap()[positionX][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY - 1))) {
                    if (!grid.getMap()[positionX][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY - 1));
                } if (!grid.getMap()[positionX+1][positionY - 1].isRevealed() && !grid.getMap()[positionX + 1][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY - 1))) {
                    if (!grid.getMap()[positionX + 1][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY - 1));
                } if (!grid.getMap()[positionX+1][positionY].isRevealed() && !grid.getMap()[positionX + 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY))) {
                    if (!grid.getMap()[positionX + 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY));
                }
            } else if (positionX == grid.getxSize() - 1 && positionY == 0) {
                if (!grid.getMap()[positionX][positionY + 1].isRevealed() && !grid.getMap()[positionX][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY + 1))) {
                    if (!grid.getMap()[positionX][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY + 1));
                } if (!grid.getMap()[positionX-1][positionY+1].isRevealed() && !grid.getMap()[positionX - 1][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY + 1))) {
                    if (!grid.getMap()[positionX - 1][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY + 1));
                } if (!grid.getMap()[positionX-1][positionY ].isRevealed() && !grid.getMap()[positionX - 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY))) {
                    if (!grid.getMap()[positionX - 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY));
                }
            } else if (positionX == grid.getxSize() - 1 && positionY == grid.getySize() - 1) {
                if (!grid.getMap()[positionX][positionY - 1].isRevealed() && !grid.getMap()[positionX][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY - 1))) {
                    if (!grid.getMap()[positionX][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY - 1));
                } if (!grid.getMap()[positionX-1][positionY - 1].isRevealed() && !grid.getMap()[positionX - 1][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY - 1))) {
                    if (!grid.getMap()[positionX - 1][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY - 1));
                } if (!grid.getMap()[positionX-1][positionY].isRevealed() && !grid.getMap()[positionX - 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY))) {
                    if (!grid.getMap()[positionX - 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY));
                }
            } else if (positionX == 0 && positionY > 0 && positionY < grid.getySize() - 1) {
                if (!grid.getMap()[positionX][positionY + 1].isRevealed() && !grid.getMap()[positionX][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY + 1))) {
                    if (!grid.getMap()[positionX][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY + 1));
                } if (!grid.getMap()[positionX+1][positionY + 1].isRevealed() && !grid.getMap()[positionX + 1][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY + 1))) {
                    if (!grid.getMap()[positionX + 1][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY + 1));
                } if (!grid.getMap()[positionX+1][positionY].isRevealed() && !grid.getMap()[positionX + 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY))) {
                    if (!grid.getMap()[positionX + 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY));
                } if (!grid.getMap()[positionX][positionY - 1].isRevealed() && !grid.getMap()[positionX][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY - 1))) {
                    if (!grid.getMap()[positionX][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY - 1));
                } if (!grid.getMap()[positionX+1][positionY - 1].isRevealed() && !grid.getMap()[positionX + 1][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY - 1))) {
                    if (!grid.getMap()[positionX + 1][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY - 1));
                }

            } else if (positionX == grid.getxSize() - 1 && positionY > 0 && positionY < grid.getySize() - 1) {
                if (!grid.getMap()[positionX][positionY + 1].isRevealed() && !grid.getMap()[positionX][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY + 1))) {
                    if (!grid.getMap()[positionX][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY + 1));
                } if (!grid.getMap()[positionX-1][positionY + 1].isRevealed() && !grid.getMap()[positionX - 1][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY + 1))) {
                    if (!grid.getMap()[positionX - 1][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY + 1));
                } if (!grid.getMap()[positionX-1][positionY].isRevealed() && !grid.getMap()[positionX - 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY))) {
                    if (!grid.getMap()[positionX - 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY));
                } if (!grid.getMap()[positionX][positionY - 1].isRevealed() && !grid.getMap()[positionX][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY - 1))) {
                    if (!grid.getMap()[positionX][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY - 1));
                } if (!grid.getMap()[positionX-1][positionY - 1].isRevealed() && !grid.getMap()[positionX - 1][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY - 1))) {
                    if (!grid.getMap()[positionX - 1][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY - 1));
                }
            } else if (positionY == 0 && positionX > 0 && positionX < grid.getxSize() - 1) {
                if (!grid.getMap()[positionX][positionY + 1].isRevealed() && !grid.getMap()[positionX][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY + 1))) {
                    if (!grid.getMap()[positionX][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY + 1));
                } if (!grid.getMap()[positionX+1][positionY + 1].isRevealed() && !grid.getMap()[positionX + 1][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY + 1))) {
                    if (!grid.getMap()[positionX + 1][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY + 1));
                } if (!grid.getMap()[positionX+1][positionY].isRevealed() && !grid.getMap()[positionX + 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY))) {
                    if (!grid.getMap()[positionX + 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY));
                } if (!grid.getMap()[positionX-1][positionY].isRevealed() && !grid.getMap()[positionX - 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY))) {
                    if (!grid.getMap()[positionX - 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY));
                } if (!grid.getMap()[positionX-1][positionY + 1].isRevealed() && !grid.getMap()[positionX - 1][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY + 1))) {
                    if (!grid.getMap()[positionX - 1][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY + 1));
                }
            } else if (positionY == grid.getySize() - 1 && positionX > 0 && positionX < grid.getxSize() - 1) {
                if (!grid.getMap()[positionX][positionY - 1].isRevealed() && !grid.getMap()[positionX][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY - 1))) {
                    if (!grid.getMap()[positionX][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY - 1));
                } if (!grid.getMap()[positionX+1][positionY - 1].isRevealed() && !grid.getMap()[positionX + 1][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY - 1))) {
                    if (!grid.getMap()[positionX + 1][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY - 1));
                } if (!grid.getMap()[positionX+1][positionY].isRevealed() && !grid.getMap()[positionX + 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY))) {
                    if (!grid.getMap()[positionX + 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY));
                } if (!grid.getMap()[positionX-1][positionY - 1].isRevealed() && !grid.getMap()[positionX - 1][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY - 1))) {
                    if (!grid.getMap()[positionX - 1][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY - 1));
                } if (!grid.getMap()[positionX-1][positionY].isRevealed() && !grid.getMap()[positionX - 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY))) {
                    if (!grid.getMap()[positionX - 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY));
                }
            } else {
                if (!grid.getMap()[positionX][positionY + 1].isRevealed() && !grid.getMap()[positionX][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY + 1))) {
                    if (!grid.getMap()[positionX][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY + 1));
                } if (!grid.getMap()[positionX+1][positionY + 1].isRevealed() && !grid.getMap()[positionX + 1][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY + 1))) {
                    if (!grid.getMap()[positionX + 1][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY + 1));
                } if (!grid.getMap()[positionX+1][positionY].isRevealed() && !grid.getMap()[positionX + 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY))) {
                    if (!grid.getMap()[positionX + 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY));
                } if (!grid.getMap()[positionX][positionY - 1].isRevealed() && !grid.getMap()[positionX][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX, positionY - 1))) {
                    if (!grid.getMap()[positionX][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX, positionY - 1));
                } if (!grid.getMap()[positionX+1][positionY - 1].isRevealed() && !grid.getMap()[positionX + 1][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX + 1, positionY - 1))) {
                    if (!grid.getMap()[positionX + 1][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX + 1, positionY - 1));
                } if (!grid.getMap()[positionX-1][positionY + 1].isRevealed() && !grid.getMap()[positionX - 1][positionY + 1].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY + 1))) {
                    if (!grid.getMap()[positionX - 1][positionY + 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY + 1));
                } if (!grid.getMap()[positionX-1][positionY - 1].isRevealed() && !grid.getMap()[positionX - 1][positionY - 1].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY - 1))) {
                    if (!grid.getMap()[positionX - 1][positionY - 1].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY - 1));
                } if (!grid.getMap()[positionX-1][positionY].isRevealed() && !grid.getMap()[positionX - 1][positionY].isFlagged() && !stack.contains(buttons.findButton(positionX - 1, positionY))) {
                    if (!grid.getMap()[positionX - 1][positionY].isBombPlanted())
                        stack.push(buttons.findButton(positionX - 1, positionY));
                }
            }
        }
    }

    private void reveal(FieldButton button) {
        Image img;
        Image newimg;
        switch(grid.getMap()[button.getPositionX()][button.getPositionY()].getBombsNearby())
        {
            case 1:
                img = (new ImageIcon("images/one.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setBackground(Color.white);
                break;
            case 2:
                img = (new ImageIcon("images/two.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setBackground(Color.white);
                break;
            case 3:
                img = (new ImageIcon("images/three.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setBackground(Color.white);
                break;
            case 4:
                img = (new ImageIcon("images/four.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setBackground(Color.white);
                break;
            case 5:
                img = (new ImageIcon("images/five.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setBackground(Color.white);
                break;
            case 6:
                img = (new ImageIcon("images/six.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setBackground(Color.white);
                break;
            case 7:
                img = (new ImageIcon("images/seven.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setBackground(Color.white);
                break;
            case 8:
                img = (new ImageIcon("images/eight.png")).getImage();
                newimg = img.getScaledInstance( button.getWidth()-5, button.getHeight()-5,  java.awt.Image.SCALE_SMOOTH );
                button.setIcon(new ImageIcon( newimg ));
                button.setBackground(Color.white);
                break;

        }
    }


}
