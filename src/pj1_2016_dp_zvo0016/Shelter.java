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
public class Shelter extends GameEntity {

  private int health;

  private final Image texture2;
  private final Image texture3;
  private final Image texture4;

  public Shelter(Position position) {
    super(position, "Textures/Shelter/01.png", 0);

    this.texture2 = ImageUtil.loadImage("Textures/Shelter/02.png");
    this.texture3 = ImageUtil.loadImage("Textures/Shelter/03.png");
    this.texture4 = ImageUtil.loadImage("Textures/Shelter/04.png");

    this.health = 8;
  }

  @Override
  public void action() {
    if (this.health == 6 || this.health == 5) {
      this.setTexture(this.texture2);
    } else if (this.health == 4 || this.health == 3) {
      this.setTexture(this.texture3);

    } else if (this.health == 2 || this.health == 1) {
      this.setTexture(this.texture4);

    } else if (this.health <= 0) {
      this.setAlive(false);
    }
  }

  public int getHealth() {
    return this.health;
  }

  public void addHealth(int hp) {
    this.health += hp;
  }

}
