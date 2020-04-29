package com.learning.securityservice.security;

import com.learning.securityservice.model.User;
import com.learning.securityservice.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service   // It has to be annotated with @Service.
public class UserDetailsServiceImpl implements UserDetailsService  {
	
	@Setter(onMethod = @__(@Autowired))
	private BCryptPasswordEncoder encoder;

	@Setter(onMethod = @__(@Autowired))
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Username: " + username + " not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), encoder.encode(user.getPassword()),
				AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
	}
}
