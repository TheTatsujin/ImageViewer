package software.ulpgc.imageview.swingApp;

import software.ulpgc.imageview.core.ImagePresenter;
import software.ulpgc.imageview.core.io.FileImageLoader;
import software.ulpgc.imageview.core.model.Image;

import java.io.File;

public class Main {
    public static final String root = "/home/lily/Pictures/";
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        Image image = new FileImageLoader(new File(root)).load();

        ImagePresenter presenter = new ImagePresenter(frame.imageDisplay());
        presenter.show(image);
        frame.setVisible(true);
    }

}
