package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;


import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest()
class HtmlControllerTest {

    @Autowired
    private AccountsDao accountsDao;

    @Autowired
    private HtmlController htmlController;

    @Test
    public void checkTableAccounts(){
        accountsDao.insertAccount("1", "2");
        List<User> expected = Arrays.asList(new User("1", "2"));
        List<User> actual = accountsDao.selectUser();
        assertEquals(expected, actual);
    }

    @Test
    public void insertTwoExactDataIntoAccountsShouldReturnError(){
        accountsDao.insertAccount("1", "2");
        assertThrows(DuplicateKeyException.class, () -> {
            accountsDao.insertAccount("1", "2");
        });
    }

    @Test
    public void checkUpdationgInTableHistories(){
        accountsDao.insertHistory("Alex");
        accountsDao.update("Alex", "", 1, 2, "plus");
        accountsDao.update("Alex", "1 + 2 = 3, ", 5, 6, "times");
        List<UserHistory> expected = Arrays.asList(new UserHistory("Alex", "1 + 2 = 3, 5 * 6 = 30, "));
        List<UserHistory> actual = accountsDao.selectHistory();
        assertEquals(expected, actual);
    }
}