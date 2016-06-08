package com.lanxi.lanxiproject.service;

import com.lanxi.lanxiproject.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by summer on 16/6/7.
 */
@Service
public interface IUserService {
    User getUserById(int id);
}
