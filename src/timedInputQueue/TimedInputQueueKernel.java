package timedInputQueue;

import components.map.Map;
import components.standard.Standard;

/**
 * Kernel interface for TimedInputQueue.
 *
 * @mathmodel type TimedInputQueueKernel is modeled by string of (label: string,
 *            time: real)
 * @initially ensures this = <>
 */
public interface TimedInputQueueKernel extends Standard<TimedInputQueue> {

    /**
     * Adds an event with a label and a timestamp to the end of {@code this}.
     *
     * @param label
     *            the name of the action
     * @param time
     *            the timestamp of the action
     * @updates this
     * @requires [time is >= the timestamp of the last element in this]
     * @ensures this = #this * <(label, time)>
     */
    void enqueue(String label, double time);

    /**
     * Removes and returns the oldest event at the front of {@code this}.
     *
     * @return the event-timestamp pair at the front
     * @updates this
     * @requires this /= <>
     * @ensures #this = <dequeue> * this
     */
    Map.Pair<String, Double> dequeue();

    /**
     * Reports the current number of events in {@code this}.
     *
     * @return the number of events
     * @ensures length = |this|
     */
    int length();
}