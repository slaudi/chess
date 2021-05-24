/**
 * The main module of the chess application.
 */
module chess {
    requires javafx.controls;
    requires transitive javafx.graphics;

    opens chess.pieces;

    exports chess.gui;
}
