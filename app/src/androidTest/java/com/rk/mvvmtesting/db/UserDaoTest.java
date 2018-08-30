package com.rk.mvvmtesting.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rk.mvvmtesting.data.LocalDatabase;
import com.rk.mvvmtesting.data.localdb.User;
import com.rk.mvvmtesting.data.localdb.UserDao;
import com.rk.mvvmtesting.data.mock.FakeUserSource;
import com.rk.mvvmtesting.utilities.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private LocalDatabase localDatabase;
    private UserDao userDao;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        localDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                LocalDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
        userDao = localDatabase.userDao();
    }

    @After
    public void closeDb() throws Exception {
        localDatabase.close();
    }

    @Test
    public void getUsersWhenNoProductInserted() throws InterruptedException {
        List<User> users = LiveDataTestUtil.getValue(userDao.getAllUsers());
        assertTrue(users.isEmpty());
    }

    @Test
    public void onInsertingUsers_checkIf_RowCountIsCorrect() throws InterruptedException {
        userDao.insertAll(FakeUserSource.getFakeUsers(5));
        List<User> userList = LiveDataTestUtil.getValue(userDao.getAllUsers());
        assertEquals(5, userList.size());
    }

    @Test
    public void getUserById() throws InterruptedException {
        userDao.insertAll(FakeUserSource.getFakeUsers(5));
        User user = LiveDataTestUtil.getValue(userDao.getUserById(1));
        assertThat(user.getId(), is(1));
    }

}