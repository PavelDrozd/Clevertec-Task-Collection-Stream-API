package by.clevertec;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    public void task22() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("ComputerScience", 18);
        expected.put("Chemistry", 18);
        expected.put("Mathematics", 18);
        expected.put("Physics", 18);
        assertEquals(expected, Main.task22());
    }
}
