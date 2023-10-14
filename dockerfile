FROM rabbitmq:management

# Abilita i plugin MQTT e WEBSTOMP
RUN rabbitmq-plugins enable rabbitmq_mqtt rabbitmq_web_stomp