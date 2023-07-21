package com.chat.communication.services;

import com.chat.communication.Responses.AuthResponse;
import com.chat.communication.Responses.data.AuthResponseData;
import com.chat.communication.Responses.data.AuthResponseDefault;
import com.chat.communication.Responses.data.AuthResponseErrors;
import com.chat.communication.Responses.data.AuthResponseLoginData;
import com.chat.communication.domains.User;
import com.chat.communication.dto.UserDto;
import com.chat.communication.dto.UserLoginDto;
import com.chat.communication.mappers.UserMapper;
import com.chat.communication.repository.UserRespository;
import com.chat.communication.security.ApplicationConfig;
import com.chat.communication.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {


    private final UserRespository userRespository;
    private final ApplicationConfig applicationConfig;
    private final JwtService jwtService;
    private final AuthResponse authResponse;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseData registerUser(UserDto userDto) {

        Optional<User> userExist =  userRespository.findUserByEmail(userDto.getEmail());

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

        if(user.isPresent() && !applicationConfig.passwordEncoder().matches(loginDto.getPassword(), user.get().getPassword())) {
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

            return  authResponse.loginResponse(
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
}
