/**
 * The main module of the chess application.
 */
module schach {
    requires javafx.controls;
    requires transitive javafx.graphics;
    
    exports schach;
    opens schach;
}
