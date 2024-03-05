import java.util.List;

import processing.core.PImage;

public abstract class Resourceable extends Actionable {

    private int resourceLimit;
    private int resourceCount;
    private int health;

    public Resourceable(String id, Point position, List<PImage> images, int imageIndex, double animationPeriod, double behaviorPeriod, int resourceLimit, int resourceCount, int health) {
        super(id, position, images, imageIndex, animationPeriod, behaviorPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.health = health;
    }

    
    public int getResourceCount() {
        return resourceCount;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }
    
}
