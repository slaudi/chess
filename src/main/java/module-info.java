/**
 * The main module of the chess application.
 */
module chess {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires java.desktop;

    opens chess.pieces;
    opens chess.gui;

    exports chess;
}
