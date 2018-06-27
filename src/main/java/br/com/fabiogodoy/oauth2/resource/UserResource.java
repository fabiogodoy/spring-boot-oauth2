package br.com.fabiogodoy.oauth2.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabiogodoy.oauth2.domain.User;
import br.com.fabiogodoy.oauth2.repository.UserRepository;

@RestController
@RequestMapping(path = "/api-v1/user")
public class UserResource {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping
	@RequestMapping(path="/")
	public ResponseEntity<User> register(@RequestBody User user) {
		ResponseEntity<User> response = null;
		final Optional<User> found = this.repository.findByUsername(user.getUsername());
		
		if (!found.isPresent()) {
			user.setPassword(encoder.encode(user.getPassword()));
			this.repository.save(user);
			response = ResponseEntity.ok(user);
		} else {
			response = ResponseEntity.noContent().build();
		}
		
		return response;
		
	}

}
