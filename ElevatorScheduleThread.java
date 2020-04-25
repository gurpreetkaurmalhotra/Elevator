public class ElevatorScheduleThread extends Thread {

    private volatile boolean running;
    private ElevatorSystem elevatorSystem;

    public ElevatorScheduleThread(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
        this.running = true;
    }

    public void run() {
        running = true;
        while(running) {
            scheduleElevators();
            if (Thread.interrupted()) {
                return;
            }
        }
    }

    public void stopRunning() {
        running = false;
    }

    private void scheduleElevators() {
        if (elevatorSystem.getActiveRequests() > 0) {
            elevatorSystem.step();
            for(Elevator elevator : elevatorSystem.getElevators()){
                System.out.println("Elevator[" + elevator.getId() + "] is on floor " + elevator.getCurrentFloor());
            }
        }
    }


}