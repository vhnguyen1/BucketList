package edu.orangecoastcollege.cs273.vnguyen629.bucketlist;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Instrumentation test for DBHelper, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * @author Vincent Nguyen
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
public class DBHelperTest {
    private Context mContext;

    /**
     *
     */
    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    /**
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {

    }

    /*
    @Test
    public void testLoadJSONFromAsset() {
        // Tests for exception that you're NOT supposed to have
        try {
            ArrayList<MusicEvent> testList = JSONLoader.loadJSONFromAsset(mContext);
            assertEquals(8, testList.size());
        } catch (IOException e) {
            fail("Expected loader to load 8 Music Events.");
            //e.printStackTrace();
        }
    }
    */
}