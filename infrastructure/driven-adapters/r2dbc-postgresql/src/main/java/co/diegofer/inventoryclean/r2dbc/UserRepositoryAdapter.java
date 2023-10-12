package co.diegofer.inventoryclean.r2dbc;

import co.diegofer.inventoryclean.model.user.User;
import co.diegofer.inventoryclean.model.user.gateways.UserRepository;
import co.diegofer.inventoryclean.r2dbc.config.JwtService;
import co.diegofer.inventoryclean.model.user.AuthRequest;
import co.diegofer.inventoryclean.model.user.AuthResponse;
import co.diegofer.inventoryclean.r2dbc.data.UserData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final ObjectMapper mapper;

    private final UserRepositoryR2dbc userRepositoryR2dbc;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ReactiveAuthenticationManager authManager;

    @Override
    public Mono<User> saveAUser(User user) {
        //String newId = UUID.randomUUID().toString();
        UserData userData = mapper.map(user, UserData.class);
        //userData.setId(newId);
        System.out.println(userData.getId());
        return userRepositoryR2dbc.saveUser(userData.getId(),userData.getName(),userData.getLastName(),
                passwordEncoder.encode(userData.getPassword()),userData.getEmail(),
                userData.getRole(),userData.getBranchId()).thenReturn(
                mapper.map(userData, User.class)
        ).onErrorMap(DataIntegrityViolationException.class, e -> new DataIntegrityViolationException("Error creating user: "+e.getMessage()));

    }

    @Override
    public Mono<User> saveASuper(User user) {

        user.setId(UUID.randomUUID().toString());
        UserData userData = mapper.map(user, UserData.class);

        return userRepositoryR2dbc.saveUser(userData.getId(),userData.getName(),userData.getLastName(),
                passwordEncoder.encode(userData.getPassword()),userData.getEmail(),
                userData.getRole(),null).thenReturn(
                mapper.map(userData, User.class)
        ).onErrorMap(DataIntegrityViolationException.class, e -> new DataIntegrityViolationException("Error creating user: "+e.getMessage()));
    }

    public Mono<AuthResponse> authenticate(AuthRequest request) {
        return authManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()))
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .map(auth -> {
                    var userDetails = (UserDetails) auth.getPrincipal();
                    return getAuthResponse(userDetails);
                });
    }

    private AuthResponse getAuthResponse(UserDetails userDetails) {
        var extraClaims = extractAuthorities("roles", userDetails);

        var jwtToken = jwtService.generateToken(userDetails, extraClaims);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    private Map<String, Object> extractAuthorities(String key, UserDetails userDetails) {
        Map<String, Object> authorities = new HashMap<>();

        authorities.put(key,
                userDetails
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")));

        return authorities;
    }


}
