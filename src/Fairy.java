import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import processing.core.PImage;

public class Fairy extends Actionable implements Moveable{

    public static final String FAIRY_KEY = "fairy";

    public Fairy(String id, Point position, List<PImage> images, double animationPeriod,
            double behaviorPeriod) {
        super(id, position, images, 0, animationPeriod, behaviorPeriod);
    }

    public boolean move(World world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacentTo(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());
            if (!getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    /** Determines a Fairy's next position when moving. */
    public Point nextPosition(World world, Point destination) {
        PathingStrategy pathingStrategy = new AStarPathingStrategy(); 
        Point currentPosition = this.getPosition();
        Predicate<Point> canPassThrough = point -> world.inBounds(point) && !world.isOccupied(point) && world.inBounds(point);
        BiPredicate<Point, Point> withinReach = (p, q) -> p.adjacentTo(q);
        Function<Point, Stream<Point>> potentialNeighbors = p -> {
            Stream<Point> ret = Stream.of(
                new Point(p.x + 1, p.y),
                new Point(p.x - 1, p.y),
                new Point(p.x, p.y + 1),
                new Point(p.x, p.y - 1)
            );
            return ret;
        };
        List<Point> path = pathingStrategy.computePath(currentPosition, destination, canPassThrough, withinReach, potentialNeighbors);


        if (path.isEmpty()) {
            return this.getPosition();
        } else {
            return path.get(0);
        }
    }

    public void executeActivity(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(getPosition(), new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (move(world, fairyTarget.get(), scheduler)) {
                Sapling sapling = new Sapling(Sapling.SAPLING_KEY + "_" + fairyTarget.get().getId(), tgtPos, imageLibrary.get(Sapling.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageLibrary);
            }
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }

    

    public void updateImage() {
        this.setImageIndex(this.getImageIndex() + 1);
    }

}
