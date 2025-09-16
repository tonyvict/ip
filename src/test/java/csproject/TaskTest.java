package csproject;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Minimal test for Task class tagging functionality.
 */
class TaskTest {

    private static class TestTask extends Task {
        public TestTask(String description) {
            super(description);
        }

        @Override
        public String typeIcon() {
            return "T";
        }
    }

    @Test
    void addTagAndToString_includesTags() {
        TestTask task = new TestTask("Test task");
        task.addTag("urgent");
        task.addTag("work");

        String result = task.toString();
        assertTrue(result.contains("#urgent"));
        assertTrue(result.contains("#work"));
    }
}
