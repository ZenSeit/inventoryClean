package co.diegofer.inventoryclean.api;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {



    @Bean
    public RouterFunction<ServerResponse> loginUser(Handler handler){
        return route(POST("api/v1/auth/login").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTLoginUser);
    }


}
