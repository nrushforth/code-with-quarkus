package org.acme;

import com.google.cloud.spanner.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SpannerService {

    private Spanner spanner;
    private DatabaseClient dbClient;

    public DatabaseClient getDbClient() {
        return dbClient;
    }

    @PostConstruct
    public void init() {

        SpannerOptions options = SpannerOptions.newBuilder().setEmulatorHost(System.getenv("SPANNER_EMULATOR_HOST"))
                .setProjectId("banking-project")
                .build();

        spanner = options.getService();

        InstanceId instanceId = InstanceId.of("banking-project", "banking-instance");
        DatabaseId dbId= DatabaseId.of(instanceId,"banking-database");

        dbClient = spanner.getDatabaseClient(dbId);
    }
}
