package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class) // ac를 자동으로 만들어줌
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UserDaoImplTest {

    @Autowired
    UserDao userDao;

    @Test
    public void selectUser() {
        User user = userDao.selectUser("asdf2");
        if (user == null) {
            System.out.println("일치하는 유저가 없습니다.");
        } else {
            System.out.println("user = " + user);
        }
    }

    @Test
    public void insertUser() {
        User user = new User("asdf", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());

        int rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt == 1);
    }

    @Test
    public void deleteUser() {
        int rowCnt = userDao.deleteUser("asdf2");
        assertTrue(rowCnt == 1);
    }

    @Test
    public void TestupdateUser() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2021, 1, 1);

        userDao.deleteUser("asdf2");

        User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(cal.getTimeInMillis()), "fb", new Date());
        int rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt == 1);
        System.out.println("user = " + user);

        user.setPwd("4321");
        user.setEmail("asdf@asdf.com");
        rowCnt = userDao.updateUser(user);
        assertTrue(rowCnt == 1);

        User user2 = userDao.selectUser(user.getId());
        System.out.println("user2 = " + user2);
        assertTrue(user.equals(user2));


    }
}