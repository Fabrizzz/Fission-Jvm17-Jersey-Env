package io.fission;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.jetty.webapp.WebAppContext;

import static io.fission.Main.hotSwapHandler;


@Path("specialize")
public class RequestHandler {
    @Consumes("application/json")
    @POST
    public Response specialize(FunctionLoadRequest request) {
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setWar(request.getFilepath());
        webAppContext.setExtractWAR(true);
        try {
            hotSwapHandler.setHandler(webAppContext);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        if (!webAppContext.isAvailable()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().build();
    }
}
