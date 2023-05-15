package application;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;


public abstract class Function {
    @Inject
    private ContainerRequestContext request;


    public abstract Response call(ContainerRequestContext request);

    @GET
    public Response get() {
        return call(request);
    }

    @PUT
    public Response put() {
        return call(request);
    }

    @DELETE
    public Response delete() {
        return call(request);
    }

    @POST
    public Response post() {
        return call(request);
    }
}
