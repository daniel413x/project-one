package com.warehouse.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.utils.DatabaseUtil;

@CrossOrigin
@RestController
@RequestMapping("/api/testing")
public class TestingController {

    final private DatabaseUtil databaseUtil;

    public TestingController(DatabaseUtil databaseUtil) {
        this.databaseUtil = databaseUtil;
    }

    @PostMapping("/reset")
    @ResponseStatus(code = HttpStatus.OK)
    public void reset() {
        this.databaseUtil.clearDatabase();
        this.databaseUtil.seedTheDatabase();
    }

    @PostMapping("/clear")
    @ResponseStatus(code = HttpStatus.OK)
    public void clear() {
        this.databaseUtil.clearDatabase();
    }

    @PostMapping("/seed")
    @ResponseStatus(code = HttpStatus.OK)
    public void seed() {
        this.databaseUtil.seedTheDatabase();
    }
}
