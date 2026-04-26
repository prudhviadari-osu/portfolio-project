import timedInputQueue.TimedInputQueue;
import timedInputQueue.TimedInputQueue1L;

/**
 * A simple application that uses TimedInputQueue to track and analyze user
 * click patterns. * @author Prudhvi Adari
 */
public final class ActivityTracker {

        /**
         * Private constructor to prevent instantiation.
         */
        private ActivityTracker() {
        }

        /**
         * Main method to demonstrate frequency and duration analysis.
         *
         * @param args
         *                command line arguments
         */
        public static void main(String[] args) {
                /*
                 * Initialize the queue
                 */
                TimedInputQueue logs = new TimedInputQueue1L();

                /*
                 * Simulate user input: Type a character, then click, then type
                 * again. Times are in milliseconds.
                 */
                logs.enqueue("Key_Press", 100.0);
                logs.enqueue("Mouse_Click", 250.0);
                logs.enqueue("Key_Press", 400.0);
                logs.enqueue("Key_Press", 600.0);
                logs.enqueue("Mouse_Click", 850.0);

                /*
                 * Output analysis using Secondary methods
                 */
                System.out.println("Total interaction time: " + logs.duration()
                                + "ms");
                System.out.println("Average gap between actions: "
                                + logs.averageInterval() + "ms");
                System.out.println("Number of Key Presses: "
                                + logs.frequency("Key_Press"));
                System.out.println("Number of Mouse Clicks: "
                                + logs.frequency("Mouse_Click"));
        }
}