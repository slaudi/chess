package chess.gui;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Helper-Class for Image-Handling
 */
public class ImageHandler {

    private static ImageHandler instance;

    private final Map<String, Image> imgs = new HashMap<>();

    /**
     * Generates a new ImageHandler instance if none exists yet.
     *
     * @return ImageHandler The instance of the ImageHandler.
     */
    public static ImageHandler getInstance() {
        if (instance == null) {
            instance = new ImageHandler();
        }
        return instance;
    }

    private void loadImage(String name) {
        Image image = new Image(Objects.requireNonNull(ImageHandler.class.getResource(name + ".png")).toExternalForm(), true);
        imgs.put(name, image);
    }

    /**
     * Gets an already loaded image from a HashMap. If it hasn't been loaded yet, it wil load it and inserts it into
     * the HashMap.
     * @param key A String of the name of the requested image.
     * @return Image The requested image.
     */
    public Image getImage (String key) {
        if (imgs.get(key) == null) {
            loadImage(key);
        }
        return imgs.get(key);
    }

}
