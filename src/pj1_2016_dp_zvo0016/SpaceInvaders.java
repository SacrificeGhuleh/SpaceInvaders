/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2016_dp_zvo0016;

import javax.swing.JFrame;

/**
 * @author Richard Zvonek
 * @version 1.0
 */
public class SpaceInvaders {

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setTitle("Space Invaders | Richard Zvonek | 2016");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.addKeyListener(Keyboard.getInstance());
    frame.add(new GamePanel(Keyboard.getInstance()));
    frame.setSize(512, 512);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

}
