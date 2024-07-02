package org.soframel;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.MountableFile;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

/**
 * Starts a testcontainer with RabbitMQ.
 * Uses the RabbitMQ configuration in src/test/resources/rabbitmq
 */
public class RabbitMQResource implements QuarkusTestResourceLifecycleManager {

    static GenericContainer rabbitMqContainer;

    static Integer RABBITMQ_PORT = 5672;

    static {
        rabbitMqContainer = new GenericContainer<>(
                "rabbitmq:3.13-management")
                .withEnv("RABBITMQ_DEFAULT_USER", "guest")
                .withEnv("RABBITMQ_DEFAULT_PASS", "guest")
                .withCopyFileToContainer(MountableFile.forClasspathResource("etc_rabbitmq"), "/etc/rabbitmq/")
                .withExposedPorts(RABBITMQ_PORT)
                .withStartupTimeout(Duration.ofSeconds(180));
    }

    @Override
    public Map<String, String> start() {
        rabbitMqContainer.start();
        Map<String, String> props = new HashMap<>();
        props.put("amqp-host", rabbitMqContainer.getHost());
        props.put("amqp-port", rabbitMqContainer.getMappedPort(RABBITMQ_PORT) + "");
        return props;
    }

    /**
     * debug logs
     */
    @Override
    public void stop() {
        String logs = rabbitMqContainer.getLogs();
        // Note: not using logging API because it (in some contexts) does not work in
        // Quarkus Test Resources ! see
        // https://github.com/quarkusio/quarkus/issues/10885
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@ RabbitMQ Container logs:\n" + logs);

        rabbitMqContainer.stop();
        rabbitMqContainer.close();
    }

}
