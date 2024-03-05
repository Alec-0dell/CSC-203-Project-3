import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PImage;

public class Mushroom extends Actionable{

    public static final String MUSHROOM_KEY = "mushroom";

    public Mushroom(String id, Point position, List<PImage> images, double behaviorPeriod) {
        super(id, position, images, 0, 0, behaviorPeriod);
    }


    @Override
    public void updateImage() {
        throw new UnsupportedOperationException("Unimplemented method 'updateImage'");
    }

    public void executeActivity(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        List<Point> adjacentPositions = new ArrayList<>(List.of(
                new Point(this.getPosition().x - 1, this.getPosition().y),
                new Point(this.getPosition().x + 1, this.getPosition().y),
                new Point(this.getPosition().x, this.getPosition().y - 1),
                new Point(this.getPosition().x, this.getPosition().y + 1)
        ));
        Collections.shuffle(adjacentPositions);

        List<Point> mushroomBackgroundPositions = new ArrayList<>();
        List<Point> mushroomEntityPositions = new ArrayList<>();
        for (Point adjacentPosition : adjacentPositions) {
            if (world.inBounds(adjacentPosition) && !world.isOccupied(adjacentPosition) && world.hasBackground(adjacentPosition)) {
                Background bg = world.getBackgroundCell(adjacentPosition);
                if (bg.getId().equals("grass")) {
                    mushroomBackgroundPositions.add(adjacentPosition);
                } else if (bg.getId().equals("grass_mushrooms")) {
                    mushroomEntityPositions.add(adjacentPosition);
                }
            }
        }

        if (!mushroomBackgroundPositions.isEmpty()) {
            Point position = mushroomBackgroundPositions.get(0);

            Background background = new Background("grass_mushrooms", imageLibrary.get("grass_mushrooms"), 0);
            world.setBackgroundCell(position, background);
        } else if (!mushroomEntityPositions.isEmpty()) {
            Point position = mushroomEntityPositions.get(0);

            Mushroom mushroom = new Mushroom(MUSHROOM_KEY, position, imageLibrary.get(MUSHROOM_KEY), this.getBehaviorPeriod() * 4.0);

            world.addEntity(mushroom);
            mushroom.scheduleActions(scheduler, world, imageLibrary);
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Behavior((Entity)this, world, imageLibrary), this.getBehaviorPeriod());
    }
}
