package com.techelevator.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.security.Principal;

public class FavoriteController {

    //favorite a post
    @PostMapping("/favorite")
    public boolean favoritePost(Principal principal, @RequestParam("postId") int postId) {
//        int currentUser = userDao.principal.getName()


    }

    //list of favorite by userId (Principal principal)




}
