package com.medilabo.gateway.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.tinylog.Logger;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class AuthManager implements ReactiveAuthenticationManager
{
	@Override
	public Mono<Authentication> authenticate(Authentication authentication)
	{
		final String name = authentication.getName();
		final String password = authentication.getCredentials().toString();
		if (("user".equals(name) && "password".equals(password)))
		{
			Logger.info("Credentials : " + name + ", " + password);
			return Mono.just(new UsernamePasswordAuthenticationToken(name, password, Collections.emptyList()));
		}
		return null;
	}
}
