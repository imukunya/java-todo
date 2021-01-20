package dao;

import models.Category;
import models.Task;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2oTaskDaoTest {
    private Sql20TaskDao taskDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        taskDao = new Sql20TaskDao(sql2o); //ignore me for now
        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Task task = setupNewTask();
        int originalTaskId = task.getCategoryId();
        taskDao.add(task);
        assertNotEquals(originalTaskId, task.getId()); //how does this work?
    }

    @Test
    public void existingTasksCanBeFoundById() throws Exception {
        Task task = setupNewTask();
        taskDao.add(task);
        Task foundTask = taskDao.findById(task.getId()); //retrieve
        assertEquals(task, foundTask); //should be the same
    }

    @Test
    public void addingTaskSetsId() throws Exception {
        Task task = setupNewTask();
        int originalTaskId = task.getId();
        taskDao.add(task);
        assertNotEquals(originalTaskId, task.getId());
    }

    //define the following once and then call it as above in your tests.
    public Task setupNewTask(){
        return new Task("mow the lawn", 1);
    }

    @Test
    public void categoryIdIsReturnedCorrectly() throws Exception {
        Task task = setupNewTask();
        int originalCatId = task.getCategoryId();
        taskDao.add(task);
        assertEquals(originalCatId, taskDao.findById(task.getId()).getCategoryId());
    }


    @Test
    public void updateChangesTaskContent() throws Exception {
        String initialDescription = "mow the lawn";
        Task task = new Task (initialDescription, 1);// or use the helper method for easier refactoring
        taskDao.add(task);
        taskDao.update(task.getId(),"brush the cat", 1);
        Task updatedTask = taskDao.findById(task.getId()); //why do I need to refind this?
        assertNotEquals(initialDescription, updatedTask.getDescription());
    }




}