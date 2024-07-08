# Mystery Maze

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Gameplay](#gameplay)
- [Controls](#controls)
- [Resources](#resources)
- [License](#license)
- [Contact](#contact)

## Introduction

Welcome to **Mystery Maze**, a thrilling adventure game developed in Java for a game hackathon. Navigate through intricate mazes, and uncover hidden secrets to reach your goal. The game challenges your strategic thinking and problem-solving skills, providing a captivating experience for players of all ages.

## Features

- **Single-player Mode**: Immerse yourself in solo maze exploration.
- **Hidden Secrets**: Discover hidden paths and rewards.

## Installation

You can run the Mystery Maze game using either the `.exe` file for Windows or the `.jar` file for any system with Java installed.

### Windows (Using the `.exe` file)

1. **Download the `mystery_maze.exe` file**.
2. **Download the `Assets` folder**
3. **Place both the folder and the `.exe` file in the same location**
4. **Double-click the `.exe` to Launch and start Playong the game**

### Cross-Platform (Using the `.jar` file)

1. **Download the `mystery_maze.jar` file**.
2. **Download the `Assets` folder**
3. **Place both the folder and the `.exe` file in the same location**
4. **Ensure you have Java Runtime Environment (JRE) installed**:
   - You can download it from [Oracle's website](https://www.oracle.com/java/technologies/javase-jre8-downloads.html).
5. **Run the Game**:
   - Open a terminal or command prompt.
   - Navigate to the directory where `mystery_maze.jar` is located.
   - Execute the following command:

     ```bash
     java -jar MysteryMaze.jar
     ```

## Gameplay

In Mystery Maze, your objective is to find the exit of each maze. Each level is filled with obstacles, and enemies. Use your wits and keen observation to navigate through the maze and uncover its secrets.

## Controls

- **Movement**: Use the arrow keys (Up, Down, Left, Right) or WASD to move through the maze.
- **Bombs**: Press the spacebar to drop bombs which will destroy spikes and kill enemies.
- **Keys**: Keys can be found scattered throughout the maze, use them to open and loot treasure chests.

## Resources

- **Game Development**: Learnt 2D Game Development using Swing Framework from [Zetcode](https://zetcode.com/javagames/) and [Learn Code by Gaming](https://learncodebygaming.com/blog/how-to-make-a-video-game-in-java-2d-basics).
- **Maze Generation**: Learnt about Procedural Maze Generation by following the code from [OpenGenus](https://iq.opengenus.org/maze-generator-in-java/)

## Development

### Requirements

- **Java Development Kit (JDK)** 8 or higher
- **IDE** VsCode for development

### Project Structure

```
mystery-maze-gameathon/
│
├── problem-statement.pdf         # Problem Statement for the hackathon
|
├── mystery_maze/                 # Source files
│   ├── Application.java          # Main class to start the game
│   ├── Board.java                # Core game logic
│   ├── Player.java               # Player character logic
│   ├── Maze.java                 # Maze generation and management
│   ├── Bomb.java                 # Bomb Timers
│   ├── Enemy.java                # Enemy AI Logic
│   └── Sprite.java               # Common functions for Player, Bomb and Enemy
│
├── Assets/                       # Game assets (images)
|
├── mystery_maze.jar              # Jar file to run the game
├── mystery_maze.exe              # Exe file to run the game
│
└── README.md                     # This readme file
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact

- **Email**: [21f3001823@ds.study.iitm.ac.in](mailto:21f3001823@ds.study.iitm.ac.in)
- **GitHub**: [Risdorn](https://github.com/Risdorn)
- **LinkedIn**: [Rishabh Indoria](https://www.linkedin.com/in/rishabh-indoria-687929205/)