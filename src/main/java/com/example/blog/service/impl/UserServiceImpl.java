package com.example.blog.service.impl;


import com.example.blog.dao.UserRepository;
import com.example.blog.po.User;
import com.example.blog.service.UserService;
import com.example.blog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private static final String salt="m0ov3e22e";
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Util.inputPassToDBPass(password, salt));
        return user;
    }
}
