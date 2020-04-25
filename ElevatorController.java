import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ElevatorController {

    private static String FINAL_END = "quit";

    public static void main(String[] args) {

        ElevatorSystem elevatorSystem = new ElevatorSystem();

        ElevatorScheduleThread thread = new ElevatorScheduleThread(elevatorSystem);
        thread.start();

        String input = null;
        while (!FINAL_END.equals(input)) {

            System.out.print("Enter floor: \n");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                input = br.readLine();
            } catch (IOException ioe) {
                System.out.println("IO error trying to read input!");
                thread.stopRunning();
                System.exit(1);
            }

            if (!FINAL_END.equals(input) && !"".equals(input)) {
                try {
                    int fromFloor = Integer.parseInt(input);
                    elevatorSystem.schedulePickUpRequest(fromFloor);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Floor!");
                }
            }

        }

        thread.stopRunning();

    }
}
