package co.diegofer.inventoryclean.api;


import co.diegofer.inventoryclean.model.user.AuthRequest;
import co.diegofer.inventoryclean.model.user.AuthResponse;
import co.diegofer.inventoryclean.usecase.service.loginuser.LoginUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {



    @Bean
    @RouterOperation(path = "/api/v1/auth/login", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = LoginUserUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "loginUser", tags = "Auth usecases",
                    parameters = {
                            @Parameter(name = "item", in = ParameterIn.PATH, schema = @Schema(implementation = AuthRequest.class))
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                            @ApiResponse(responseCode = "401", description = "Unauthorized user")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = AuthRequest.class)))))
    public RouterFunction<ServerResponse> loginUser(Handler handler){
        return route(POST("api/v1/auth/login").and(accept(MediaType.APPLICATION_JSON)),
                handler::listenPOSTLoginUser);
    }


}
