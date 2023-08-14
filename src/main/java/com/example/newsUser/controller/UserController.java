package com.example.newsUser.controller;

import com.example.newsUser.contract.CreateUserRequest;
import com.example.newsUser.contract.CreateUserResponse;
import com.example.newsUser.contract.EmailRequest;
import com.example.newsUser.service.EmailService;
import com.example.newsUser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest){

        return Optional
                .ofNullable(userService.createUser(createUserRequest))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }



    @GetMapping("/news")
    public ResponseEntity<?> getNews(@RequestParam("uid") Long uid){
        return Optional
                .ofNullable(userService.getNews(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/newsLimited")
    public ResponseEntity<?> getNews(@RequestParam("uid") Long uid, @RequestParam("max_articles") Integer max_articles){
        return Optional
                .ofNullable(userService.getNewsLimited(uid, max_articles))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/sources")
    public ResponseEntity<?> getSources(@RequestParam("uid") Long uid){
        return Optional
                .ofNullable(userService.getSources(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/addPrefSource")
    public ResponseEntity<?> addSource(@RequestParam("uid") Long uid, @RequestParam("source") String source){
        return Optional
                .ofNullable(userService.addPrefSource(uid, source))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping ("/sourceBasedNews")
    public ResponseEntity<?> sourceBasedNews(@RequestParam("uid") Long uid){
            return Optional
                .ofNullable(userService.sourceBasedNews(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
//
    @GetMapping ("/newsByDate")
    public ResponseEntity<?> newsByDate(@RequestParam("uid") Long uid , @RequestParam("from") String from, @RequestParam("to") String to){
        return Optional
                .ofNullable(userService.newsByDate(uid, from, to))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//        return userRepository.newsByDate(from, to);
    }

    @PostMapping("/sendNewsByEmail")
    public ResponseEntity<?> sendNewsByEmail(@RequestParam("uid") Long uid) {
        return Optional
                .ofNullable(userService.sendNewsByEmail(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam("uid") Long uid) {
        return Optional
                .ofNullable(userService.subscribe(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/unsubscribe")
        public ResponseEntity<?> unsubscribe(@RequestParam("uid") Long uid) {
        return Optional
                .ofNullable(userService.unsubscribe(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


}
