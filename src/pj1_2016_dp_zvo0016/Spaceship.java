/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2016_dp_zvo0016;

/**
 * @author Richard Zvonek
 * @version 1.0
 */
public class Spaceship extends GameEntity {

  private final Keyboard keyboard;

  private int lives;
  private int cooldown;

  public void setCooldown(int cooldown) {
    this.cooldown = cooldown;
  }

  public Spaceship(Position position, Keyboard keyboard) {
    super(position, "Textures/Spaceship/Spaceship.png", 4);
    this.keyboard = keyboard;
    this.lives = 3;
    this.cooldown = 20;

  }

  @Override
  public void action() {

    if (this.cooldown > 0) {
      this.cooldown--;
    }

    if (this.lives <= 0) {
      this.setAlive(false);
      return;
    }

    if (this.isAlive()) {
      if (this.keyboard.getKey(37)) {
        this.moveLeft();
      } else if (this.keyboard.getKey(39)) {
        this.moveRight();
      }
    }
  }

  public void addLives(int lives) {
    this.lives += lives;
  }

  public int getLives() {
    return this.lives;
  }

  public int getCooldown() {
    return this.cooldown;
  }

}
