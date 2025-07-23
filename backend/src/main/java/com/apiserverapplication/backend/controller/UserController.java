package com.apiserverapplication.backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiserverapplication.backend.model.AuthRequest;
import com.apiserverapplication.backend.service.AuthService;

@RestController
@RequestMapping("/api")
public class UserController {

	 @Autowired
	    private AuthService authService;

	    @PostMapping("/register")
	    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
	        return authService.register(request);
	    }

	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
	        return authService.login(request);
	    }

}
