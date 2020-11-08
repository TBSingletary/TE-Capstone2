package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface UserDAO {

    List<User> findAll();
    
    List<User> findOtherUsers(Principal principal);

    User findByUsername(String username);    

    int findIdByUsername(String username);

    boolean create(String username, String password);
    
    public BigDecimal getUserBalance(String username);
}
