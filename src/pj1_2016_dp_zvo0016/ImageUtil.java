/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2016_dp_zvo0016;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * @author Richard Zvonek
 * @version 1.0
 */
public class ImageUtil {

  private static HashMap<String, Image> cache = new HashMap<>();

  public static Image loadImage(String path) {
    Image image = null;

    if (cache.containsKey(path)) {
      return cache.get(path);
    }

    try {
      image = ImageIO.read(new File(path));

      if (!cache.containsKey(path)) {
        cache.put(path, image);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return image;
  }

}
