package com.developer.journalApp.controller;

import com.developer.journalApp.api.response.WeatherResponse;
import com.developer.journalApp.entity.JournalEntry;
import com.developer.journalApp.entity.User;
import com.developer.journalApp.repository.JournalEntryRepository;
import com.developer.journalApp.repository.UserRepository;
import com.developer.journalApp.service.UserService;
import com.developer.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        journalEntryRepository.deleteAll(journalEntries);
        userRepository.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        WeatherResponse weatherResponse = weatherService.getWeather("Yamunanagar");
        String greeting = "";
        if(weatherResponse != null)
            greeting = "Today temperature feels like : " + weatherResponse.getCurrent().getFeelsLike();
        return new ResponseEntity<>("Hi " + userName + ", " + greeting, HttpStatus.OK);
    }
}
