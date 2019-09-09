import java.io.IOException;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

class Main {

    private static HttpServer startServer(String address) {
        URI serverUri = UriBuilder.fromPath("http://"+address).build();

        ResourceConfig rc = ResourceConfig.forApplication(new Server());
        System.out.println(serverUri);
        return GrizzlyHttpServerFactory.createHttpServer(serverUri, rc);
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println(System.getProperty("sun.java.command") + " address:port");
    }

    public static void main(String[] args) {
        System.out.println("Starting server...\npress enter to terminate");
        if (args.length > 0) {
            HttpServer httpServer = startServer(args[0]);
            try {
                System.in.read();
            } catch (IOException ex) {
                System.err.println("IOException: "+ex);
            }
            httpServer.shutdownNow();
        } else {
            printUsage();
        }
    }
}
