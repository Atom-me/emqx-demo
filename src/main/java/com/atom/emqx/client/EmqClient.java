package com.atom.emqx.client;

import com.atom.emqx.config.MqttProperties;
import com.atom.emqx.enums.QosEnum;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;


/**
 * @author Atom
 */
@Component
public class EmqClient {

    private static final Logger log = LoggerFactory.getLogger(EmqClient.class);


    private IMqttClient mqttClient;

    @Resource
    private MqttProperties mqttProperties;

    @Resource
    private MqttCallback mqttCallback;

    @PostConstruct
    public void init() {
        String broker = mqttProperties.getBrokerUrl();
        String clientId = mqttProperties.getClientId();
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            mqttClient = new MqttClient(broker, clientId, persistence);
        } catch (MqttException e) {
            log.error("init mqtt client fail {},broker={},clientId={}", e, broker, clientId);
        }
    }


    /**
     * connect emq x broker
     *
     * @param username
     * @param password
     */
    public void connect(String username, String password) {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setUserName(username);
        connectOptions.setPassword(password.toCharArray());
        connectOptions.setCleanSession(true);

        mqttClient.setCallback(mqttCallback);
        try {
            mqttClient.connect(connectOptions);
        } catch (Exception e) {
            log.error("connect emqx broker fail {},connectOptions={}", e, connectOptions);
        }
    }


    /**
     * disconnect
     *
     * @PreDestroy :  auto disconnect when application exist
     */
    @PreDestroy
    public void disconnect() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            log.error("disConnect emq x broker fail.", e);
        }
    }


    /**
     * reconnect
     */
    public void reConnect() {
        try {
            mqttClient.reconnect();
        } catch (MqttException e) {
            log.error("reconnect emq x broker fail.", e);
        }
    }


    /**
     * publish message
     *
     * @param topic
     * @param payload
     * @param qos
     * @param retained
     */
    public void publish(String topic, String payload, QosEnum qos, boolean retained) {
        MqttMessage message = new MqttMessage();
        message.setPayload(payload.getBytes(StandardCharsets.UTF_8));
        message.setQos(qos.value());
        message.setRetained(retained);
        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            log.error("mqttClient publish message fail.{}, topic={},message={},qos={},retained={}", e, topic, payload, qos, retained);
        }
    }


    /**
     * subscribe topics
     *
     * @param topicFilter
     * @param qos
     */
    public void subscribe(String topicFilter, QosEnum qos) {
        try {
            mqttClient.subscribe(topicFilter, qos.value());
        } catch (MqttException e) {
            log.error("mqttClient subscribe fail {},topicFilter={},qos={}", e, topicFilter, qos);
        }
    }

    /**
     * unSubscribe topics
     *
     * @param topicFilter
     */
    public void unSubscribe(String topicFilter) {
        try {
            mqttClient.unsubscribe(topicFilter);
        } catch (MqttException e) {
            log.error(" mqttClient unsubscribe fail {},topicFilter={}", e, topicFilter);
        }
    }

}
