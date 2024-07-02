package org.soframel;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
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
    MessageOutgoingInterceptor interceptor;

    @Test
    void testPublishOverflowAckNack(){

        given()
                .when().get("/rabbit?s=test1")
                .then()
                .statusCode(204);
        assertEquals(Boolean.TRUE, interceptor.getLastResult());
        interceptor.reset();

        given()
                .when().get("/rabbit?s=test2")
                .then()
                .statusCode(204);
        assertEquals(Boolean.TRUE, interceptor.getLastResult());
        interceptor.reset();

        given()
                .when().get("/rabbit?s=test3")
                .then()
                .statusCode(204);
        assertEquals(Boolean.FALSE, interceptor.getLastResult());

    }

    //commentted because consumer is commented
    /*
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
    }*/

}
