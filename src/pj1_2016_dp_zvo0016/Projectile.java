/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2016_dp_zvo0016;

/**
 * @author Richard Zvonek
 * @version 1.0
 */
public class Projectile extends GameEntity {

  private final boolean enemy;

  public Projectile(Position position, boolean enemy) {
    super(position, "Textures/Projectile/Projectile.png", 7);
    this.enemy = enemy;
  }

  @Override
  public void action() {
    if (this.enemy) {
      moveDown();
    } else {
      moveUp();
    }
    if (!this.inPanel()) {
      this.setAlive(false);
    }
  }

  public boolean enemyProjectile() {
    return this.enemy;
  }

  private void moveDown() {
    setPosition(
        new Position(this.position.getX(), this.position.getY() + this.step));
  }

  private void moveUp() {
    setPosition(
        new Position(this.position.getX(), this.position.getY() - this.step));
  }

  public boolean playerProjectile() {
    return !this.enemy;
  }

}
