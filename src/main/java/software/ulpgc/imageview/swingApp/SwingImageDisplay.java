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
        paintContent(g, paintQueue.getFirst());
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
    public void paint(int width, int height, int shiftOffset, BufferedImage content) {
        this.paintQueue.add(new Paint(0, 0, width, height, shiftOffset,  content));
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

    private void paintContent(Graphics g, Paint paint) {
        Dimension contentSize = fitToResolution(paint.width, paint.height());
        Dimension startOffset = offset(contentSize);
        g.drawImage(paint.content, startOffset.width + paint.shiftOffset, startOffset.height, contentSize.width, contentSize.height, null);
    }


    private Dimension fitToResolution(int contentWidth, int contentHeight) {
        Dimension contentSize = new Dimension(contentWidth, contentHeight);
        return contentSize.scale(Math.min(
                (double) getWidth() / contentWidth,
                (double) getHeight() / contentHeight
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

        @Override
        public String toString() {
            return "( " + width + ", " + height + " )";
        }
    }


    private record Paint(int offsetWidth, int offsetHeight, int width, int height, int shiftOffset, BufferedImage content) {}

}
