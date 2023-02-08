# SnakesAndLadders
SnakesAndLadders is a two-player game written in JavaFX. It is written based on the concept of Network Programming, so it can be played by people using the same network. Each player's game is handled on separate threads.

## Table of contents
* [Technologies](#technologies)
* [Features](#features)
* [Setup](#setup)
* [Examples of use](#examples-of-use)

## Technologies:
- JavaFX 19
- Scene Builder

## Features:
- Setting up a game server
- Connecting the client to the server
- IP input validation
- Handling the players' game (drawing the dice value, movement of the pawns, handling winnings, handling when a player leaves the game)

## Setup:
Download the SnakesAndLadders.jar file located at out/artifacts/SnakesAndLadders_jar and run it by double clicking on it.

## Examples of use:
### If a player starts the game first it means that he will be the server in the game.</br>
![image](https://user-images.githubusercontent.com/62484042/217391797-63136148-6793-4dbe-b594-2e3afa0704f7.png) </br>
</br>

### The IP address and the specified port number are valid.</br>
![image](https://user-images.githubusercontent.com/62484042/217392025-5cd457d9-3d04-4a54-8a29-12f333314e42.png)
![image](https://user-images.githubusercontent.com/62484042/217392357-16c3149f-f6e7-46ad-aac9-155ee3f3c58f.png)</br>
</br>

### If a user wants to connect to the server, he should enter the server's IP address and the selected port.</br>
![image](https://user-images.githubusercontent.com/62484042/217392417-80cdfbfc-9409-41ea-bcf2-5584fb2b2625.png)</br>
</br>

### The player who created the server must wait for an opponent to join.</br>
![image](https://user-images.githubusercontent.com/62484042/217392439-02218aa1-4afe-444e-991c-2184e713b34e.png)</br>
</br>

### Once an opponent has joined, the game starts with the first player. Each player sees his pawn as blue and the opponent's as yellow.</br>
![image](https://user-images.githubusercontent.com/62484042/217392604-289a4b35-9488-49f6-8f76-0f33d1007fee.png)</br>
</br>

### In order to move from the starting position, players must draw 6 eyes on the dice. The second player always sees what his opponent has drawn.</br>
![image](https://user-images.githubusercontent.com/62484042/217392686-34c3efd5-25f3-47f2-9951-bb2daaba1e13.png)</br>
</br>

### After drawing 6 eyes, a player can move from the first tile. The other player also sees the user's pawn being moved. In this situation, when the user drew 6 eyes, he was placed on the tile that was the beginning of the ladder, which moved him to the tile with the number 29. If the user had stood on the tile with the snake's head, he would have been moved back to the tile marked with the end of the snake's tail. </br>
![image](https://user-images.githubusercontent.com/62484042/217392879-4c53e26e-8796-4f52-9c1d-a17d82bf4bc5.png)</br>
</br>

### The user wins only when he stands exactly on the tile with the number 100.</br>
![image](https://user-images.githubusercontent.com/62484042/217393135-daad8deb-74ce-4afc-bd7e-2720bbea6bb3.png)</br>
</br>

### After winning, players can start the game again, or leave it.</br>
![image](https://user-images.githubusercontent.com/62484042/217393181-052a1330-1b5e-4226-8cf1-3a45c699dec9.png)</br>
</br>

### When one user leaves the game, the other gets a message about it.</br>
![image](https://user-images.githubusercontent.com/62484042/217393227-991884f4-d21f-4606-8ef9-f48a2d41a2cb.png)</br>
</br>

