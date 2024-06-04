package com.sismics.docs.core.dao.jpa;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.model.jpa.User;
import com.sismics.docs.core.util.TransactionUtil;
import com.sismics.docs.core.util.authentication.InternalAuthenticationHandler;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the persistance layer.
 * 
 * @author jtremeaux
 */
public class TestJpa extends BaseTransactionalTest {
    @Test
    public void testJpa() throws Exception {
        // Create a user
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("username");
        user.setPassword("12345678");
        user.setEmail("toto@docs.com");
        user.setRoleId("admin");
        user.setStorageQuota(10L);
        String id = userDao.create(user, "me");
        
        TransactionUtil.commit();

        // Search a user by his ID
        user = userDao.getById(id);
        Assert.assertNotNull(user);
        Assert.assertEquals("toto@docs.com", user.getEmail());

        // Authenticate using the database
        Assert.assertNotNull(new InternalAuthenticationHandler().authenticate("username", "12345678"));
    }

    @Test
    public void testJpa2() throws Exception {
        // Create a user
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("username");
        user.setPassword("12345678");
        user.setEmail("fsdf@docs.com");
        user.setRoleId("admin");
        user.setStorageQuota(10L);
        String id = userDao.create(user, "me");

        TransactionUtil.commit();

        user = userDao.getById(id);
        Assert.assertNotNull(user);
        Assert.assertEquals("fsdf@docs.com", user.getEmail());
        Assert.assertEquals("admin", user.getRoleId());
        Assert.assertEquals("12345678", user.getPassword());

        userDao.updatePassword(user, "87654321");

        TransactionUtil.commit();

        user = userDao.getById(id);
        Assert.assertNotNull(user);
        Assert.assertEquals("87654321", user.getPassword());


        // Authenticate using the database
        Assert.assertNotNull(new InternalAuthenticationHandler().authenticate("username", "87654321"));
    }
}
