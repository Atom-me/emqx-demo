package com.atom.emqx;

import com.atom.emqx.client.EmqClient;
import com.atom.emqx.config.MqttProperties;
import com.atom.emqx.enums.QosEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class EmqxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmqxDemoApplication.class, args);
    }


    @Resource
    private EmqClient emqClient;
    @Resource
    private MqttProperties mqttProperties;

    @PostConstruct
    public void init() {
        emqClient.connect(mqttProperties.getUsername(), mqttProperties.getPassword());

        emqClient.subscribe("testtopic/#", QosEnum.QOS2);

        new Thread(() -> {
            while (true) {
                emqClient.publish("testtopic/123", "publish message:" + LocalDateTime.now(), QosEnum.QOS2, false);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
