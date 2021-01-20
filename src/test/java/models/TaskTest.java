package models;

import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class TaskTest {
    @Test
    public void NewTaskObjectGetsCorrectlyCreated_true() throws Exception {
        Task task = setupNewTask();
        assertEquals(true, task instanceof Task);
    }

    @Test
    public void TaskInstantiatesWithDescription_true() throws Exception {
        Task task = setupNewTask();
        assertEquals("Mow the lawn", task.getDescription());
    }

    @Test
    public void isCompletedPropertyIsFalseAfterInstantiation() throws Exception {
        Task task = setupNewTask();
        assertEquals(false, task.getCompleted()); //should never start as completed
    }

    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
        Task task = setupNewTask();
        assertEquals(LocalDateTime.now().getDayOfWeek(), task.getCreatedAt().getDayOfWeek());
    }

    //helper methods
    @Test
    public void addingTaskSetsId() throws Exception {
        Task task = setupNewTask();
        int originalTaskId = task.getId();

        assertNotEquals(originalTaskId, task.getId());
    }

    //define the following once and then call it as above in your tests.
    public Task setupNewTask() {
        return new Task("mow the lawn", 1);
    }
}