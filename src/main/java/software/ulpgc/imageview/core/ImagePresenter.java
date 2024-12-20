package software.ulpgc.imageview.core;

import software.ulpgc.imageview.core.model.Image;
import software.ulpgc.imageview.core.model.ImageDisplay;

import java.awt.image.BufferedImage;

public class ImagePresenter {
    private final ImageDisplay imageDisplay;
    private Image image;

    public ImagePresenter(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
        this.imageDisplay.on((ImageDisplay.Shift) this::shiftEvent);
        this.imageDisplay.on((ImageDisplay.Release) this::releaseEvent);
    }

    private void releaseEvent(int offset){
        if (Math.abs(offset) >= imageDisplay.width() / 2)
            image = offset > 0 ? image.previous() : image.next();
        updateDisplay();
    }

    private void shiftEvent(int offset){
        imageDisplay.clear();
        imageDisplay.paint(image.content().getWidth(), image.content().getHeight(), offset, image.content());
        if (offset > 0){
            imageDisplay.paint(image.previous().content().getWidth(), image.previous().content().getHeight(), offset, image.previous().content());
        }
        else
            imageDisplay.paint(image.next().content().getWidth(), image.next().content().getHeight(), offset, image.next().content());
    }

    private void updateDisplay() {
        imageDisplay.clear();
        imageDisplay.paint(image.content().getWidth(), image.content().getHeight(), 0, image.content());
    }

    public void show(Image image) {
        this.image = image;
        updateDisplay();
    }


}
