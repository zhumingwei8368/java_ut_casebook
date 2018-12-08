package com.opensrc.kafka;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;


/**
 * Created by David on 2017/12/4.
 */
public class SimpleKafkaConsumer {
  private String topic;
  private String group;
  private Properties properties = new Properties();

  public SimpleKafkaConsumer(String topic, String group) {
    this.topic = topic;
    this.group = group;
    initProperties();
  }

  @VisibleForTesting
  protected void setProperties(Properties properties){
    this.properties = properties;
  }

  private KafkaConsumer<Integer, String> createConsumer() {
    KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(properties);
    consumer.subscribe(Collections.singleton(topic));
    return consumer;
  }

  private void initProperties() {
    properties.put("group.id", group);
    properties.put("bootstrap.servers", "10.180.108.205:9092");  //my real Kafka server
    properties.put("enable.auto.commit", "false");
    properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    properties.put("auto.offset.reset", "earliest");//earliest  latest
  }

  public List<String> receiveMessages(int size){
    List<String> messages = Lists.newArrayList();

    KafkaConsumer<Integer, String> consumer = createConsumer();
    while (messages.size() < size){
      ConsumerRecords<Integer, String> records = consumer.poll(100);
      for (ConsumerRecord<Integer, String> record : records) {
        messages.add(record.value());
      }
    }

    return messages;
  }
}
