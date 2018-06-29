package br.com.fabiogodoy.oauth2.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fabiogodoy.oauth2.domain.Response;
import br.com.fabiogodoy.oauth2.domain.User;
import br.com.fabiogodoy.oauth2.repository.UserRepository;

@Service
public class UserService extends AbstractService implements IUserService {

	private static final String REGISTER_USER_ERROR = "user.general.error";

	private static final String REGISTER_USER_SUCESS = "user.register.success";

	private static final String USER_ALREADY_EXISTS_ERROR = "user.already.exists.error";

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER")))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
    }

	@Override
	public Response register(User user) {
		Response response = null;
		try {
			final Optional<User> found = this.userRepository.findByUsername(user.getUsername());
			
			if (!found.isPresent()) {
				user.setPassword(this.encoder.encode(user.getPassword()));
				this.userRepository.save(user);
				response = Response.ok(user, super.getMessage(REGISTER_USER_SUCESS));
			} else {
				response = Response.fail(user, super.getMessage(USER_ALREADY_EXISTS_ERROR, user.getUsername()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.fail(user, super.getMessage(REGISTER_USER_ERROR));
		}
		
		return response;
	}
    
}
