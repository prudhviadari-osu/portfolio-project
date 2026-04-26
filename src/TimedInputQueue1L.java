import components.map.Map;
import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Implementation of TimedInputQueue using a Queue of Map.Pairs.
 */
public class TimedInputQueue1L extends TimedInputQueueSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of this.
     */
    private Queue<Map.Pair<String, Double>> rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.rep = new Queue1L<Map.Pair<String, Double>>();
    }

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public TimedInputQueue1L() {
        this.createNewRep();
    }

    /*
     * Kernel Methods ---------------------------------------------------------
     */

    @Override
    public final void enqueue(String label, double time) {
        // We use our local SimplePair class to avoid JAR visibility issues
        Map.Pair<String, Double> p = new SimplePair<String, Double>(label,
                time);
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
     * Standard Methods -------------------------------------------------------
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
                + "Violation of: source is of dynamic type TimedInputQueue1L";

        TimedInputQueue1L localSource = (TimedInputQueue1L) source;
        this.rep.transferFrom(localSource.rep);
    }

    /*
     * Private Helper Class ---------------------------------------------------
     */

    /**
     * Local implementation of Map.Pair. This allows us to create Pair objects
     * without needing MapSecondary.
     */
    private static class SimplePair<K, V> implements Map.Pair<K, V> {
        private K key;
        private V value;

        SimplePair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K key() {
            return this.key;
        }

        @Override
        public V value() {
            return this.value;
        }
    }
}
