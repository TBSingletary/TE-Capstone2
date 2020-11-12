package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.User;

public interface UserDAO {

    List<User> findAll();
    
    User findByUsername(String username);    

    int findIdByUsername(String username);

    boolean create(String username, String password);
        
}
