package org.minjay.data.stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Java8 test
 * Created by minjay on 2017/4/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class StreamTest {

    private List<User> users = new ArrayList<>();

    @Before
    public void setup() {
        User user1 = new User("minjay", 23);
        User user2 = new User("kobe", 23);
        User user3 = new User("wade", 23);
        User user4 = new User("paul", 23);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
    }

    @Test
    public void testGetOne() {
        Optional<User> optional = users.stream()
                .filter(user -> user.getName().contains("wade"))
                .findFirst();
        System.out.println("end");
    }

    @Test
    public void testLoop() {
    List<User> result = users.stream()
            .filter(user -> user.getName().contains("wade"))
            .collect(Collectors.toList());
        System.out.println("end");
    }

}
