import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import processing.core.PImage;

public class Dude extends Resourceable implements Moveable{

    public static final String DUDE_KEY = "dude";

    public Dude(String id, Point position, List<PImage> images, double animationPeriod,
            double behaviorPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, 0, animationPeriod, behaviorPeriod, resourceLimit, resourceCount, 0);
    }


    public boolean transformDude(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (this.getResourceCount() < this.getResourceLimit()) {
            this.setResourceCount(this.getResourceCount() + 1);
            if (this.getResourceCount() == this.getResourceLimit()) {
                Dude dude = new Dude(this.getId(), this.getPosition(), imageLibrary.get(DUDE_KEY + "_carry"), this.getAnimationPeriod(), this.getBehaviorPeriod(), this.getResourceCount(), this.getResourceLimit());

                world.removeEntity(scheduler, this);

                world.addEntity(dude);
                dude.scheduleActions(scheduler, world, imageLibrary);

                return true;
            }
        } else {
            Dude dude = new Dude(this.getId(), this.getPosition(), imageLibrary.get(DUDE_KEY), this.getAnimationPeriod(), this.getBehaviorPeriod(), 0, this.getResourceLimit());

            world.removeEntity(scheduler, this);

            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageLibrary);

            return true;
        }

        return false;
    }


    public boolean move(World world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacentTo(target.getPosition())) {
            if (target instanceof Tree || target instanceof Sapling) {
                ((Resourceable)target).setHealth(((Resourceable)target).getHealth() -1);
            }
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!target.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }

            return false;
        }
    }

    public Point nextPosition(World world, Point destination) {
        PathingStrategy pathingStrategy = new AStarPathingStrategy(); 
        Point currentPosition = this.getPosition();
        Predicate<Point> canPassThrough = point -> world.inBounds(point) && (!world.isOccupied(point) || world.getOccupant(point).get().getClass() == Stump.class);
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
        Optional<Entity> dudeTarget = findDudeTarget(world);
        if (dudeTarget.isEmpty() || !move(world, dudeTarget.get(), scheduler) || !transformDude(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    public Optional<Entity> findDudeTarget(World world) {
        List<Class<?>> potentialTargets;

        if (this.getResourceCount() == this.getResourceLimit()) {
            potentialTargets = List.of(House.class);
        } else {
            potentialTargets = List.of(Tree.class, Sapling.class);
        }

        return world.findNearest(this.getPosition(), potentialTargets);
    }

    public void updateImage() {
        this.setImageIndex(this.getImageIndex() + 1);
    }




    
}
