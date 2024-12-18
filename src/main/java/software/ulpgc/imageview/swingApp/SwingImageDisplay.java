package software.ulpgc.imageview.swingApp;

import software.ulpgc.imageview.core.model.Image;
import software.ulpgc.imageview.core.model.ImageDisplay;

import javax.swing.*;
import java.awt.*;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private Image image;

    @Override
    public void show(Image image) {
        this.image = image;
        this.repaint();
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        Dimension scaledImage = fitToResolution(image);
        g.drawImage(
                image.content(),
                offset(scaledImage).width,
                offset(scaledImage).height,
                scaledImage.width,
                scaledImage.height,
                null
        );
    }

    private Dimension fitToResolution(Image image) {
        Dimension imageSize = new Dimension(image.content().getWidth(), image.content().getHeight());
        return imageSize.scale(Math.min(
                (double) this.getWidth() / imageSize.width(),
                (double) this.getHeight() / imageSize.height()
        ));
    }

    private Dimension offset(Dimension imageSize) {
        return new Dimension(
                (getWidth() - imageSize.width) / 2,
                (getHeight() - imageSize.height) / 2
        );
    }


    private record Dimension(int width, int height) {
        public Dimension scale(double factor) {
            return new Dimension((int) (width * factor), (int) (height * factor));
        }
    }

}
