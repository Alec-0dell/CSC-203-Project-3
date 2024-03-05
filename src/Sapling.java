import java.util.List;

import processing.core.PImage;

public class Sapling extends Resourceable{

    public static final String SAPLING_KEY = "sapling";

    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final double SAPLING_BEHAVIOR_PERIOD = 2.0;
    public static final double SAPLING_ANIMATION_PERIOD = 0.01;

    public Sapling(String id, Point position, List<PImage> images) {
        super(id, position, images, 0, SAPLING_ANIMATION_PERIOD, SAPLING_BEHAVIOR_PERIOD, 0, 0, 0);
    }

    public boolean transformSapling(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (this.getHealth() <= 0) {
            Stump stump = new Stump(Stump.STUMP_KEY + "_" + this.getId(), this.getPosition(), imageLibrary.get(Stump.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        } else if (this.getHealth() >= SAPLING_HEALTH_LIMIT) {
            Tree tree = new Tree(
                    Tree.TREE_KEY + "_" + this.getId(),
                    this.getPosition(),
                    imageLibrary.get(Tree.TREE_KEY),
                    NumberUtil.getRandomDouble(Tree.TREE_RANDOM_ANIMATION_PERIOD_MIN, Tree.TREE_RANDOM_ANIMATION_PERIOD_MAX), NumberUtil.getRandomDouble(Tree.TREE_RANDOM_BEHAVIOR_PERIOD_MIN, Tree.TREE_RANDOM_BEHAVIOR_PERIOD_MAX),
                    NumberUtil.getRandomInt(Tree.TREE_RANDOM_HEALTH_MIN, Tree.TREE_RANDOM_HEALTH_MAX)
            );

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageLibrary);

            return true;
        }

        return false;
    }

    public void executeActivity(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        this.setHealth(this.getHealth() + 1);
        if (!transformSapling(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    @Override
    public void updateImage() {
        if (this.getHealth() <= 0) {
            this.setImageIndex(0);
        } else if (this.getHealth() < SAPLING_HEALTH_LIMIT) {
            this.setImageIndex(this.getImages().size() * this.getHealth()/ SAPLING_HEALTH_LIMIT);
        } else {
            this.setImageIndex(this.getImages().size() - 1);
        }
    }
    
}
