import javax.swing.*;

public class Field {
    private boolean isEmpty;
    private boolean isFlagged;
    private boolean hasBomb;
    private int bombsNearby;
    private boolean revealed;

    public Field()
    {
        isEmpty = true;
        isFlagged = false;
        hasBomb = false;
        revealed = false;
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

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
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

    public void changeFlag() {
        if(isFlagged)
            isFlagged=false;
        else
            isFlagged=true;
    }
}
