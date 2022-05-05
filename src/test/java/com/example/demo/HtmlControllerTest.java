package com.example.demo;

import com.example.demo.userOperations.AccountsDao;
import com.example.demo.userOperations.HtmlController;
import com.example.demo.userOperations.User;
import com.example.demo.userOperations.UserHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest()
class HtmlControllerTest {

    @Autowired
    private AccountsDao accountsDao;

    @Autowired
    private HtmlController htmlController;

//    @Test
//    public void checkTableAccounts(){
//        accountsDao.insertAccount("1", "2");
//        List<User> expected = Arrays.asList(new User("1", "2"));
//        List<User> actual = accountsDao.selectUser();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void insertTwoExactDataIntoAccountsShouldReturnError(){
//        accountsDao.insertAccount("1", "2");
//        assertThrows(DuplicateKeyException.class, () -> {
//            accountsDao.insertAccount("1", "2");
//        });
//    }
//
//    @Test
//    public void checkUpdationgInTableHistories(){
//        accountsDao.insertHistory("Alex");
//        accountsDao.update("Alex", "", 1, 2, "plus");
//        accountsDao.update("Alex", "1 + 2 = 3, ", 5, 6, "times");
//        List<UserHistory> expected = Arrays.asList(new UserHistory("Alex", "1 + 2 = 3, 5 * 6 = 30, "));
//        List<UserHistory> actual = accountsDao.selectHistory();
//        assertEquals(expected, actual);
//    }
}