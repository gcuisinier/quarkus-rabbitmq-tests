package org.soframel;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@QuarkusTestResource(RabbitMQResource.class)
public class RabbitMqApiImplTest {

    Logger logger = Logger.getLogger(RabbitMqApiImplTest.class.getName());

    @Inject
    RabbitConsumer consumer;

    @Test
    void testPublishConsume() {

        String msg = UUID.randomUUID().toString();

        given()
                .when()
                .get("/rabbit?s=" + msg)
                .then()
                .statusCode(204);

        // Check that one message was received? not needed, we test that routed=true

        // Wait for message to be read
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // NO-OP
        }

        assertEquals(msg, consumer.getLastMessage());
    }

}
