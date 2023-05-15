package io.fission;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HotSwapHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final int DEFAULT_PORT = 8888;

	static HotSwapHandler hotSwapHandler;
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public Main(int serverPort) throws Exception {
		Server server = new Server(serverPort);

		ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
		server.setHandler(contextHandlerCollection);

		hotSwapHandler = new HotSwapHandler();
		ContextHandler contextHandler = new ContextHandler();
		contextHandler.setHandler(new DefaultHandler());
		contextHandler.setContextPath("/");
		hotSwapHandler.setHandler(contextHandler);
		contextHandlerCollection.addHandler(hotSwapHandler);

		ResourceConfig config = new ResourceConfig();
		config.register(RequestHandler.class);
		// Create a ServletContainer with the ResourceConfig
		ServletContainer jerseyServletContainer = new ServletContainer(config);
		// Create a ServletHolder with the ServletContainer
		ServletHolder servletHolder = new ServletHolder(jerseyServletContainer);
		// Create a new ServletContextHandler and add the ServletHolder to it
		ServletContextHandler jettyServletContext = new ServletContextHandler();
		jettyServletContext.setContextPath("/v2"); // Set the context path for the Jersey resource
		jettyServletContext.addServlet(servletHolder, "/*");
		// Add the new ServletContextHandler to the ContextHandlerCollection
		contextHandlerCollection.addHandler(jettyServletContext);

		// Start the server
		server.start();
		logger.info(server.getURI().toString());
		Handler[] handlers = contextHandlerCollection.getHandlers();
		for (Handler handler : handlers) {
			if (handler instanceof ContextHandler contextHandle) {
				String contextPath = contextHandle.getContextPath();
				logger.info("Entry point: " + contextPath);
			}
		}
	}


	public static void main(String[] args) throws Exception {

		int serverPort = DEFAULT_PORT;

		if(args.length >= 1) {
			try {
				serverPort = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		new Main(serverPort);
	}
}
