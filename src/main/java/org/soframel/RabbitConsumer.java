package org.soframel;

import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.common.annotation.Identifier;
import io.vertx.amqp.AmqpClientOptions;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.core.net.PemTrustOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class RabbitConsumer {

    Logger logger = Logger.getLogger(this.getClass().getName());

    String lastMessage = null;

    public String getLastMessage() {
        return lastMessage;
    }

    @Incoming("myqueue-in")
    public CompletionStage<Void> readMessage(Message<String> message) {
        lastMessage = message.getPayload();
        logger.info("Received message " + lastMessage);
        return message.ack();
    }

}
