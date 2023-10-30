package com.example.shamanApi.controler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    @GetMapping("/activity")
    public ResponseEntity CheckActivity(){
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/islogged")
    public ResponseEntity IsLogged(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
