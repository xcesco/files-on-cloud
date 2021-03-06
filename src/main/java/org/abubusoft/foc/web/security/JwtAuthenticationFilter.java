package org.abubusoft.foc.web.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.abubusoft.foc.web.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Value("${jwt.header}")
	private String tokenHeader;

	private static final String BEARER = "Bearer";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String authToken = Optional.ofNullable(request.getHeader(this.tokenHeader))
				.map(v -> v.replace(BEARER, "").trim()).orElse(null);

		UserDetails userDetails = null;

		if (authToken != null) {
			userDetails = jwtService.getUserDetails(authToken);
		}

		if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// Ricostruisco l userdetails con i dati contenuti nel token

			// controllo integrita' token
			if (jwtService.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}
}