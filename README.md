# Quarkus RabbitMQ Tests

Tests with Quarkus and RabbitMQ. 

RabbitMQ instance is started from the docker compose file, using configurations read from `src/test/resource/etc_rabbitmq`. 
The same configuration is also used in a RabbitMQ testcontainers for unit tests. 

## Ack/Nack in case of Queue overflow

RabbitMQ is configured to only accept 1 message per queue in `src/test/resource/etc_rabbitmq/definitions.json` using a `reject-publish` mode, using a policy (see https://www.rabbitmq.com/docs/maxlength#overflow-behaviour).

The application is configured to only produce messages, and not consume them (consumer has been commented), with `publish-confirms=true`. 
The test in `RabbitMqApiImplTest.testPublishOverflowAckNack` publishes 3 message, and should receive a nack at least for the 3rd one (rabbitmq accepts one message more than configured in overflow). 