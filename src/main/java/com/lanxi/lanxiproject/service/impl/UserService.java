package com.lanxi.lanxiproject.service.impl;

import com.lanxi.lanxiproject.dao.UserMapper;
import com.lanxi.lanxiproject.entity.User;
import com.lanxi.lanxiproject.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by summer on 16/4/11.
 */
@Service
public class UserService implements IUserService{

    @Autowired
    private UserMapper userDao;

    private static Logger logger = Logger.getLogger(UserService.class);


    @Override
    public User getUserById(int id) {
        User user = null;
        try {
            user  = userDao.selectByPrimaryKey(id);
        }catch (Exception e) {
            logger.info(e);
        }



        return user;

    }
}
