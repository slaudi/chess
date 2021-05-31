package chess.gui;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImageHandler {

    private static ImageHandler instance;

    private final Map<String, Image> imgs = new HashMap<>();

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

    public Image getImage (String key) {
        if (imgs.get(key) == null) {
            loadImage(key);
        }
        return imgs.get(key);
    }

}
