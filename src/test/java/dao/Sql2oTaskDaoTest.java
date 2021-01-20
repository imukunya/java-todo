package dao;

import models.Category;
import models.Task;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2oTaskDaoTest {
    private static Sql20TaskDao taskDao; //ignore me for now. We'll create this soon.
    private static Connection conn; //must be sql2o class conn
    private static Sql2oCategoryDao categoryDao; //these variables are now static.

    @BeforeClass //changed to @BeforeClass (run once before running any tests in this file)
    public static void setUp() throws Exception { //changed to static
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        taskDao = new Sql20TaskDao(sql2o); //ignore me for now
        categoryDao = new Sql2oCategoryDao(sql2o);
        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        categoryDao.clearAllCategories(); // clear all categories after every test
        taskDao.clearAllTasks(); // clear all tasks after every test
    }

    @AfterClass // changed to @AfterClass (run once after all tests in this file completed)
    public static void shutDown() throws Exception { //changed to static and shutDown
        conn.close(); // close connection once after this entire test file is finished
        System.out.println("connection closed");
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