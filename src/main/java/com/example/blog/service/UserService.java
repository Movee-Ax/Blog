package com.example.blog.service;

import com.example.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
