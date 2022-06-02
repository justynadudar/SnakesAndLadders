package sample;

public class Tile {
    private int id, x, y;
    private LadderSnakeTile snake = null;
    private LadderSnakeTile ladder = null;

    public Tile(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void setSnake(LadderSnakeTile snake) {
        this.snake = snake;
    }

    public void setLadder(LadderSnakeTile ladder) {
        this.ladder = ladder;
    }

    public LadderSnakeTile getSnake() {
        return snake;
    }

    public LadderSnakeTile getLadder() {
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
