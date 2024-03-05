public interface Moveable {
    public boolean move(World world, Entity entity1, EventScheduler eventScheduler);
    public Point nextPosition(World world, Point point);
}
