package eci.ieti.mongodb.controller.Auth;

import eci.ieti.mongodb.repository.User.User;
import eci.ieti.mongodb.security.encrypt.PasswordEncryptionService;
import eci.ieti.mongodb.service.User.UserServiceMongoDb;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RequestMapping("v1/auth")
public class AuthController {

    private final UserServiceMongoDb usersService;

    private PasswordEncryptionService passwordEncryptionService;

    @Value("${jwt.secret}")
    String secret;

    public AuthController(@Autowired UserServiceMongoDb usersService, @Autowired PasswordEncryptionService passwordEncryptionService) {
        this.usersService = usersService;
        this.passwordEncryptionService = passwordEncryptionService;
    }

    @PostMapping
    public TokenDto login(@RequestBody LoginDto loginDto) {
        Optional<User> optionalUser = usersService.(loginDto.email());
        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        User user = optionalUser.get();

        if (passwordEncryptionService.isPasswordMatch(loginDto.password(), user.getEncryptedPassword())) {
            return generateTokenDto(user);
        } else {
            throw new InvalidCredentialsException();
        }

    }


    private String generateToken(User user, Date expirationDate) {
        return Jwts.builder()
                .setSubject(user.getId()).claim(CLAIMS_ROLES_KEY, user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private TokenDto generateTokenDto(User user) {
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.MINUTE, TOKEN_DURATION_MINUTES);
        String token = generateToken(user, expirationDate.getTime());
        return new TokenDto(token, expirationDate.getTime());
    }
}
