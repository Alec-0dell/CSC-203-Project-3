import java.util.List;

import processing.core.PImage;

public class House extends Entity{

    public static final String HOUSE_KEY = "house";

    public House(String id, Point position, List<PImage> images) {
        super(id, position, images, 0);
    }

    @Override
    public void updateImage() {
        throw new UnsupportedOperationException("Unimplemented method 'updateImage'");
    }

    @Override
    public void executeActivity(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        throw new UnsupportedOperationException("Unimplemented method 'executeActivity'");
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
    }
    
}
