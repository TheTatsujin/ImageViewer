package software.ulpgc.imageview.core.io;

import software.ulpgc.imageview.core.model.Image;
import software.ulpgc.imageview.core.model.ImageFormat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class FileImageLoader implements ImageLoader {
    private final File[] files;

    public FileImageLoader(File directory) {
        this.files = directory.listFiles(isImage());
    }

    private static FilenameFilter isImage(){
        return (dir, name) ->
                Arrays.stream(ImageFormat.values())
                        .map(ImageFormat::extension)
                        .anyMatch(name::endsWith);
    }

    @Override
    public Image load() {
        return getImage(0);
    }

    private BufferedImage getImageContent(File file) {
        try {
            return ImageIO.read(file);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int module(int a, int N){
        return ((a % N) + N) % N;
    }

    private Image getImage(int index) {
        BufferedImage image = getImageContent(files[index]);
        return new Image() {
            @Override
            public String name() {
                return files[index].getName();
            }

            @Override
            public BufferedImage content() {
                return image;
            }

            @Override
            public Image next() {
                return getImage(module(index + 1, files.length));
            }

            @Override
            public Image previous() {
                return getImage(module(index - 1, files.length));
            }
        };
    }

}
