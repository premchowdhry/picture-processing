package picture;

public class Main {

  public static void main(String[] args) {
    if (args[0].equals("invert")) {
      Process process = new Process();
      process.setPicture(args[1]);
      process.invert();
      process.save(args[2]);
    }

    else if (args[0].equals("grayscale")) {
      Process process = new Process();
      process.setPicture(args[1]);
      process.grayscale();
      process.save(args[2]);
    }

    else if (args[0].equals("rotate")) {
      Process process = new Process();
      process.setPicture(args[2]);
      process.rotate(Integer.parseInt(args[1]));
      process.save(args[3]);
    }

    else if (args[0].equals("flip")) {
      Process process = new Process();
      process.setPicture(args[2]);
      process.flip(args[1]);
      process.save(args[3]);
    }

    else if (args[0].equals("blend")) {
      Process process = new Process();
      Picture[] pictures = new Picture[args.length - 2];
      for (int i = 1; i < args.length - 1; i++) {
        pictures[i - 1] = Utils.loadPicture(args[i]);
      }
      process.blend(pictures);
      process.save(args[args.length - 1]);
    }

    else if (args[0].equals("blur")) {
      Process process = new Process();
      process.setPicture(args[1]);
      process.blur();
      process.save(args[2]);
    }

    else if (args[0].equals("mosaic")) {
      Process process = new Process();
      Picture[] pictures = new Picture[args.length - 3];
      for (int i = 2; i < args.length - 1; i++) {
        pictures[i - 2] = Utils.loadPicture(args[i]);
      }
      process.mosaic(Integer.parseInt(args[1]), pictures);
      process.save(args[args.length - 1]);
    }
  }
}
