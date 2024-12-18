package software.ulpgc.imageview.swingApp;

import software.ulpgc.imageview.core.NextImageCommand;
import software.ulpgc.imageview.core.PreviousImageCommand;
import software.ulpgc.imageview.core.io.FileImageLoader;
import software.ulpgc.imageview.core.model.Image;

import java.io.File;

public class Main {

    public static final String root = "/home/lily/Pictures/";
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        Image image = new FileImageLoader(new File(root)).load();
        frame.imageDisplay().show(image);
        frame.put("<", new PreviousImageCommand(frame.imageDisplay()));
        frame.put(">", new NextImageCommand(frame.imageDisplay()));
        frame.setVisible(true);
    }

}
