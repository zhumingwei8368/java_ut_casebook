package com.opensrc.kafka;

import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import com.google.common.collect.Lists;

public class EmbedKafkaTest {
  private static final String TOPIC = "topic1";

  @ClassRule  //means invoke before() and  after()
  public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(2, true, 2, TOPIC);

  @Test
  public void test_kafka_send_receive() throws Exception {
    Properties pros = new Properties();
    pros.putAll(KafkaTestUtils.producerProps(embeddedKafka));
    SimpleKafkaProducer producer = new SimpleKafkaProducer();
    producer.setProperties(pros);

    producer.send(TOPIC, Lists.newArrayList("msg1", "msg2"));

    String group = "group1";
    Properties consumerPros = new Properties();
    consumerPros.putAll(KafkaTestUtils.consumerProps(group, "false", embeddedKafka));
    consumerPros.put("auto.offset.reset", "earliest");
    SimpleKafkaConsumer consumer = new SimpleKafkaConsumer(TOPIC, group);
    consumer.setProperties(consumerPros);

    List<String> messages = consumer.receiveMessages(2);

    Assert.assertEquals(2, messages.size());
    Assert.assertTrue(messages.contains("msg1"));
    Assert.assertTrue(messages.contains("msg2"));
  }
}