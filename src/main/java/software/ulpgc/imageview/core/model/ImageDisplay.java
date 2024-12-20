package software.ulpgc.imageview.core.model;

import java.awt.image.BufferedImage;

public interface ImageDisplay {
    void paint(int width, int height, int shiftOffset, BufferedImage content);
    void clear();
    void on(Shift shift);
    void on(Release release);

    int width();
    int height();
    interface Shift {
        Shift Null = _ -> {};
        void offset(int offset);
    }
    interface Release {
        Release Null = _ -> {};
        void offset(int offset);
    }
}
