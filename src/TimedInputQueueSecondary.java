package timedInputQueue;

import components.map.Map;

/**
 * Abstract class for TimedInputQueue. * This class provides the implementation
 * for the secondary methods using only the kernel methods (enqueue, dequeue,
 * length).
 */
public abstract class TimedInputQueueSecondary implements TimedInputQueue {

    @Override
    public boolean isEmpty() {
        return this.length() == 0;
    }

    @Override
    public Map.Pair<String, Double> front() {
        assert this.length() > 0 : "Violation of: this.length > 0";

        // Grab the first element
        Map.Pair<String, Double> first = this.dequeue();

        // Immediately put it back so we don't permanently change the queue
        this.enqueue(first.key(), first.value());

        // Rotate the rest of the queue so 'first' is back at the front
        for (int i = 0; i < this.length() - 1; i++) {
            Map.Pair<String, Double> temp = this.dequeue();
            this.enqueue(temp.key(), temp.value());
        }

        return first;
    }

    @Override
    public double duration() {
        if (this.length() < 2) {
            return 0.0;
        }

        double startTime = 0;
        double endTime = 0;
        int len = this.length();

        for (int i = 0; i < len; i++) {
            Map.Pair<String, Double> p = this.dequeue();
            if (i == 0) {
                startTime = p.value();
            }
            if (i == len - 1) {
                endTime = p.value();
            }
            this.enqueue(p.key(), p.value());
        }
        return endTime - startTime;
    }

    @Override
    public double averageInterval() {
        if (this.length() < 2) {
            return 0.0;
        }
        // Total duration divided by the number of gaps between entries
        return this.duration() / (this.length() - 1);
    }

    @Override
    public int frequency(String label) {
        int count = 0;
        int len = this.length();
        for (int i = 0; i < len; i++) {
            Map.Pair<String, Double> p = this.dequeue();
            if (p.key().equals(label)) {
                count++;
            }
            this.enqueue(p.key(), p.value());
        }
        return count;
    }

    @Override
    public void removeOlderThan(double threshold) {
        int originalLength = this.length();
        for (int i = 0; i < originalLength; i++) {
            Map.Pair<String, Double> p = this.dequeue();
            // We only enqueue it back if it meets the threshold
            if (p.value() >= threshold) {
                this.enqueue(p.key(), p.value());
            }
        }
    }

    // --- Standard Methods (Object Overrides) ---

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<");
        int len = this.length();
        for (int i = 0; i < len; i++) {
            Map.Pair<String, Double> p = this.dequeue();
            sb.append("(").append(p.key()).append(",").append(p.value())
                    .append(")");
            if (i < len - 1) {
                sb.append(",");
            }
            this.enqueue(p.key(), p.value());
        }
        sb.append(">");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof TimedInputQueue)) {
            return false;
        }

        TimedInputQueue q = (TimedInputQueue) obj;
        if (this.length() != q.length()) {
            return false;
        }

        boolean equal = true;
        int len = this.length();
        for (int i = 0; i < len; i++) {
            Map.Pair<String, Double> p1 = this.dequeue();
            Map.Pair<String, Double> p2 = q.dequeue();

            if (!p1.key().equals(p2.key()) || !p1.value().equals(p2.value())) {
                equal = false;
            }

            this.enqueue(p1.key(), p1.value());
            q.enqueue(p2.key(), p2.value());
        }
        return equal;
    }
}
