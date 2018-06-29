package br.com.fabiogodoy.oauth2.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.fabiogodoy.oauth2.domain.Response;
import br.com.fabiogodoy.oauth2.domain.User;

public interface IUserService extends UserDetailsService {
	
	Response register(User user);

}
