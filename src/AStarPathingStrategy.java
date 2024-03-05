import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy {

    /**
     * Return a list containing a single point representing the next step toward a goal
     * If the start is within reach of the goal, the returned list is empty.
     *
     * @param start the point to begin the search from
     * @param end the point to search for a point within reach of
     * @param canPassThrough a function that returns true if the given point is traversable
     * @param withinReach a function that returns true if both points are within reach of each other
     * @param potentialNeighbors a function that returns the neighbors of a given point, as a stream
     */
    public List<Point> computePath(
            Point start,
            Point end,
            Predicate<Point> canPassThrough,
            BiPredicate<Point, Point> withinReach,
            Function<Point, Stream<Point>> potentialNeighbors
    ) {
        Set<PathingNode> closedSet = new TreeSet<PathingNode>();
        Set<PathingNode> openSet = new TreeSet<PathingNode>();
        PathingNode curNode = new PathingNode(start, new ArrayList<Point>(), start, end);
        while(!withinReach.test(curNode.getPoint(), end)){
            Stream<Point> neighbors = potentialNeighbors.apply(curNode.getPoint());
            Set<Point> newNodes = neighbors
                .filter(canPassThrough)
                .collect(Collectors.toSet());
            for (Point pt : newNodes) {
                List<Point> newPath = new ArrayList<Point>();
                newPath.addAll(curNode.getPath());
                newPath.add(pt);
                PathingNode toAdd = new PathingNode(pt, newPath, start, end);
                openSet.add(toAdd);
            }
            PathingNode nextNode = getMinFScore(openSet, start, end);
            if(!closedSet.add(nextNode)){
                return new ArrayList<>();
            }
            if (openSet.isEmpty()) {
                return new ArrayList<>();
            }
            openSet.remove(nextNode);
            curNode = nextNode;
        }
        return curNode.getPath();
    }


    public int getGScore(Point point, Point startPoint){
        return point.manhattanDistanceTo(startPoint);
    }

    public int getHScore(Point point, Point endPoint){
        return point.manhattanDistanceTo(endPoint);
    }

    public int getFScore(Point point, Point endPoint, Point startPoint){
        return point.manhattanDistanceTo(endPoint) + point.manhattanDistanceTo(startPoint);
    }

    public PathingNode getMinFScore(Set<PathingNode> openSet, Point endPoint, Point startPoint) {
        PathingNode minPoint = null;
        int minF = Integer.MAX_VALUE;
        int minH = Integer.MAX_VALUE;
    
        for (PathingNode item : openSet) {
            if (item.getFScore() < minF) {
                minF = item.getFScore();
                minH = item.getHScore();
                minPoint = item;
            }
            else if (item.getHScore() < minH){
                minF = item.getFScore();
                minH = item.getHScore();
                minPoint = item;
            }
        }
        return minPoint;
    }
}
