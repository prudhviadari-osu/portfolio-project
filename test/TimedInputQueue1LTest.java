import timedInputQueue.TimedInputQueue;
import timedInputQueue.TimedInputQueue1L;

/**
 * Concrete test suite for TimedInputQueue1L.
 */
public class TimedInputQueue1LTest extends TimedInputQueueTest {

    @Override
    protected TimedInputQueue constructorTest() {
        return new TimedInputQueue1L();
    }

    @Override
    protected TimedInputQueue constructorRef() {
        return new TimedInputQueue1L();
    }
}