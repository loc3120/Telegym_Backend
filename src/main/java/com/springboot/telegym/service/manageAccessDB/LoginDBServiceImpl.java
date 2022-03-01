package com.springboot.telegym.service.manageAccessDB;

import com.springboot.telegym.dao.manageAccessDB.LoginDBDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Transactional
@RequiredArgsConstructor
public class LoginDBServiceImpl implements LoginDBService{

    private final LoginDBDao loginDBDao;

    @Override
    public void loginDataBase(String str1, String str2) {
        loginDBDao.loginDataBase(str1, str2);
    }
}
