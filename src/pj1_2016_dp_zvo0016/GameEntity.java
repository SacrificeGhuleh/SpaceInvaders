/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2016_dp_zvo0016;

import java.awt.Image;
import java.awt.Rectangle;

/**
 * @author Richard Zvonek
 * @version 1.0
 */
public abstract class GameEntity {

  protected double step;

  private boolean alive;

  public Position position;

  private Image texture;

  public Image getTexture() {
    return this.texture;
  }

  public void setTexture(Image texture) {
    this.texture = texture;
  }

  public GameEntity(Position position, String imagePath, double step) {
    this.position = position;
    this.texture = ImageUtil.loadImage(imagePath);
    this.step = step;
    this.setAlive(true);
  }

  public abstract void action();

  public void setTexture(String path) {
    this.texture = ImageUtil.loadImage(path);
  }

  public Rectangle getCollisionBox() {
    return new Rectangle((int) this.getPosition().getX(),
        (int) this.getPosition().getY(), this.texture.getHeight(null),
        this.texture.getWidth(null));
  }

  public Position getPosition() {
    return this.position;
  }

  public double getStep() {
    return this.step;
  }

  public boolean inPanel() {
    return this.position.getX() + this.texture.getWidth(null) <= 512
        && this.position.getX() >= 0
        && this.position.getY() + this.texture.getHeight(null) <= 448
        && this.position.getY() >= 0;

  }

  public boolean isAlive() {
    return this.alive;
  }

  public void moveLeft() {
    if (this.position.getX() > 32) {
      setPosition(
          new Position(this.position.getX() - this.step, this.position.getY()));
    }

  }

  public void moveRight() {
    if (this.position.getX() + this.texture.getWidth(null) < 512 - 32) {
      setPosition(
          new Position(this.position.getX() + this.step, this.position.getY()));
    }

  }

  public boolean onEdge() {
    return (int) this.position.getX() + this.texture.getWidth(null) + 33 > 512
        || (int) this.position.getX() < 33
        || (int) this.position.getY() + this.texture.getHeight(null) + 33 > 448
        || (int) this.position.getY() < 33;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public void setStep(double step) {
    this.step = step;
  }

}
