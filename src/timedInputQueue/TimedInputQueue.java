package timedInputQueue;

import components.map.Map;

/**
 * Enhanced interface for TimedInputQueue.
 *
 * @mathmodel type TimedInputQueue is modeled by string of (label: string, time:
 *            real)
 */
public interface TimedInputQueue extends TimedInputQueueKernel {

    /**
     * Reports whether {@code this} is empty.
     *
     * @return true iff {@code this} is empty
     * @ensures isEmpty = (|this| = 0)
     */
    boolean isEmpty();

    /**
     * Reports the front of {@code this} without removing it.
     *
     * @return the event-timestamp pair at the front
     * @requires this /= <>
     * @ensures <front> is prefix of this
     */
    Map.Pair<String, Double> front();

    /**
     * Reports the time elapsed between the first and last events in
     * {@code this}.
     *
     * @return the duration between the first and last events
     * @ensures if |this| < 2 then duration = 0.0 else duration = [time of last
     *          event] - [time of first event]
     */
    double duration();

    /**
     * Calculates the average time between events in {@code this}.
     *
     * @return the average time between events
     * @ensures if |this| < 2 then averageInterval = 0.0 else averageInterval =
     *          duration() / (|this| - 1)
     */
    double averageInterval();

    /**
     * Counts how many times a specific event label appears in {@code this}.
     *
     * @param label
     *            the specific event label to count
     * @return the number of occurrences of {@code label}
     * @ensures frequency = [count of elements in this where key == label]
     */
    int frequency(String label);

    /**
     * Clears out any events that happened before a certain timestamp.
     *
     * @param threshold
     *            the minimum timestamp to keep
     * @updates this
     * @ensures this = [#this with all elements where time < threshold removed,
     *          and relative order of remaining elements preserved]
     */
    void removeOlderThan(double threshold);
}