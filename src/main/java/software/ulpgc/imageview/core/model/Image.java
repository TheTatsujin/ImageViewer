package software.ulpgc.imageview.core.model;

import java.awt.image.BufferedImage;

public interface Image{
    static Image Null() {
        return new Image(){
            @Override
            public String name() {
                return "";
            }

            @Override
            public BufferedImage content() {
                return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public Image next() {
                return this;
            }

            @Override
            public Image previous() {
                return this;
            }
        };
    }

    String name();
    BufferedImage content();
    Image next();
    Image previous();
}
