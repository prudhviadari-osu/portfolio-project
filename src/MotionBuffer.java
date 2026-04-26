import timedInputQueue.TimedInputQueue;
import timedInputQueue.TimedInputQueue1L;

/**
 * A motion tracking utility that maintains a rolling buffer of coordinates and
 * filters out stale data. * @author Prudhvi Adari
 */
public class MotionBuffer {

    /**
     * The internal storage using our custom component.
     */
    private TimedInputQueue movementData;

    /**
     * Default constructor.
     */
    public MotionBuffer() {
        this.movementData = new TimedInputQueue1L();
    }

    /**
     * Adds a new coordinate and clears out data older than 1 second.
     *
     * @param coord
     *            the coordinate label (e.g., "X:10,Y:20")
     * @param timestamp
     *            the current time in seconds
     */
    public void recordMovement(String coord, double timestamp) {
        this.movementData.enqueue(coord, timestamp);

        // Use the secondary method to keep the buffer fresh (last 1.0 seconds)
        double threshold = timestamp - 1.0;
        this.movementData.removeOlderThan(threshold);
    }

    /**
     * Returns the number of active points in the current 1-second window.
     *
     * @return count of recent movements
     */
    public int activePointCount() {
        return this.movementData.length();
    }
}