/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2016_dp_zvo0016;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

/**
 * @author Richard Zvonek
 * @version 1.0
 */
public class GamePanel extends JPanel implements Runnable {

  private final Image logo;
  private final Image scoreboard;
  private Font font;

  private static final long serialVersionUID = 6306387013100409037L;
  private final Game game;

  public GamePanel(Keyboard keyboard) {
    super();
    this.logo = ImageUtil.loadImage("Textures/Logo.png");
    this.scoreboard = ImageUtil.loadImage("Textures/Scoreboard.png");
    this.addKeyListener(keyboard);
    this.game = new Game(keyboard);
    this.setSize(512, 448);
    this.setBackground(Color.BLACK);
    this.setVisible(true);

    try {
      this.font = Font.createFont(Font.TRUETYPE_FONT,
          new FileInputStream("Textures/Minecraftia-Regular.ttf"));
      this.font = this.font.deriveFont(18F);
    } catch (FontFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    new Thread(this).start();
  }

  public Game getGame() {
    return this.game;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2D = (Graphics2D) g;
    g2D.setColor(Color.WHITE);

    g2D.setFont(this.font);

    if (this.game.isStarted()) {
      for (GameEntity ge : this.game.getEntities()) {
        g2D.drawImage(ge.getTexture(), (int) ge.position.getX(),
            (int) ge.position.getY(), null);

        g2D.drawString(
            "Best score: " + Integer.toString(this.game.getBestScore()), 32,
            32);

        g2D.drawString("Score: " + Integer.toString(this.game.getScore()), 32,
            480);
        g2D.drawString(
            "Lives: " + Integer.toString(this.game.getSpaceship().getLives()),
            390, 480);
        g2D.drawLine(32, this.getHeight() - 32, this.getWidth() - 32,
            this.getHeight() - 32);
      }
    }

    else if (!this.game.isStarted() && !this.game.isGameOver()) {

      g2D.drawImage(this.logo, 63, 32, null);
      g2D.drawImage(this.scoreboard, 216, 216 + 16, null);
      g2D.drawString("Press any key to start", 150, 360 + 32);

    }

    else if (this.game.isGameOver() && !this.game.isStarted()) {

      if (this.game.isGameWon()) {
        g2D.drawString("This time you won.", 16, 180);
      } else {
        g2D.drawString("You lost, you wretched earthling.", 73, 150);
        g2D.drawString("The planet is ours!", 156, 180);
      }
      g2D.drawString("Your score: " + Integer.toString(this.game.getScore()),
          165, 210);
      g2D.drawString("Press space to restart", 124, 240);
    }
  }

  private int countEnemies() {
    int counter = 0;
    for (GameEntity ge : this.game.getEntities()) {
      if (ge instanceof Enemy) {
        counter++;
      }
    }
    return counter;
  }

  private void removeDead() {
    for (Iterator<GameEntity> iter =
        this.game.getEntities().listIterator(); iter.hasNext();) {
      GameEntity ge = iter.next();
      if (!ge.isAlive()) {
        iter.remove();
      }
    }
  }

  private void checkCollisions() throws InterruptedException {
    for (GameEntity ge : this.getGame().getEntities()) {
      for (GameEntity ge2 : this.getGame().getEntities()) {

        if (ge == ge2) {
          break;
        }

        if (ge instanceof Enemy) {

          if (!ge.getCollisionBox()
              .intersects(new Rectangle(0, 0, 512, 448 - 64))) {
            gameOver(false);
          }

          //BLOCK: KOLIZE ENEMY A SHELTER
          if (ge2 instanceof Shelter
              && ge.getCollisionBox().intersects(ge2.getCollisionBox())) {
            ge2.setAlive(false);
          } //BLOCK: KOLIZE ENEMY A SHELTER
        }

        if (ge instanceof Projectile) {

          //BLOCK: KOLIZE PROJECTILE A SPACESHIP
          if (((Projectile) ge).enemyProjectile() && ge.getCollisionBox()
              .intersects(this.game.getSpaceship().getCollisionBox())) {
            this.game.getSpaceship().addLives(-1);
            ge.setAlive(false);
            this.game.getSpaceship()
                .setTexture("Textures/Spaceship/SpaceshipHit.png");
            repaint();
            Thread.sleep(300);
            this.game.getSpaceship()
                .setTexture("Textures/Spaceship/Spaceship.png");
            this.game.getSpaceship()
                .setPosition(new Position(499 / 2, 448 - 32));
          } //BLOCK: KOLIZE PROJECTILE A SPACESHIP

          //BLOCK: KOLIZE PROJECTILE A ENEMY
          if (((Projectile) ge).playerProjectile() && ge2 instanceof Enemy
              && ge.getCollisionBox().intersects(ge2.getCollisionBox())) {
            ge.setAlive(false);
            ge2.setAlive(false);
            this.game.addScore(((Enemy) ge2).getReward());
            for (GameEntity ge3 : this.game.getEntities()) {
              if (ge3 instanceof Enemy) {
                ge3.setStep(ge3.getStep() * 1.05);
              }
            }
          } //BLOCK: KOLIZE PROJECTILE A ENEMY

          //BLOCK: KOLIZE PROJECTILE A PROJECTILE
          if (ge2 instanceof Projectile && ge != ge2
              && ge.getCollisionBox().intersects(ge2.getCollisionBox())) {
            ge.setAlive(false);
            ge2.setAlive(false);
          } //BLOCK: KOLIZE PROJECTILE A PROJECTILE

          //BLOCK: KOLIZE PROJECTILE A SHELTER
          if (ge2 instanceof Shelter
              && ge.getCollisionBox().intersects(ge2.getCollisionBox())) {
            ge.setAlive(false);
            ((Shelter) ge2).addHealth(-1);
          } //BLOCK: KOLIZE PROJECTILE A SHELTER

        }
      }
    }

  }

  private void gameOver(boolean won) {

    this.game.setGameWon(won);
    this.game.setGameOver(true);
    this.game.setStarted(false);
  }

  private void checkGameOver() {
    if (this.game.getSpaceship().getLives() == 0) {
      gameOver(false);
    }

    if (countEnemies() == 0) {
      gameOver(true);
    }

  }

  private void checkShoot() {
    if (this.game.getKeyboard().getKey(38)
        && this.game.getSpaceship().getCooldown() == 0) {
      this.game.getEntities()
          .add(new Projectile(
              new Position(this.game.getSpaceship().getPosition().getX() + 10,
                  this.game.getSpaceship().getPosition().getY()),
              false));
      this.game.getSpaceship().setCooldown(20);
      this.game.addScore(-1);
    }
  }

  private void setEnemiesDirectionOfMoving() {
    boolean edge = false;
    for (GameEntity ge : this.game.getEntities()) {
      if (ge instanceof Enemy) {
        edge = edge || ge.onEdge();
      }
    }

    if (edge) {
      for (GameEntity ge : this.game.getEntities()) {
        if (ge instanceof Enemy) {
          ((Enemy) ge).moveDown();
          if (((Enemy) ge).getMoving() == "Right") {
            ((Enemy) ge).setMoving("Left");
          } else {
            ((Enemy) ge).setMoving("Right");
          }
        }
      }
    }
  }

  private void generateEnemyProjectiles() {

    List<GameEntity> tmp = new LinkedList<>();
    for (GameEntity ge : this.game.getEntities()) {
      if (ge instanceof Enemy && new Random().nextInt(2000) == 1024
          && ((Enemy) ge).getCooldown() == 0) {
        tmp.add(new Projectile(
            new Position(ge.getPosition().getX(), ge.getPosition().getY()),
            true));
        ((Enemy) ge).setCooldown(25);
      }
    }
    this.game.getEntities().addAll(tmp);
  }

  @Override
  public void run() {
    while (true) {
      try {
        if (!this.game.isStarted() && !this.game.isGameOver()) {
          Boolean key = false;
          for (Boolean k : this.game.getKeyboard().getKeys()) {
            key = key || k;
          }

          this.game.setStarted(key);

        } else if (this.game.isStarted() && !this.game.isGameOver()) {

          removeDead();
          checkGameOver();
          checkShoot();
          setEnemiesDirectionOfMoving();
          generateEnemyProjectiles();
          checkCollisions();

          for (GameEntity ge : this.game.getEntities()) {
            ge.action();
          }
        }

        else if (this.game.isGameOver()) {
          if (this.game.getKeyboard().getKey(KeyEvent.VK_SPACE)) {
            this.game.reload();
            this.game.setStarted(true);
          }
        }

        this.repaint();

        Thread.sleep(20);

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
