package com.chat.communication.services;

import com.chat.communication.Responses.AuthResponse;
import com.chat.communication.Responses.data.AuthResponseData;
import com.chat.communication.Responses.data.AuthResponseDefault;
import com.chat.communication.Responses.data.AuthResponseErrors;
import com.chat.communication.Responses.data.AuthResponseLoginData;
import com.chat.communication.domains.User;
import com.chat.communication.dto.TokenIsValidDto;
import com.chat.communication.dto.UserDto;
import com.chat.communication.dto.UserLoginDto;
import com.chat.communication.mappers.UserMapper;
import com.chat.communication.repository.UserRespository;
import com.chat.communication.security.ApplicationConfig;
import com.chat.communication.security.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {


    private final UserRespository userRespository;
    private final ApplicationConfig applicationConfig;
    private final JwtService jwtService;
    private final AuthResponse authResponse;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponseData registerUser(UserDto userDto) {

        Optional<User> userExist = userRespository.findUserByEmail(userDto.getEmail());

        userExist.ifPresent(user -> authResponse.authResponseErrors(
                new AuthResponseErrors(
                        user.getEmail(),
                        Date.from(Instant.now()),
                        400,
                        "This email is already used"
                )
        ));

        userDto.setPassword(applicationConfig.passwordEncoder().encode(userDto.getPassword()));


        User user = UserMapper.INSTANCE.userDtoToUser(userDto);
        userRespository.save(user);

        log.info("New User Saved");

//        ! should add a checker if the mapping has data


        return authResponse.registerResponse(
                new AuthResponseData(
                        Date.from(Instant.now()),
                        200,
                        "success",
                        UserMapper.INSTANCE.userToUserDto(user)
                )
        );
    }

    @Override
    public Object loginUser(UserDto loginDto) {
        if (loginDto.getEmail() == null) {

            log.info("This email is already used");
            return authResponse.authResponseDefaultError(
                    new AuthResponseDefault(
                            Date.from(Instant.now()),
                            400,
                            "This email is already used"
                    )
            );
        }


        Optional<User> user = userRespository.findUserByEmail(loginDto.getEmail());


        if (user.isEmpty()) {
            log.info("No user with this email");

            authResponse.authResponseDefaultError(
                    new AuthResponseDefault(
                            Date.from(Instant.now()),
                            400,
                            "No user with this email"
                    )
            );
        }

        if (user.isPresent() && !applicationConfig.passwordEncoder().matches(loginDto.getPassword(), user.get().getPassword())) {
            log.info("Passwords do not match");

            authResponse.authResponseDefaultError(
                    new AuthResponseDefault(
                            Date.from(Instant.now()),
                            400,
                            "Passwords do not match"
                    )
            );

        }

        try {
            // Try to authenticate the user using the provided email and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );

            User authenticatedUser = (User) authentication.getPrincipal();

            // Generate the JWT token and return it, or handle the successful login as needed.
            String jwtToken = jwtService.generateToken(authenticatedUser);
            UserLoginDto userLoginDto = UserMapper.INSTANCE.userToUserLoginDto(authenticatedUser);

            log.info("user {} logged", authenticatedUser.getEmail());

            return authResponse.loginResponse(
                    new AuthResponseLoginData(
                            Date.from(Instant.now()),
                            200,
                            "Logged",
                            jwtToken,
                            userLoginDto
                    )
            );


        } catch (AuthenticationException ex) {

            return authResponse.authResponseDefaultError(
                    new AuthResponseDefault(
                            Date.from(Instant.now()),
                            401,
                            String.format("Invalid credentials {}", ex.getMessage())
                    )
            );
        }


    }

    @Override
    public TokenIsValidDto verifyToken(String authorizationHeader) {


        log.info("Verifying token");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            String jwtToken = authorizationHeader.substring(7);



            Claims claims = jwtService.extractAllClaims(jwtToken);

            List<String> roles = claims.get("roles", List.class);

            List<String> authorities = roles.stream()
                    .map(role -> "ROLE_" + role)
                    .collect(Collectors.toList());






            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractUsername(jwtToken));
            log.info("Token of {}", userDetails.getUsername());

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                log.info("Token is valid {}", true);
                return new TokenIsValidDto(
                        true,
                        authorities
                );
            }

            log.info("Token is non-valid {}", false);
            return new TokenIsValidDto(
                    false,
                    null
            );


        }

        return new TokenIsValidDto(
                false,
                null
        );

    }


}
