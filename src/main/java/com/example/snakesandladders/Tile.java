package com.example.snakesandladders;

public class Tile {
    private int id, x, y;
    private LadderSnakeEndTile snake = null;
    private LadderSnakeEndTile ladder = null;

    public Tile(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void setSnake(LadderSnakeEndTile snake) {
        this.snake = snake;
    }

    public void setLadder(LadderSnakeEndTile ladder) {
        this.ladder = ladder;
    }

    public LadderSnakeEndTile getSnake() {
        return snake;
    }

    public LadderSnakeEndTile getLadder() {
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
