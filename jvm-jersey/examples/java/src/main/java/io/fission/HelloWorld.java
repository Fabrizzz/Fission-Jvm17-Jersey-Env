package io.fission;

import application.Function;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Path("")
public class HelloWorld extends Function {

    public static final String RETURN_STRING = "Hello World!";

    @Override
    public Response call(ContainerRequestContext request) {
        if (request.getMethod().equals("GET")) {
            return Response.ok(RETURN_STRING).build();
        } else {
            String body = new BufferedReader(new InputStreamReader(request.getEntityStream())).lines()
                    .parallel().collect(Collectors.joining("\n"));
            return Response.ok(body).build();
        }
    }
}
