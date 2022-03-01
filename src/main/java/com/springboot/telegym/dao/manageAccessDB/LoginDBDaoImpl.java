package com.springboot.telegym.dao.manageAccessDB;

import com.springboot.telegym.security.DatabaseConfig;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class LoginDBDaoImpl implements LoginDBDao{

    @Override
    public void loginDataBase(String str1, String str2) {
        DatabaseConfig.loginName = str1;
        DatabaseConfig.password = str2;
    }
}
