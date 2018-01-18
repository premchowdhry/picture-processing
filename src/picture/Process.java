package picture;

public class Process {

  private static Picture picture;

  public static void setPicture(String load) {
    picture = Utils.loadPicture(load);
  }

  public static void save(String destination) {
    Utils.savePicture(picture, destination);
  }

  public static void invert() {
    int height = picture.getHeight();
    int width  = picture.getWidth();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        Color color = picture.getPixel(x, y);
        color.setRed(255 - color.getRed());
        color.setBlue(255 - color.getBlue());
        color.setGreen(255 - color.getGreen());

        picture.setPixel(x, y, color);
      }
    }
  }

  public static void grayscale() {
    int height = picture.getHeight();
    int width  = picture.getWidth();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        Color color = picture.getPixel(x, y);
        int avg = (color.getBlue() + color.getGreen() + color.getRed()) / 3;
        color.setBlue(avg);
        color.setGreen(avg);
        color.setRed(avg);

        picture.setPixel(x, y, color);
      }
    }
  }

  public static void rotate(int angle) {
    assert (angle == 90 || angle == 180 || angle == 270) : "Invalid angle to rotate image";

    if (angle == 90) {
      int height = picture.getHeight();
      int width = picture.getWidth();
      Picture out = Utils.createPicture(height, width);

      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          Color color = picture.getPixel(x, y);
          out.setPixel((height - 1- y), x, color);
        }
      }

      picture = out;
    }

    if (angle == 180) {
      rotate(90);
      rotate(90);
    }

    if (angle == 270) {
      rotate(180);
      rotate(90);
    }

  }

  public static void flip(String d) {
    assert (d.equals("V") || d.equals("H")) : "Invalid orientation to flip image";

    int height = picture.getHeight();
    int width = picture.getWidth();

    Picture out = Utils.createPicture(width, height);

    if (d.equals("V")) {
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          Color color = picture.getPixel(x, y);
          out.setPixel(x, (height - y - 1), color);
        }
      }
    }

    if (d.equals("H")) {
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          Color color = picture.getPixel(x, y);
          out.setPixel((width - x - 1), y, color);
        }
      }
    }

    picture = out;

  }

  public static void blend(Picture[] pictures) {

    int width = getSmallestWidth(pictures);
    int height = getSmallestHeight(pictures);

    Picture out = Utils.createPicture(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        int avgred = 0, avggreen = 0, avgblue = 0;

        // Loop to find RGB values of all the input pictures
        for (int i = 0; i < pictures.length; i++) {
          avgred += pictures[i].getPixel(x, y).getRed();
          avggreen += pictures[i].getPixel(x, y).getGreen();
          avgblue += pictures[i].getPixel(x, y).getBlue();
        }

        avgred /= pictures.length;
        avggreen /= pictures.length;
        avgblue /= pictures.length;

        Color avgcolor = new Color(avgred, avggreen, avgblue);
        out.setPixel(x, y , avgcolor);
      }
    }

    picture = out;
  }

  public static void blur() {
    int height = picture.getHeight();
    int width = picture.getWidth();

    Picture out = Utils.createPicture(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
          Color color = picture.getPixel(x, y);
          out.setPixel(x, y, color);
        }
      }
    }

    for (int y = 1; y < height - 1; y++) {
      for (int x = 1; x < width - 1; x++) {

        int avgred = 0, avggreen = 0, avgblue = 0;

        for (int i = x - 1; i < x + 2; i ++) {
          for (int j = y - 1; j < y + 2; j++) {
            avgred += picture.getPixel(i, j).getRed();
            avggreen += picture.getPixel(i, j).getGreen();
            avgblue += picture.getPixel(i, j).getBlue();
          }
        }

        Color avgcolor = new Color (avgred / 9, avggreen / 9, avgblue / 9);
        out.setPixel(x, y, avgcolor);
      }
    }

    picture = out;
  }

  public static void mosaic(int n, Picture[] pictures) {
    int width = getSmallestWidth(pictures);
    int height = getSmallestHeight(pictures);

    // Trimming each dimension to be a multiple of the tile size
    height -= (height % n);
    width -= (width % n);

    Picture out = Utils.createPicture(width, height);
    int start = 0;

    for (int y = 0; y < height; y += n) {
      int k = start;
      for (int x = 0; x < width; x += n) {

        for (int i = 0; i < n; i++) {
          for (int j = 0; j < n; j++) {
            Color color = pictures[k % pictures.length].getPixel(x + i, y + j);
            out.setPixel(x + i, y + j, color);
          }
        }
        k++;

      }
      start++;
    }

    picture = out;
  }

  private static int getSmallestWidth(Picture[] pictures) {
    int minwidth = pictures[0].getWidth();

    for (int i = 1; i < pictures.length; i++) {
      int width = pictures[i].getWidth();
      if (width < minwidth) {
        minwidth = width;
      }
    }
    return minwidth;
  }

  private static int getSmallestHeight(Picture[] pictures) {
    int minheight = pictures[0].getHeight();

    for (int i = 1; i < pictures.length; i++) {
      int height = pictures[i].getHeight();
      if (height < minheight) {
        minheight = height;
      }
    }
    return minheight;
  }


}
