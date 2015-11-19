package example;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Chris on 11/19/2015.
 */


@Path("/climbingserver")
public class ClimbingServer {

    private static String DB_URI = "jdbc:postgresql://localhost:5433/ClimbingLog";
    private static String DB_LOGINID = "postgres";
    private static String DB_PASSWORD = "LegendOfZelda567890";

    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hey, World!";
    }

    @GET
    @Path("/climbs/")
    @Produces("text/plain")
    public String getClimbs() {
        String result = "";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URI, DB_LOGINID, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Climb");
            if (resultSet.next()) {
                result += resultSet.getInt(1) + " " + resultSet.getString(3) + " " + resultSet.getString(2) + "\n";
                while(resultSet.next()) {
                    result += resultSet.getInt(1) + " " + resultSet.getString(3) + " " + resultSet.getString(2) + "\n";
                }
            } else {
                result = "nothing found...";
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            result = e.getMessage().toString();
        }
        return result;
    }

    @GET
    @Path("/climbers/")
    @Produces("text/plain")
    public String getClimbers() {
        String result = "";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URI, DB_LOGINID, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Climber");
            if (resultSet.next()) {
                result += resultSet.getString(1) + " " + resultSet.getString(3) + " " + resultSet.getString(4) + "\n";
                while(resultSet.next()) {
                    result += resultSet.getString(1) + " " + resultSet.getString(3) + " " + resultSet.getString(4) + "\n";
                }
            } else {
                result = "nothing found...";
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            result = e.getMessage().toString();
        }
        return result;
    }

    @GET
    @Path("/climber/{id}")
    @Produces("text/plain")
    public String getClimber(@PathParam("id") String id) {
        String result = "";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URI, DB_LOGINID, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Climber WHERE id = '" + id + "'");
            if (resultSet.next()) {
                result = resultSet.getString(1) + " " + resultSet.getString(3) + " " + resultSet.getString(4) + "\n";
            } else {
                result = "nothing found...";
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            result = e.getMessage().toString();
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9998/climbingserver");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }
}
