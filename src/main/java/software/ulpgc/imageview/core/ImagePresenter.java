package software.ulpgc.imageview.core;

import software.ulpgc.imageview.core.model.Image;
import software.ulpgc.imageview.core.model.ImageDisplay;

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
        paintImage(image, offset);
        if (offset > 0){
            paintImage(image.previous(), offset);
        }
        else
            paintImage(image.next(), offset);
    }

    private void updateDisplay() {
        imageDisplay.clear();
        paintImage(image, 0);
    }

    public void show(Image image) {
        this.image = image;
        updateDisplay();
    }

    public void paintImage(Image image, int offset) {
        Dimension imageSize = fitToResolution(image);
        Dimension startOffset = offset(imageSize);
        imageDisplay.paint(startOffset.width, startOffset.height, imageSize.width, imageSize.height, offset, image.content());

    }


    private record Dimension(int width, int height) {
        public Dimension scale(double factor) {
            return new Dimension((int) (width * factor), (int) (height * factor));
        }
    }


    private Dimension fitToResolution(Image image) {
        Dimension imageSize = new Dimension(image.content().getWidth(), image.content().getHeight());
        return imageSize.scale(Math.min(
                (double) imageDisplay.width() / imageSize.width(),
                (double) imageDisplay.width() / imageSize.height()
        ));
    }

    private Dimension offset(Dimension imageSize) {
        return new Dimension(
                (imageDisplay.width() - imageSize.width) / 2,
                (imageDisplay.height() - imageSize.height) / 2
        );
    }

}
