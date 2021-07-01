/**
 * The main module of the chess application.
 */
module chess {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;
    requires ChessProtocol;

    opens chess.pieces;
    opens chess.gui;

    exports chess;
}
