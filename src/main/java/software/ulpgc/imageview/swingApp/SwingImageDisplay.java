package software.ulpgc.imageview.swingApp;

import software.ulpgc.imageview.core.model.ImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private final List<Paint> paintQueue = new ArrayList<>();
    private Shift shift = Shift.Null;
    private Release release = Release.Null;
    private int initialOffset;

    public SwingImageDisplay() {
        this.addMouseListener(mouseListener());
        this.addMouseMotionListener(mouseMotionListener());
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        Paint paint = paintQueue.getFirst();
        g.drawImage(paint.content, paint.offsetWidth + paint.shiftOffset, paint.offsetHeight, null);
    }
    @Override
    public void on(Shift shift) {
        this.shift = shift != null ? shift : Shift.Null;
    }

    @Override
    public void on(Release released) {
        this.release = released != null ? released : Release.Null;
    }



    @Override
    public void clear() {
        this.paintQueue.clear();
    }

    @Override
    public void paint(int offsetWidth, int offsetHeight, int width, int height, int shiftOffset, BufferedImage content) {
        this.paintQueue.add(new Paint(offsetWidth, offsetHeight, width, height, shiftOffset,  content));
        repaint();
    }

    @Override
    public int width() {
        return getWidth();
    }

    @Override
    public int height() {
        return getHeight();
    }

    private MouseListener mouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                initialOffset = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                release.offset(e.getX() - initialOffset);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) { }
        };
    }

    private MouseMotionListener mouseMotionListener() {
        return new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                shift.offset(e.getX() - initialOffset);
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        };
    }


    private record Paint(int offsetWidth, int offsetHeight, int width, int height, int shiftOffset, BufferedImage content) {}

}
