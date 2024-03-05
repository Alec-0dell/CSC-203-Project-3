import java.util.List;

import processing.core.PImage;

public abstract class Actionable extends Entity{

    private double animationPeriod;
    private double behaviorPeriod;

    public Actionable(String id, Point position, List<PImage> images, int imageIndex, double animationPeriod, double behaviorPeriod) {
        super(id, position, images, imageIndex);
        this.animationPeriod = animationPeriod;
        this.behaviorPeriod = behaviorPeriod;
    }

    public double getAnimationPeriod() {
        return animationPeriod;
    }

    public double getBehaviorPeriod() {
        return behaviorPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        this.scheduleAnimation(scheduler, world, imageLibrary);
        this.scheduleBehavior(scheduler, world, imageLibrary);
    }

    public void scheduleAnimation(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Animation(this, 0), this.getAnimationPeriod());
    }

    /** Schedules a single behavior update for the entity. */
    public void scheduleBehavior(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Behavior(this, world, imageLibrary), this.getBehaviorPeriod());
    }



}
