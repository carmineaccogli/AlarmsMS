package it.unisalento.smartcitywastemanagement.alarmsms.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MqttConfiguration {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String baseClientId;

    @Value("${mqtt.topic.capacityAlarms}")
    private String capacityAlarmsTopic;

    @Value("${mqtt.topic.cleaningAlarms}")
    private String cleaningAlarmsTopic;




    @Bean
    public MessageChannel mqttInputChannelCapacityTopic() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttInputChannelCleaningTopic() {
        return new DirectChannel();
    }


    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInboundCapacity() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(brokerUrl, baseClientId+"-capacity", capacityAlarmsTopic);
        adapter.setOutputChannel(mqttInputChannelCapacityTopic());
        return adapter;
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInboundCleaning() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(brokerUrl, baseClientId+"-cleaning", cleaningAlarmsTopic);
        adapter.setOutputChannel(mqttInputChannelCleaningTopic());
        return adapter;
    }


}
