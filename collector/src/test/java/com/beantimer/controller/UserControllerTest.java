package com.beantimer.controller;


import com.beantimer.SpringContextTest;
import com.beantimer.entity.User;
import com.beantimer.repo.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *
 */
public class UserControllerTest extends SpringContextTest {


    public static final String USER_NAME = "username";

    private User user;

    @Autowired
    private UserRepository userRepository;


    @Before
    public void setup() throws Exception {
        super.setUp();

        user = new User(USER_NAME);
        user = userRepository.save(user);
    }

    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(USER_NAME)));
    }


}