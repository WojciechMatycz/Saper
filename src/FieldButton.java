import javax.swing.*;
import java.awt.event.ActionListener;

public class FieldButton extends JButton {
    private int positionX;
    private int positionY;

    public FieldButton(int x, int y) {
        super();
        this.positionX = x;
        this.positionY = y;
    }


    public int getPositionX() {
        return positionX;
    }

    public void setX(int x) {
        this.positionX = x;
    }


    public int getPositionY() {
        return positionY;
    }

    public void setY(int y) {
        this.positionY = y;
    }
}
