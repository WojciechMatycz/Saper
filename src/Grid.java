import javax.swing.*;
import java.util.Random;

public class Grid extends JButton {
    private int xSize;
    private int ySize;
    private Field map[][];

    public Grid(int xSize, int ySize, int bombsAmount)
    {
        this.xSize = xSize;
        this.ySize = ySize;
        map = new Field[xSize][ySize];
        setEmpty();
        putBombs(bombsAmount);
        putValues();
    }

    private void setEmpty() {
        for(int i=0; i<xSize; i++)
            for(int j=0; j<ySize; j++)
                map[i][j] = new Field();
    }


    public void putBombs(int amount)
    {
        Random r = new Random();
        while(amount > 0)
        {
            int x = r.nextInt(xSize);
            int y = r.nextInt(ySize);
            if(canPlaceABombHere(x,y)) {
                map[x][y].plantBomb();
                amount--;
            }

        }
    }

    private boolean canPlaceABombHere(int x, int y) {
        if(map[x][y].isBombPlanted())
            return false;
        if (((x==0 && y==0) || (x==0 && y==ySize-1) || (x==xSize-1 && y==ySize-1) || (x==xSize-1 && y==0)) && isCornerBlocked(x,y))
            return false;
        if(((y>0 && y<ySize-1) &&(x==0 || x==xSize-1)) || ((x>0 && x<xSize-1)&&(y==0 || y==ySize-1)) && isWallBlocked(x,y))
            return false;
        if ((x>0 && x<xSize-1 && y>0 && y<ySize-1) && isSurrounded(x,y))
            return false;
        else
            return true;
    }

    private boolean isSurrounded(int x, int y) {
        if(map[x+1][y-1].isBombPlanted() && map[x+1][y].isBombPlanted() && map[x+1][y+1].isBombPlanted() &&
                map[x-1][y-1].isBombPlanted() && map[x-1][y].isBombPlanted() && map[x-1][y+1].isBombPlanted()
                && map[x][y+1].isBombPlanted() && map[x][y-1].isBombPlanted())
            return true;
        else
            return false;
    }

    private boolean isWallBlocked(int x, int y) {
        if(x==0 && map[x+1][y-1].isBombPlanted() && map[x+1][y].isBombPlanted() &&
                map[x+1][y+1].isBombPlanted() && map[x][y-1].isBombPlanted() && map[x][y+1].isBombPlanted())
            return true;

        if(y==0 && map[x+1][y].isBombPlanted() && map[x+1][y+1].isBombPlanted() &&
                map[x-1][y].isBombPlanted() && map[x][y+1].isBombPlanted() && map[x-1][y+1].isBombPlanted())
            return true;

        if(x==xSize-1 && map[x][y-1].isBombPlanted() && map[x][y+1].isBombPlanted() &&
                map[x-1][y].isBombPlanted() && map[x-1][y-1].isBombPlanted() && map[x-1][y+1].isBombPlanted())
            return true;

        if(y==ySize-1 && map[x-1][y-1].isBombPlanted() && map[x][y-1].isBombPlanted() &&
                map[x+1][y-1].isBombPlanted() && map[x-1][y].isBombPlanted() && map[x+1][y-1].isBombPlanted())
            return true;

        return false;
    }

    private boolean isCornerBlocked(int x, int y) {
        if((x==0 && y==0) && map[x+1][y].isBombPlanted() &&
                map[x+1][y+1].isBombPlanted() && map[x][y+1].isBombPlanted())
            return true;

        if((x==xSize-1 && y==0) && map[x][y+1].isBombPlanted() &&
                map[x-1][y+1].isBombPlanted() && map[x-1][y].isBombPlanted())
            return true;

        if((x==0 && y==ySize-1) && map[x+1][y].isBombPlanted() &&
                map[x+1][y-1].isBombPlanted() && map[x][y-1].isBombPlanted())
            return true;

        if((x==xSize-1 && y==ySize-1) && map[x-1][y].isBombPlanted() &&
                map[x-1][y-1].isBombPlanted() && map[x][y-1].isBombPlanted())
            return true;

        return false;
    }

    public void putValues()
    {
        int bombs;
        for(int i=0; i<xSize; i++)
            for(int j=0; j<ySize; j++)
                if(map[i][j].isEmpty())
                {
                    bombs = countBombsAround(i,j);
                    if(bombs!=0)
                        map[i][j].setBombsNearby(bombs);
                }
    }

    private int countBombsAround(int x, int y) {
        if((x==0 && y==0) || (x==0 && y==ySize-1) || (x==xSize-1 && y==ySize-1) || (x==xSize-1 && y==0))
        {
            return countIfCorner(x,y);
        }
        else if(x==0 || x==xSize-1 || y==0 || y==ySize-1)
        {
            return countIfWall(x,y);
        }
        else
            return countIfSurrounded(x,y);
    }

    private int countIfSurrounded(int x, int y) {
        int bombs = 0;
        if (map[x+1][y-1].isBombPlanted())
            bombs++;
        if (map[x+1][y].isBombPlanted())
            bombs++;
        if (map[x+1][y+1].isBombPlanted())
            bombs++;
        if (map[x][y + 1].isBombPlanted())
            bombs++;
        if (map[x][y-1].isBombPlanted())
            bombs++;
        if (map[x-1][y-1].isBombPlanted())
            bombs++;
        if (map[x-1][y].isBombPlanted())
            bombs++;
        if (map[x-1][y+1].isBombPlanted())
            bombs++;

        return bombs;
    }

    private int countIfWall(int x, int y) {
        int bombs = 0;
        if(x==0)
        {
            if (map[x+1][y-1].isBombPlanted())
                bombs++;
            if (map[x+1][y].isBombPlanted())
            bombs++;
            if (map[x+1][y+1].isBombPlanted())
                bombs++;
            if (map[x][y + 1].isBombPlanted())
                bombs++;
            if (map[x ][y-1].isBombPlanted())
                bombs++;

            return bombs;
        }
        if(x==xSize-1)
        {
            if (map[x-1][y-1].isBombPlanted())
                bombs++;
            if (map[x-1][y].isBombPlanted())
                bombs++;
            if (map[x-1][y+1].isBombPlanted())
                bombs++;
            if (map[x][y + 1].isBombPlanted())
                bombs++;
            if (map[x][y-1].isBombPlanted())
                bombs++;

            return bombs;
        }
        if(y==0)
        {
            if (map[x-1][y].isBombPlanted())
                bombs++;
            if (map[x+1][y].isBombPlanted())
                bombs++;
            if (map[x-1][y+1].isBombPlanted())
                bombs++;
            if (map[x][y+1].isBombPlanted())
                bombs++;
            if (map[x+1][y+1].isBombPlanted())
                bombs++;

            return bombs;
        }
        else
        {
            if (map[x-1][y].isBombPlanted())
                bombs++;
            if (map[x+1][y].isBombPlanted())
                bombs++;
            if (map[x-1][y-1].isBombPlanted())
                bombs++;
            if (map[x][y-1].isBombPlanted())
                bombs++;
            if (map[x+1][y-1].isBombPlanted())
                bombs++;

            return bombs;
        }
    }

    private int countIfCorner(int x, int y) {
        int bombs = 0;
        if(x==0 && y==0) {
            if (map[x + 1][y + 1].isBombPlanted())
                bombs++;
            if (map[x][y + 1].isBombPlanted())
                bombs++;
            if (map[x + 1][y].isBombPlanted())
                bombs++;

            return bombs;
        }
        else if((x==0 && y==ySize-1))
        {
            if (map[x + 1][y].isBombPlanted())
                bombs++;
            if (map[x][y-1].isBombPlanted())
                bombs++;
            if (map[x][y-1].isBombPlanted())
                bombs++;

            return bombs;
        }
        else if((x==xSize-1 && y==ySize-1))
        {
            if (map[x-1][y].isBombPlanted())
                bombs++;
            if (map[x][y-1].isBombPlanted())
                bombs++;
            if (map[x-1][y-1].isBombPlanted())
                bombs++;

            return bombs;
        }
        else
        {
            if (map[x-1][y].isBombPlanted())
                bombs++;
            if (map[x-1][y+1].isBombPlanted())
                bombs++;
            if (map[x][y+1].isBombPlanted())
                bombs++;

            return bombs;
        }

    }


    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int getySize() {
        return ySize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public Field[][] getMap() {
        return map;
    }

    public void setMap(Field[][] map) {
        this.map = map;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<xSize; i++) {
            for (int j = 0; j < ySize; j++)
                result.append(map[i][j]);
            result.append("\n");
        }

        return result.toString();
    }
}
