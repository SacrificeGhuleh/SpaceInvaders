/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2016_dp_zvo0016;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Richard Zvonek
 * @version 1.0
 */
public class Keyboard implements KeyListener {
  private static Keyboard instance;

  static Keyboard getInstance() {
    if (instance == null) {
      instance = new Keyboard();
    }
    return instance;
  }

  private final boolean[] keys;

  private Keyboard() {
    this.keys = new boolean[256];
  }

  public boolean getKey(int index) {
    return this.keys[index];
  }

  public boolean[] getKeys() {
    return this.keys;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (this.keys[e.getKeyCode()] != true && e.getKeyCode() >= 0
        && e.getKeyCode() < this.keys.length) {
      this.keys[e.getKeyCode()] = true;
    }
  }

  @Override

  public void keyReleased(KeyEvent e) {
    if (this.keys[e.getKeyCode()] != false && e.getKeyCode() >= 0
        && e.getKeyCode() < this.keys.length) {
      this.keys[e.getKeyCode()] = false;

    }

  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
  }

}
