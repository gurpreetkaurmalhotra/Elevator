public class ElevatorSystem {

    private Elevator[] elevators;
    private static int ELEVATOR_COUNT = 3;
    
    private int activeRequests;

    public ElevatorSystem(){
        this.elevators = new Elevator[ELEVATOR_COUNT];
        for(int elevator = 0; elevator < ELEVATOR_COUNT; elevator++){
            elevators[elevator] = new Elevator(0, elevator);
        }
    }

    public void schedulePickUpRequest(int fromFloor) {

        int maxRequestPerElevator = activeRequests / ELEVATOR_COUNT + 1;
        int minUp = Integer.MAX_VALUE;
        Elevator minUpElevator = null;
        int minDown = Integer.MAX_VALUE;
        Elevator minDownElevator = null;

        for(Elevator elevator : elevators){

            if(elevator.getTotalRequests() >= maxRequestPerElevator){
                continue;
            }
            if((elevator.getDirection() == Direction.UP
                    || elevator.getDirection() == Direction.NONE)
                    && elevator.getCurrentFloor() < fromFloor){
                if(minUp > fromFloor - elevator.getCurrentFloor()){
                    minUp = fromFloor - elevator.getCurrentFloor();
                    minUpElevator = elevator;
                }
            }else if((elevator.getDirection() == Direction.DOWN
                    || elevator.getDirection() == Direction.NONE)
                    && elevator.getCurrentFloor() > fromFloor){
                if(minDown > elevator.getCurrentFloor() - fromFloor){
                    minDown = elevator.getCurrentFloor() - fromFloor;
                    minDownElevator = elevator;
                }
            }
        }

        if(minDownElevator != null && minUpElevator != null){
            if(minDown < minUp){
                minDownElevator.addNewDestination(fromFloor);
            }else{
                minUpElevator.addNewDestination(fromFloor);
            }
        } else if(minDownElevator != null){
            minDownElevator.addNewDestination(fromFloor);
        } else if(minUpElevator != null){
            minUpElevator.addNewDestination(fromFloor);
        }

        activeRequests++;
    }

    public void step() {
        for (Elevator currElevator : elevators) {
            if(currElevator.moveAndCheckIfServed()){
                activeRequests--;
            }
        }
    }

    public Elevator[] getElevators(){
        return elevators;
    }

    public int getActiveRequests(){
        return activeRequests;
    }

}
