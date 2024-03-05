import java.util.List;

import processing.core.PImage;

public class Tree extends Resourceable {

    public static final String TREE_KEY = "tree";

    public static final double TREE_RANDOM_BEHAVIOR_PERIOD_MIN = 0.01;
    public static final double TREE_RANDOM_BEHAVIOR_PERIOD_MAX = 0.10;
    public static final double TREE_RANDOM_ANIMATION_PERIOD_MIN = 0.1;
    public static final double TREE_RANDOM_ANIMATION_PERIOD_MAX = 1.0;
    public static final int TREE_RANDOM_HEALTH_MIN = 1;
    public static final int TREE_RANDOM_HEALTH_MAX = 3;


    public Tree(String id, Point position, List<PImage> images, double animationPeriod,
            double behaviorPeriod, int health) {
        super(id, position, images, 0, animationPeriod, behaviorPeriod, 0, 0, health);
    }

    public Tree(){
        super("", null, null, 0, 0, 0, 0, 0, 0);
    }

    @Override
    public void updateImage() {
        this.setImageIndex(this.getImageIndex() + 1);
    }

    public boolean transformTree(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (this.getHealth() <= 0) {
            Stump stump = new Stump(Stump.STUMP_KEY + "_" + this.getId(), this.getPosition(), imageLibrary.get(Stump.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }

    public void executeActivity(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        if (!transformTree(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

}
