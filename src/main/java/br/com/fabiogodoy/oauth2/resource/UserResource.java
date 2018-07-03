package br.com.fabiogodoy.oauth2.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabiogodoy.oauth2.domain.Response;
import br.com.fabiogodoy.oauth2.domain.User;
import br.com.fabiogodoy.oauth2.service.UserService;

@RestController
@RequestMapping(path = "/api-v1/user")
public class UserResource {
	
	@Autowired
	UserService service;
	
	@PostMapping
	@RequestMapping(path="/")
	public ResponseEntity<Response> register(@RequestBody User user) {
		
		return ResponseEntity.ok(this.service.register(user));
		
	}

}
