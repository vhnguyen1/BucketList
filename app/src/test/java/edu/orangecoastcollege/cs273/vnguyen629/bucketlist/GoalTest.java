package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * @author Vincent Nguyen
 * @version 1.0
 */
public class GoalTest {
    private Goal mGoal;

    /**
     *
     * @throws Exception
     */
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /**
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        mGoal = new Goal();
        mGoal.setTitle("Test Title");
    }

    /**
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {

    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void getTitle() throws Exception {
        assertEquals("Test Title", mGoal.getTitle());
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void setTitle() throws Exception {

    }
}