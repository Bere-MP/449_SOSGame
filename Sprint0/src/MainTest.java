import static org.junit.jupiter.api.Assertions.assertEquals; // <- add this
import org.junit.jupiter.api.Test;

class Counter {
    private int value = 0;

    public void increment() {
        value++;
    }
    public void decrement() {
        value--;
    }
    public int getValue() {
        return value;
    }
}

class MainTest {

    @Test
    void testIncrement() {
        Counter c = new Counter();
        c.increment();
        c.increment();
        assertEquals(2, c.getValue());
    }

    @Test
    void testDecrement() {
        Counter c = new Counter();
        c.increment();
        c.decrement();
        assertEquals(0, c.getValue());
    }
}
