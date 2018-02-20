public class Grid {
    private int size;
    private Field map[][];

    public Grid(int size)
    {
        this.size = size;
        map = new Field[size][size];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
        for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++)
                result.append(map[i][j]);
            result.append("\n");
        }

        return result.toString();
    }
}
