/*
 * Copyright 2021 Baloise Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baloise.open.edw.wcc;

import com.baloise.open.edw.wcc.dto.StatusDto;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EdwService {
  /**
   * In Kafka client the parameter is wrong
   * {@link AdminClientConfig#BOOTSTRAP_SERVERS_CONFIG} is set to "bootstrap.servers" which does not work
   */
  public static final String SCHEMA_SERVER_CONFIG_KEY = "schema.registry.url";
  public static final String STATUS_TOPIC_NAME = "dz.edw.workflow.status";
  private static final String CC_CLIENT_ID = "EDW-CC";
  private static final String KAFKA_HOSTNAME = "localhost";

  protected EdwService() {
  }

  /**
   * Provides the collection of topic partitions we need to brows a topic.
   *
   * @param consumer  the consumer we use, to access the topic.
   * @param topicName the topic we want to access.
   * @return the collection, must not be null.
   */
  private static Set<TopicPartition> getTopicPartition(KafkaConsumer<?, ?> consumer, String topicName) {
    List<PartitionInfo> partitionInfos = consumer.partitionsFor(topicName);
    return partitionInfos
        .stream()
        .map(partitionInfo -> new TopicPartition(partitionInfo.topic(), partitionInfo.partition()))
        .collect(Collectors.toSet());
  }

  private Properties getProperties() {
    final Properties properties = new Properties();
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, CC_CLIENT_ID);
    properties.put(AdminClientConfig.CLIENT_ID_CONFIG, KAFKA_HOSTNAME);

    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

    properties.put(SCHEMA_SERVER_CONFIG_KEY, "http://localhost:9012");
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
    properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

    return properties;
  }

  public List<StatusDto> getEdwStatus() {
    KafkaConsumer<String, StatusDto> consumer = new KafkaConsumer<>(getProperties());

    Set<TopicPartition> topicPartitions = getTopicPartition(consumer, STATUS_TOPIC_NAME);
    consumer.assign(topicPartitions);

    ArrayList<StatusDto> statusList = new ArrayList<>();
    consumer.seekToBeginning(topicPartitions);
    ConsumerRecords<String, StatusDto> records = consumer.poll(Duration.ofDays(1));
    records.forEach((record) -> {
      try {
        StatusDto status = record.value();
        EdwService.log.info("Record: {}", status);
        statusList.add(status);
      } catch (Throwable e) {
        EdwService.log.error("Issue converting AVRO", e);
      }
    });

    return statusList;
  }
}
