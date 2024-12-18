package software.ulpgc.imageview.core;

import software.ulpgc.imageview.core.model.ImageDisplay;

public class NextImageCommand implements Command {
    private final ImageDisplay imageDisplay;

    public NextImageCommand(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }

    @Override
    public void execute() {
        imageDisplay.show(imageDisplay.getImage().next());
    }
}
