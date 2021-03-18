package schach;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        boolean cli = Arrays.asList(args).contains("--no-gui");
        if (cli) {
          Cli.main(args);
        } else {
          Gui.main(args);
        }
    }
}
