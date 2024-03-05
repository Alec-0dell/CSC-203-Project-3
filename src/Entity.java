import java.util.List;

import processing.core.PImage;

public abstract class Entity {

    public static final int ENTITY_PROPERTY_KEY_INDEX = 0;
    public static final int ENTITY_PROPERTY_ID_INDEX = 1;
    public static final int ENTITY_PROPERTY_POSITION_X_INDEX = 2;
    public static final int ENTITY_PROPERTY_POSITION_Y_INDEX = 3;
    public static final int ENTITY_PROPERTY_COLUMN_COUNT = 4;

    // Constant save file column positions for properties corresponding to speicific entity types.
    // Do not use these values directly in any constructors or 'create' methods.
    public static final int DUDE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int DUDE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 1;
    public static final int DUDE_PARSE_PROPERTY_RESOURCE_LIMIT_INDEX = 2;
    public static final int DUDE_PARSE_PROPERTY_COUNT = 3;

    public static final int FAIRY_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int FAIRY_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 1;
    public static final int FAIRY_PARSE_PROPERTY_COUNT = 2;

    public static final int HOUSE_PARSE_PROPERTY_COUNT = 0;

    public static final int MUSHROOM_PARSE_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int MUSHROOM_PARSE_PROPERTY_COUNT = 1;

    public static final int STUMP_PARSE_PROPERTY_COUNT = 0;

    public static final int SAPLING_PARSE_PROPERTY_COUNT = 0;

    public static final int TREE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int TREE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 1;
    public static final int TREE_PARSE_PROPERTY_HEALTH_INDEX = 2;
    public static final int TREE_PARSE_PROPERTY_COUNT = 3;

    // Constant limits and default values for specific entity types.
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final double SAPLING_BEHAVIOR_PERIOD = 2.0;
    public static final double SAPLING_ANIMATION_PERIOD = 0.01; // Very small to react to health changes

    public static final int WATER_PARSE_PROPERTY_COUNT = 0;

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Entity(String id, Point position, List<PImage> images, int imageIndex) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = imageIndex;
    }

    public String getId() {
        return id;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public List<PImage> getImages() {
        return images;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String log(){
        if (id.isEmpty()) {
            return null;
        } else {
            return String.format("%s %d %d %d", id, position.x, position.y, imageIndex);
        }
    }

    public abstract void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary);
    public abstract void updateImage();
    public abstract void executeActivity(World world, ImageLibrary imageLibrary, EventScheduler scheduler);


    
}
