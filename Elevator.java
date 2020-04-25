import java.util.Comparator;
import java.util.TreeSet;

public class Elevator {

    private final Integer id;
    private Integer currentFloor;

    private TreeSet<Integer> upDestinationFloors;

    private TreeSet<Integer> downDestinationFloors;
    Direction direction;

    public Elevator(Integer currentFloor, Integer id) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.upDestinationFloors = new TreeSet<Integer>();
        this.downDestinationFloors = new TreeSet<Integer>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        direction = Direction.NONE;
    }

    public int getId(){
        return this.id;
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    }

    public void addNewDestination(Integer destination) {
        if(destination > currentFloor){
            upDestinationFloors.add(destination);
        }else{
            downDestinationFloors.add(destination);
        }
    }

    public boolean moveAndCheckIfServed() {
        direction();
        if(direction.equals(Direction.UP)){
            if(upDestinationFloors.first().equals(currentFloor)){
                return popUpDestination();
            }else {
                currentFloor++;
            }
        }else if(direction.equals(Direction.DOWN)){
            if(downDestinationFloors.first().equals(currentFloor)){
                return popDownDestination();
            }else {
                currentFloor--;
            }
        }
        return false;
    }

    public Direction getDirection(){
        return this.direction;
    }

    private void direction() {
        if(direction.equals(Direction.NONE)){
            if(upDestinationFloors.size() > 0 && downDestinationFloors.size() > 0){
                if(Math.abs(currentFloor - upDestinationFloors.first()) < Math.abs(currentFloor - downDestinationFloors.first())){
                    direction = Direction.UP;
                }else{
                    direction = Direction.DOWN;
                }
            }else if(upDestinationFloors.size() > 0){
                direction = Direction.UP;
            }else if(downDestinationFloors.size() > 0){
                direction = Direction.DOWN;
            }
        }
    }

    private boolean popUpDestination() {
        upDestinationFloors.remove(upDestinationFloors.first());
        if (upDestinationFloors.size() == 0) {
            direction = Direction.NONE;
        }
        return true;
    }

    private boolean popDownDestination() {
        downDestinationFloors.remove(downDestinationFloors.first());
        if(downDestinationFloors.size() == 0){
            direction = Direction.NONE;
        }
        return true;
    }

    public int getTotalRequests(){
        return upDestinationFloors.size() + downDestinationFloors.size();
    }
}
