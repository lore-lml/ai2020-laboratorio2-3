package it.ai.polito.lab2.security.filters;

import it.ai.polito.lab2.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        //try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
            if (token != null && jwtTokenProvider.validateToken(token, (HttpServletRequest) req)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        /*}catch (AuthenticationException e){
            ((HttpServletResponse)res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid/Expired JWT: please create a valid one at /authenticate");
            throw e;
        }*/

        filterChain.doFilter(req, res);
    }
}
