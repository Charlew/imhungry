package pl.codzisnaobiad.imhungry.config

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.GenericContainer

@Configuration
class MongoTestConfiguration {
    private static final String IMAGE = "mongo:3.4"
    private static final String MONGODB_PREFIX = "mongodb://"

    private final int mongoPort

    MongoTestConfiguration(@Value('\${mongo.integration.port}') int mongoPort) {
        this.mongoPort = mongoPort
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    GenericContainer mongoContainer() {
        return new GenericContainer(IMAGE)
            .withExposedPorts(mongoPort)
    }

    @Bean
    MongoClient mongoClient(GenericContainer mongoContainer) {
        return MongoClients.create(
            "${MONGODB_PREFIX + mongoContainer.containerIpAddress}:${mongoContainer.getMappedPort(mongoPort)}"
        )
    }
}
