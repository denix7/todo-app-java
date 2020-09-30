package com.todo.app;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    TaskDaoTest test = new TaskDaoTest();

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void shouldGetATask() {
        try {
            test.get();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void shouldSaveATask() {
        try {
            test.add();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteATask() {
        try {
            test.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
