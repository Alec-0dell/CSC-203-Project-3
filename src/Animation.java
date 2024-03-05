public class Animation extends Action {

    private int repeatCount;

    public Animation(Entity entity, int repeatCount) {
        super(entity);
        this.repeatCount = repeatCount;
    }

    public void execute(EventScheduler scheduler) {
        Entity entity = super.getEntity();
        super.getEntity().updateImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(entity, new Animation(entity, Math.max(this.repeatCount - 1, 0)), ((Actionable) entity).getAnimationPeriod());
        }
    }
    
}
