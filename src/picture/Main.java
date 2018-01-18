package picture;

public class Main {

  public static void main(String[] args) {
    assert (args.length > 2) : "Invalid argument input";

    switch(args[0]) {

      case "invert" :
        Process.setPicture(args[1]);
        Process.invert();
        break;

      case "grayscale" :
        Process.setPicture(args[1]);
        Process.grayscale();
        break;

      case "rotate" :
        int angle = Integer.parseInt(args[1]);
        Process.setPicture(args[2]);
        Process.rotate(angle);
        break;

      case "flip" :
        Process.setPicture(args[2]);
        Process.flip(args[1]);
        break;

      case "blend" :
        Picture[] picsBlend = new Picture[args.length - 2];
        for (int i = 1; i < args.length - 1; i++) {
          picsBlend[i - 1] = Utils.loadPicture(args[i]);
        }
        Process.blend(picsBlend);
        break;

      case "blur" :
        Process.setPicture(args[1]);
        Process.blur();
        break;

      case "mosaic" :
        Picture[] picsMosaic = new Picture[args.length - 3];
        int tileSize = Integer.parseInt(args[1]);
        for (int i = 2; i < args.length - 1; i++) {
          picsMosaic[i - 2] = Utils.loadPicture(args[i]);
        }
        Process.mosaic(tileSize, picsMosaic);
        break;

      default :
        assert (true) : "Invalid command";
        break;
    }

    Process.save(args[args.length - 1]);

  }
}
