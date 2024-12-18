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
        g.drawImage(image.content(), 0, 0, getWidth(), getHeight(), null);
    }
}
