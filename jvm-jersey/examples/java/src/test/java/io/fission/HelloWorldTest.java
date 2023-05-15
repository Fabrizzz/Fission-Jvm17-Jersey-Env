package io.fission;


import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HelloWorldTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(HelloWorld.class);
    }

    @Test
    public void testGet() {
        try {
            var hello = target().request().get(String.class);
            assertEquals(hello, "Hello World!");
        } catch (jakarta.ws.rs.client.ResponseProcessingException e) {
            e.printStackTrace();
            org.junit.jupiter.api.Assertions.fail();
        }
    }

    @Test
    public void testPost() {
        String requestBody = "{\"message\":\"Hello\nWorld!\"}";
        try (Response response = target().request().post(Entity.entity(requestBody, MediaType.TEXT_PLAIN));){
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(requestBody, response.readEntity(String.class));
        } catch (jakarta.ws.rs.client.ResponseProcessingException e) {
            e.printStackTrace();
            org.junit.jupiter.api.Assertions.fail();
        }
    }
}
/*
@Test
	public void testResponse() {
		HelloWorld hw = new HelloWorld();
		ContainerRequest request = null;
		try {
			request = new ContainerRequest(new URI("http://example.com/"),new URI("/hello"),"GET",null,null);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Response response = hw.call(request, null);
		Assert.assertTrue(response.getEntity().toString().equals(HelloWorld.RETURN_STRING));
	}
 */
