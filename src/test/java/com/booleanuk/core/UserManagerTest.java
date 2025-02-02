package com.booleanuk.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserManagerTest {

    UserManager userManager;

    @BeforeEach
    public void setup(){
        userManager = new UserManager();
    }

    @Test
    public void testValidEmailAndPassword() {
        Assertions.assertTrue(userManager.createUser("mpampis@app.com", "123456789"));
        Assertions.assertFalse(userManager.createUser("app.com", "123456789"));
        Assertions.assertFalse(userManager.createUser("mpampis@app.com", "1234567"));
    }

    @Test
    public void testAccountDisabledByDefault() {
        userManager.createUser("mpampis@app.com", "123456789");
        Assertions.assertFalse(userManager.getUsers().get(0).isEnabled());
    }

    @Test
    public void testAccountEnabledState() {
        userManager.createUser("mpampis@app.com", "123456789");
        Assertions.assertFalse(userManager.canLogin("mpampis@app.com"));

        userManager.getUsers().get(0).setEnabled(true);
        Assertions.assertTrue(userManager.canLogin("mpampis@app.com"));

        Assertions.assertFalse(userManager.canLogin("kwstis@app.com"));
    }

    @Test
    public void testLogin(){
        userManager.createUser("mpampis@app.com", "123456789");
        User mpampis = userManager.getUsers().get(0);

        Assertions.assertNull(userManager.login("nonexisting@app.com", "pass1234"));
        Assertions.assertNull(userManager.login(mpampis.getEmail(), "wrongpass"));

        Assertions.assertNull(userManager.login(mpampis.getEmail(), mpampis.getPassword()));

        mpampis.setEnabled(true);
        Assertions.assertEquals(mpampis, userManager.login(mpampis.getEmail(), mpampis.getPassword()));
    }
}
