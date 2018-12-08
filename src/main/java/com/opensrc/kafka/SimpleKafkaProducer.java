package com.opensrc.kafka;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.common.annotations.VisibleForTesting;

/**
 * Created by David on 2017/12/4.
 */
public class SimpleKafkaProducer {
  private Producer<Integer,String> producer;
  private Properties props = new Properties();

  public SimpleKafkaProducer() {
    initProducer();
  }

  @VisibleForTesting
  protected void setProperties(Properties props){
    this.props = props;
    producer = new KafkaProducer<Integer,String>(props);
  }

  private void initProducer() {
    props = new Properties();

    props.put("metadata.broker.list", "10.180.108.205:9092");//my real Kafka server
    props.put("bootstrap.servers", "10.180.108.205:9092");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    producer = new KafkaProducer<Integer,String>(props);
  }

  public void send(final String topic, List<String> messages){
    for (String message : messages) {
      producer.send(new ProducerRecord<>(topic, 0, 0, message));
    }
  }
}
