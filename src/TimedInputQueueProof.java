import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Proof of concept for the {@code TimedInputQueue} component.
 *
 * @author Prudhvi Adari
 */
public final class TimedInputQueueProof {

    /**
     * @convention $this.eventQueue \neq null$
     * @correspondence this = [the sequence of (label, time) pairs in
     *                 this.eventQueue]
     */
    private static Queue<Map.Pair<String, Double>> eventQueue = new Queue1L<>();

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TimedInputQueueProof() {
    }

    /**
     * Adds an event with a label and a timestamp to the end of the history.
     *
     * @param label
     *            the string name of the event
     * @param time
     *            the timestamp of when the event occurred
     * @updates this
     * @requires [time is >= the timestamp of the last element in this]
     * @ensures this = #this * <(label, time)>
     */
    public static void enqueue(String label, double time) {
        Map<String, Double> temp = new Map1L<>();
        temp.add(label, time);
        eventQueue.enqueue(temp.remove(label));
    }

    /**
     * Removes and returns the oldest event in the history. * @requires this /=
     * <> (not empty)
     *
     * @return the (label, time) pair of the oldest event
     * @updates this
     * @ensures dequeue = [old front of this]
     */
    public static Map.Pair<String, Double> dequeue() {
        return eventQueue.dequeue();
    }

    /**
     * Counts occurrences of a specific action to identify patterns. * @param
     * label the event to look for
     *
     * @param label
     *            the string name of the event to count
     * @return the number of times the specified label appears in the history
     * @ensures frequency = [number of times label appears in this]
     */
    public static int frequency(String label) {
        int count = 0;
        for (Map.Pair<String, Double> p : eventQueue) {
            if (p.key().equals(label)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total time duration of the captured history.
     *
     * @return the total duration from first to last event
     * @ensures duration = [time of last event] - [time of first event]
     */
    public static double duration() {
        double timeSpan = 0.0;
        if (eventQueue.length() > 0) {
            double start = eventQueue.front().value();
            double end = start;
            for (Map.Pair<String, Double> p : eventQueue) {
                end = p.value();
            }
            timeSpan = end - start;
        }
        return timeSpan;
    }

    /**
     * Main method.
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        enqueue("JUMP", 0.0);
        enqueue("JUMP", 0.15);
        enqueue("DASH", 0.4);

        out.println("History length: " + eventQueue.length());
        out.println("JUMP frequency: " + frequency("JUMP"));
        out.println("Total duration: " + duration());

        out.close();
    }
}
