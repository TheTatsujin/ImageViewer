package software.ulpgc.imageview.core.model;

public enum ImageFormat {
    jpg(".jpg"),
    png(".png"),
    jpeg(".jpeg");

    private final String extension;
    ImageFormat(String extension) {
        this.extension = extension;
    }

    public String extension() {
        return extension;
    }
}
