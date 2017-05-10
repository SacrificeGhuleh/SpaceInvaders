/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2016_dp_zvo0016;

/**
 * @author Richard Zvonek
 * @version 1.0
 */
public class Position {

  private double x;
  private double y;

  Position() {
    this(0, 0);
  }

  Position(double x, double y) {
    this.setX(x);
    this.setY(y);
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }
}
