package sample;

public class Tile {
    private int id, x, y;
    private Snake snake = null;
    private Ladder ladder = null;

    public Tile(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Tile(int id, int x, int y, Snake snake, Ladder ladder){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setLadder(Ladder ladder) {
        this.ladder = ladder;
    }

    public Snake getSnake() {
        return snake;
    }

    public Ladder getLadder() {
        return ladder;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "id: " + id +
                ", x: " + x +
                ", y: " + y +
                '}';
    }
}
