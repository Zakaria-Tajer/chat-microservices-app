package com.chat.communication.user.security;

import com.chat.clients.AuthenticationServiceClient;
import com.chat.clients.TokenIsValidDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@EnableFeignClients(basePackages = "com.chat.clients")
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final AuthenticationServiceClient authenticationServiceClient;
    private final String SECRET_KEY = "2A462D4A614E645267556A586E3272357538782F413F4428472B4B6250655368";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);

            return;
        }


        jwt = authHeader.substring(7);

        TokenIsValidDto tokenIsValid = authenticationServiceClient.verifyToken(authHeader);

        if(SecurityContextHolder.getContext().getAuthentication() == null){

            if(tokenIsValid.isValid()){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        null,
                        null,
                        List.of(new SimpleGrantedAuthority(tokenIsValid.getRole().get(0)))
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}