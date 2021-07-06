package chess;

import chess.cli.Cli;
import chess.cli.Simple;
import chess.gui.Gui;
import chess.network.NetworkClient;
import chess.network.NetworkServer;

import java.io.IOException;
import java.util.Arrays;

/**
 * The common starting point of the GUI and the CLI. Depending on the given command line arguments either
 * the GUI or the CLI interface are initialized.
 */
public class Main {
    /**
     * The external entry point of the application.
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) throws IOException {
        boolean cli = Arrays.asList(args).contains("--no-gui");
        boolean simple = Arrays.asList(args).contains("--simple");
        boolean networkServer = Arrays.asList(args).contains("--network-server");
        boolean networkClient = Arrays.asList(args).contains("--network-client");
        if (cli && !simple) {
            Cli.main(args);
        } else if (simple) {
            Simple.main(args);
        /*} else if (networkServer) {
            NetworkServer.main(args);
        } else if (networkClient) {
            NetworkClient.main(args);*/
        } else {
            Gui.main(args);
        }
    }
}
