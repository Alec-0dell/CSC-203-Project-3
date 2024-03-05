import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PathingNode implements Comparable<PathingNode>{
    private Point point;
    private List<Point> path; //g-score = path.length
    private Point start;
    private Point end;
    private int hScore;
    private int fScore;

    public PathingNode(Point point, List<Point> path, Point start, Point end){
        this.point = point;
        this.start = start;
        this.end = end;
        this.path = path;
        this.calculateScores();
    }

    public void calculateScores(){
        this.calcFScore();
        this.calcHScore();
    }


    public void calcHScore(){
        this.hScore = this.point.manhattanDistanceTo(this.end);
    }

    public void calcFScore(){
        this.fScore = this.getGScore() + this.point.manhattanDistanceTo(this.end);
    }

    public void setPath(List<Point> path) {
        this.path = path;
        calculateScores();
    }

    public int getGScore() {
        if (this.path == null){
            return 0;
        }
        return this.path.size();
    }

    public int getHScore() {
        return hScore;
    }

    public int getFScore() {
        return fScore;
    }

    public List<Point> getPath() {
        return path;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PathingNode p) {
            return this.getPoint().equals(p.getPoint());
        }
        return false;
    }

    @Override
    public int compareTo(PathingNode o) {
        if (o instanceof PathingNode p) {
            if (this.getPoint().equals(o.getPoint())){
                return 0;
            }
            return 1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return this.point.toString();
    }
}
