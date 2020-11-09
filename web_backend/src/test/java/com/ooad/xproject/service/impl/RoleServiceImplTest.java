package com.ooad.xproject.service.impl;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.junit.Test;

public class RoleServiceImplTest {

    @Test
    public void testSalt() {
        System.out.println(new SecureRandomNumberGenerator().nextBytes().toString());
    }

}