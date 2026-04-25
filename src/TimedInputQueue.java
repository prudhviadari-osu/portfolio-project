import components.map.Map;

/**
 * Enhanced interface for TimedInputQueue.
 */
public interface TimedInputQueue extends TimedInputQueueKernel {

    /**
     * Reports the pair at the front of {@code this} without removing it.
     *
     * @return the event-timestamp pair at the front
     * @requires this /= <>
     * @ensures <front> is prefix of this
     */
    Map.Pair<String, Double> front();

    /**
     * Returns the time elapsed between the first and last events in
     * {@code this}.
     *
     * @return the total time duration
     * @requires |this| > 0
     * @ensures duration = [timestamp of last element] - [timestamp of first
     *          element]
     */
    double duration();

    /**
     * Reports whether {@code this} contains any events.
     *
     * @return true if empty, false otherwise
     * @ensures isEmpty = (|this| = 0)
     */
    boolean isEmpty();

    /**
     * Removes all events from {@code this} that have a timestamp strictly less
     * than the given threshold. * @param threshold the timestamp cut-off point
     *
     * @updates this
     * @ensures this = [elements of #this whose timestamps are >= threshold,
     *          remaining in their original relative order]
     */
    void removeOlderThan(double threshold);

    /**
     * Counts how many times a specific event appears in {@code this}.
     *
     * @param label
     *            the event label to count
     * @return the number of occurrences of the label
     * @ensures frequency = [number of times label appears in this]
     */
    int frequency(String label);

    /**
     * Calculates the average time between events.
     *
     * @return the average interval between events
     * @requires |this| > 1
     * @ensures averageInterval = duration / (|this| - 1)
     */
    double averageInterval();
}
