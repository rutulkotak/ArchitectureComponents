package com.rk.mvvmtesting.data.mock;

import com.rk.mvvmtesting.data.localdb.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakeUserSource {

    private static FakeUserSource INSTANCE;
    private static final Map<String, User> NOTES_SERVICE_DATA = new LinkedHashMap<>();

    public static FakeUserSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeUserSource();
        }
        return INSTANCE;
    }

    public static List<User> getFakeUsers(int size) {
        List<User> users = new ArrayList<>();
        for(int i = 1; i <= size; i++ ) {
            users.add(new User(i, "Fname "+i, "Lname "+i));
        }
        return users;
    }

}