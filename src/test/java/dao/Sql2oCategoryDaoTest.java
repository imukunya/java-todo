package dao;

import models.Category;
import dao.Sql2oCategoryDao;

import models.Task;
import org.junit.Test;
import org.sql2o.*;
import org.junit.*;

import javax.smartcardio.CardTerminal;

import static org.junit.Assert.*;

public class Sql2oCategoryDaoTest {

    private  Sql2oCategoryDao taskDao;
    private  Sql2oCategoryDao categoryDao;//ignore me for now. We'll create this soon.
    private  Sql20TaskDao tDao;
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        taskDao = new Sql2oCategoryDao(sql2o); //ignore me for now
        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Category c = new Category("Sample Category");
        int originalTaskId = c.getId();
        taskDao.add(c);
        assertNotEquals(originalTaskId, c.getId()); //how does this work?
    }

    @Test
    public void existingTasksCanBeFoundById() throws Exception {
        Category task = new Category("Sample Category");
        taskDao.add(task); //add to dao (takes care of saving)
        Category foundTask = taskDao.findById(task.getId()); //retrieve
        assertEquals(task, foundTask); //should be the same
    }

    @Test
    public void getAllTasksByCategoryReturnsTasksCorrectly() throws Exception {
        Category category = setupNewCategory();
        categoryDao.add(category);
        int categoryId = category.getId();
        Task newTask = new Task("mow the lawn", categoryId);
        Task otherTask = new Task("pull weeds", categoryId);
        Task thirdTask = new Task("trim hedge", categoryId);
        tDao.add(newTask);
        tDao.add(otherTask); //we are not adding task 3 so we can test things precisely.
        assertEquals(2, categoryDao.getAllTasksByCategory(categoryId));
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(newTask));
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(otherTask));
        assertFalse(categoryDao.getAllTasksByCategory(categoryId).contains(thirdTask)); //things are accurate!
    }

    //define the following once and then call it as above in your tests.
    public Category setupNewCategory(){
        return new Category("mow the lawn");
    }


}