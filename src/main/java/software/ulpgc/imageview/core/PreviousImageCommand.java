package software.ulpgc.imageview.core;

import software.ulpgc.imageview.core.model.ImageDisplay;

public class PreviousImageCommand implements Command {
    private final ImageDisplay imageDisplay;

    public PreviousImageCommand(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }

    @Override
    public void execute() {
        imageDisplay.show(imageDisplay.getImage().previous());
    }
}
