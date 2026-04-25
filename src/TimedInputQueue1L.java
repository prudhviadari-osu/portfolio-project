package components.timedinputqueue;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;

/**
 * {@code TimedInputQueue} represented as a {@code Queue} of {@code Map.Pair}s,
 * done as a thin layer on top of the library Queue component. * @convention
 * <pre>
 * [this.rep is not null] and
 * [for all pairs in this.rep, the value (timestamp) is >= 0]
 * </pre>
 * 
 * @correspondence <pre>
 * this = [the sequence of pairs in this.rep]
 * </pre>
 */
public class TimedInputQueue1L extends TimedInputQueueSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of the TimedInputQueue.
     */
    private Queue<Map.Pair<String, Double>> rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.rep = new Queue1L<>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public TimedInputQueue1L() {
        this.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void enqueue(String label, Double time) {
        assert label != null : "Violation of: label is not null";
        assert time >= 0 : "Violation of: time is non-negative";

        Map.Pair<String, Double> p = new Map1L.SimplePair<>(label, time);
        this.rep.enqueue(p);
    }

    @Override
    public final Map.Pair<String, Double> dequeue() {
        assert this.length() > 0 : "Violation of: this.length > 0";

        return this.rep.dequeue();
    }

    @Override
    public final int length() {
        return this.rep.length();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final TimedInputQueue newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void transferFrom(TimedInputQueue source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof TimedInputQueue1L : ""
                + "Violation of: source is of type TimedInputQueue1L";

        /*
         * This cast is safe because of the assert above.
         */
        TimedInputQueue1L localSource = (TimedInputQueue1L) source;
        this.rep.transferFrom(localSource.rep);
    }
}