package br.com.fabiogodoy.oauth2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.fabiogodoy.oauth2.domain.Response;
import br.com.fabiogodoy.oauth2.domain.User;
import br.com.fabiogodoy.oauth2.repository.UserRepository;
import br.com.fabiogodoy.oauth2.validation.FieldConstants;
import br.com.fabiogodoy.oauth2.validation.ValidationConstants;

@Service
public class UserService extends AbstractService implements IUserService {

	private static final String REGISTER_USER_ERROR = "user.general.error";

	private static final String REGISTER_USER_SUCESS = "user.register.success";

	private static final String USER_ALREADY_EXISTS_ERROR = "user.already.exists.error";
	
	private static final String USER_NOTFOUND = "user.notfound";
	
	private final Pattern emailPattern;

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public UserService() {
		this.emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER")))
                .orElseThrow(() -> new UsernameNotFoundException(super.getMessage(USER_NOTFOUND, username)));
    }

	@Override
	public Response register(User user) {
		Response response = null;
		try {
			final List<String> messages = this.validate(user);
			
			if(messages.size() == 0x0) {
				
				final Optional<User> found = this.userRepository.findByUsername(user.getUsername());
				
				if (!found.isPresent()) {
					user.setPassword(this.encoder.encode(user.getPassword()));
					this.userRepository.save(user);
					response = Response.ok(user, super.getMessage(REGISTER_USER_SUCESS));
				} else {
					response = Response.fail(user, super.getMessage(USER_ALREADY_EXISTS_ERROR, user.getUsername()));
				}
				
			} else {
				final String[] messagesArr = new String[messages.size()];
				response = Response.fail(user, messages.toArray(messagesArr));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.fail(user, super.getMessage(REGISTER_USER_ERROR));
		}
		
		return response;
	}
	
	private List<String> validate(final User user){
		final List<String> messages = new ArrayList<>();
		
		if (StringUtils.isEmpty(user.getName())) {
			final String usernameField = super.getMessage(FieldConstants.USERNAME_FIELD);
			messages.add(super.getMessage(ValidationConstants.NOT_NULL_FIELD, usernameField));
		}
		
		if (StringUtils.isEmpty(user.getPassword())) {
			final String passwordField = super.getMessage(FieldConstants.PASSWORD_FIELD);
			messages.add(super.getMessage(ValidationConstants.NOT_NULL_FIELD, passwordField));
		}
		
		if (!StringUtils.isEmpty(user.getUsername())) {
			if (!emailPattern.matcher(user.getUsername()).matches()) {
				messages.add(super.getMessage(ValidationConstants.INVALID_EMAIL, user.getUsername()));
			}
		} else {
			messages.add(super.getMessage(ValidationConstants.NOT_NULL_FIELD, user.getUsername()));
		}
		
		return messages;
	}
    
}
