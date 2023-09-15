FROM rabbitmq:management

# Abilita i plugin MQTT e STOMP
RUN rabbitmq-plugins enable rabbitmq_mqtt rabbitmq_stomp