import javax.swing.*;

public class Field {
    private boolean isEmpty;
    private boolean isFlagged;
    private boolean hasBomb;
    private int bombsNearby;

    public Field()
    {
        isEmpty = true;
        isFlagged = false;
        hasBomb = false;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isBombPlanted() {
        return hasBomb;
    }

    public int getBombsNearby() {
        return bombsNearby;
    }

    public void setBombsNearby(int bombsNearby) {
        isEmpty = false;
        this.bombsNearby = bombsNearby;
    }

    public void plantBomb() {
        this.hasBomb = true;
        this.isEmpty = false;
    }

    public void disableBomb()
    {
        this.hasBomb = false;
        this.isEmpty = true;
    }

    @Override
    public String toString() {
        if(hasBomb)
            return "#";
        if(isEmpty)
            return " ";
        else
            return ""+bombsNearby;
    }
}
