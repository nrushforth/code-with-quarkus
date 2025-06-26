package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import com.google.cloud.spanner.*;

@Path("/spanner")
public class SpannerResource {
    private static final Logger LOG = Logger.getLogger(SpannerResource.class);

    @Inject
    SpannerService spannerService;// Inject Spanner

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String spanner() {
        // Create a database client
        var dbClient = spannerService.getDbClient();

        // Insert 4 singer records
        dbClient.readWriteTransaction().run(transaction -> {
            String sql = "INSERT INTO Customers (CustomerId, Name) VALUES "
                    + "(1, 'Melissa Garcia'), "
                    + "(2, 'Russell Morales'), "
                    + "(3, 'Jacqueline Long'), "
                    + "(4, 'Dylan Shaw')";
            long rowCount = transaction.executeUpdate(Statement.of(sql));
            LOG.infov("{0} records inserted.", rowCount);
            return null;
        });

        // Read them
        try (ResultSet resultSet = dbClient.singleUse() // Execute a single read or query against Cloud Spanner.
                .executeQuery(Statement.of("SELECT CustomerId, Name FROM Customers"))) {
            StringBuilder builder = new StringBuilder();
            while (resultSet.next()) {
                builder.append(resultSet.getLong(0)).append(' ').append(resultSet.getString(1)).append('\n');
            }
            return builder.toString();
        }
    }

}