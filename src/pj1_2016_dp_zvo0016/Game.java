/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2016_dp_zvo0016;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Richard Zvonek
 * @version 1.0
 */
public class Game {

  private boolean gameOver;
  private boolean started;
  private boolean gameWon;

  private int score;
  private int bestScore;

  private Spaceship spaceship;
  private final Keyboard keyboard;

  private List<GameEntity> entities;

  public Game(Keyboard keyboard) {
    this.keyboard = keyboard;
    this.bestScore = 0;
    this.score = 0;
    reload();
  }

  public void addScore(int add) {
    this.score += add;
  }

  public Keyboard getKeyboard() {
    return this.keyboard;
  }

  public List<GameEntity> getEntities() {
    return this.entities;
  }

  public int getScore() {
    return this.score;
  }

  public Spaceship getSpaceship() {
    return this.spaceship;
  }

  public boolean isGameOver() {
    return this.gameOver;
  }

  public boolean isStarted() {
    return this.started;
  }

  public void reload() {
    this.spaceship =
        new Spaceship(new Position(499 / 2, 448 - 32), this.keyboard);
    if (this.entities != null) {
      this.entities.clear();
    }
    this.entities = Collections.synchronizedList(new LinkedList<GameEntity>());
    this.entities.add(this.spaceship);
    for (int i = 0; i < 5; i++) {

      this.entities.add(new Shelter(new Position(56 + i * 90, 336)));
    }

    this.gameOver = false;
    this.started = false;
    this.gameWon = false;
    this.setBestScore();
    this.score = 0;
    for (int i = 1; i < 12; i++) {
      for (int j = 1; j < 6; j++) {

        switch (j) {
          case 1:
            this.entities.add(new Enemy(new Position(32 + 35 * i, 32 + 24 * j),
                "Textures/Enemy/Enemy1.png", "Textures/Enemy/Enemy1Alt.png",
                "Textures/Enemy/EnemyHit.png", 11));
            continue;
          case 2:
            this.entities.add(new Enemy(new Position(32 + 35 * i, 32 + 24 * j),
                "Textures/Enemy/Enemy2.png", "Textures/Enemy/Enemy2Alt.png",
                "Textures/Enemy/EnemyHit.png", 7));
            continue;
          case 3:
            this.entities.add(new Enemy(new Position(32 + 35 * i, 32 + 24 * j),
                "Textures/Enemy/Enemy2.png", "Textures/Enemy/Enemy2Alt.png",
                "Textures/Enemy/EnemyHit.png", 7));
            continue;
          case 4:
            this.entities.add(new Enemy(new Position(32 + 35 * i, 32 + 24 * j),
                "Textures/Enemy/Enemy3.png", "Textures/Enemy/Enemy3Alt.png",
                "Textures/Enemy/EnemyHit.png", 3));
            continue;
          case 5:
            this.entities.add(new Enemy(new Position(32 + 35 * i, 32 + 24 * j),
                "Textures/Enemy/Enemy3.png", "Textures/Enemy/Enemy3Alt.png",
                "Textures/Enemy/EnemyHit.png", 3));
            continue;
        }

      }
    }

  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setStarted(boolean started) {
    this.started = started;
  }

  public boolean isGameWon() {
    return this.gameWon;
  }

  public void setGameWon(boolean gameWon) {
    this.gameWon = gameWon;
  }

  public int getBestScore() {
    return this.bestScore;
  }

  public void setBestScore() {
    if (this.score >= this.bestScore) {
      this.bestScore = this.score;
    }
  }

}
