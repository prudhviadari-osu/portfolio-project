import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;
import timedInputQueue.TimedInputQueue;
import timedInputQueue.TimedInputQueue1L;

public abstract class TimedInputQueueTest {

    protected abstract TimedInputQueue constructorTest();

    protected abstract TimedInputQueue constructorRef();

    // -------------------------------------------------------------------------
    // KERNEL METHOD TESTS
    // -------------------------------------------------------------------------

    @Test
    public final void testEnqueueEmpty() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("a", 1.0);
        assertEquals(1, q.length());
    }

    @Test
    public final void testEnqueueMultiple() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("a", 1.0);
        q.enqueue("b", 2.0);
        q.enqueue("c", 3.0);
        assertEquals(3, q.length());
    }

    @Test
    public final void testDequeueToEmpty() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("a", 1.0);
        Map.Pair<String, Double> p = q.dequeue();
        assertEquals("a", p.key());
        assertEquals(0, q.length());
    }

    @Test
    public final void testLengthEmpty() {
        TimedInputQueue q = this.constructorTest();
        assertEquals(0, q.length());
    }

    // -------------------------------------------------------------------------
    // SECONDARY METHOD TESTS
    // -------------------------------------------------------------------------

    @Test
    public final void testFrontDoesNotRemove() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("keepMe", 5.0);
        q.enqueue("other", 10.0);
        String key = q.front().key();
        assertEquals("keepMe", key);
        assertEquals(2, q.length()); // Crucial: front should not change size
    }

    @Test
    public final void testDurationNormal() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("start", 10.0);
        q.enqueue("mid", 15.0);
        q.enqueue("end", 30.5);
        assertEquals(20.5, q.duration(), 0.001);
    }

    @Test
    public final void testDurationSingleElement() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("only", 10.0);
        assertEquals(0.0, q.duration(), 0.001);
    }

    @Test
    public final void testAverageIntervalTypical() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("a", 0.0);
        q.enqueue("b", 10.0);
        q.enqueue("c", 20.0);
        // 20 duration / 2 intervals = 10.0
        assertEquals(10.0, q.averageInterval(), 0.001);
    }

    @Test
    public final void testFrequencyZero() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("a", 1.0);
        assertEquals(0, q.frequency("b"));
    }

    @Test
    public final void testFrequencyMultiple() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("click", 1.0);
        q.enqueue("move", 2.0);
        q.enqueue("click", 3.0);
        assertEquals(2, q.frequency("click"));
    }

    @Test
    public final void testRemoveOlderThanFilterAll() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("old1", 5.0);
        q.enqueue("old2", 8.0);
        q.removeOlderThan(10.0);
        assertEquals(0, q.length());
    }

    @Test
    public final void testRemoveOlderThanKeepAll() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("new1", 15.0);
        q.enqueue("new2", 20.0);
        q.removeOlderThan(10.0);
        assertEquals(2, q.length());
    }

    @Test
    public final void testIsEmptyTrue() {
        TimedInputQueue q = this.constructorTest();
        assertTrue(q.isEmpty());
    }

    // -------------------------------------------------------------------------
    // STANDARD METHOD TESTS
    // -------------------------------------------------------------------------

    @Test
    public final void testClearPopulated() {
        TimedInputQueue q = this.constructorTest();
        q.enqueue("a", 1.0);
        q.enqueue("b", 2.0);
        q.clear();
        assertEquals(0, q.length());
    }

    @Test
    public final void testNewInstance() {
        TimedInputQueue q1 = this.constructorTest();
        q1.enqueue("a", 1.0);
        TimedInputQueue q2 = q1.newInstance();
        assertEquals(0, q2.length());
        assertTrue(q2 instanceof TimedInputQueue1L);
    }
}