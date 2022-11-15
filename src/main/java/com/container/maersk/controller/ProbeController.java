package com.container.maersk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ProbeController {
    @GetMapping("/live")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity live() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/ready")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity ready() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
