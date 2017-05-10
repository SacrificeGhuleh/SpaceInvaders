/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2016_dp_zvo0016;

import java.awt.Image;

/**
 * @author Richard Zvonek
 * @version 1.0
 */
public class Enemy extends GameEntity {

  private String moving;
  private int cooldown;

  private final int reward;

  private final Image primaryTexture;
  private final Image secondaryTexture;
  private final Image deadTexture;

  private int textureCooldown;

  public Enemy(Position position, String primary, String secondary, String dead,
      int reward) {
    super(position, primary, 0.4);
    this.primaryTexture = ImageUtil.loadImage(primary);
    this.secondaryTexture = ImageUtil.loadImage(secondary);
    this.deadTexture = ImageUtil.loadImage(dead);
    this.moving = "Right";
    this.reward = reward;
    this.cooldown = 0;
    this.textureCooldown = 10;
  }

  @Override
  public void action() {

    if (!isAlive()) {
      setTexture(this.deadTexture);
    } else if (this.textureCooldown == 0) {
      if (this.getTexture().hashCode() == this.primaryTexture.hashCode()) {
        setTexture(this.secondaryTexture);
      } else {
        setTexture(this.primaryTexture);
      }
      this.textureCooldown = 10;
    } else {
      this.textureCooldown--;
    }
    if (this.cooldown > 0) {
      this.cooldown--;
    }

    switch (this.moving) {

      case "Right":
        moveRight();
        return;
      case "Left":
        moveLeft();
        return;
      default:
        return;
    }

  }

  public String getMoving() {
    return this.moving;
  }

  public int getReward() {
    return this.reward;
  }

  public void moveDown() {
    setPosition(new Position(this.position.getX(), this.position.getY() + 8));
  }

  @Override
  public void moveLeft() {
    if (this.position.getX() > 0) {
      setPosition(
          new Position(this.position.getX() - this.step, this.position.getY()));
    }

  }

  @Override
  public void moveRight() {
    if (this.position.getX() + this.getTexture().getWidth(null) < 512) {
      setPosition(
          new Position(this.position.getX() + this.step, this.position.getY()));
    }

  }

  public void setMoving(String moving) {
    this.moving = moving;
  }

  public int getCooldown() {
    return this.cooldown;
  }

  public void setCooldown(int cooldown) {
    this.cooldown = cooldown;
  }

}
