package com.baloise.open.edw.wcc;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.state.*;
import org.apache.kafka.streams.state.internals.KeyValueStoreBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class EdwService {
  /**
   * In Kafka client the parameter is wrong
   * {@link AdminClientConfig#BOOTSTRAP_SERVERS_CONFIG} is set to "bootstrap.servers" which does not work
   */
  public static final String KAFKA_SERVER_CONFIG_KEY = "kafka.bootstrap.servers";
  public static final String STATUS_TOPIC_NAME = "dz.edw.workflow.status";
  private static final String CC_CLIENT_ID = "EDW-CC";
  private static final String KAFKA_HOSTNAME = "${com.baloise.open.edw.wcc.kafka-host}";
  public static final String STATUS_STORE = "StatusStore";

  protected EdwService() {
  }

  private Properties getProperties() {
    final Properties configProps = new Properties();
    configProps.put(KAFKA_SERVER_CONFIG_KEY, "localhost:9092");
//    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//    configProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//    configProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//    configProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//    configProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, CC_CLIENT_ID);
    configProps.put("acks", "all");
    configProps.put(AdminClientConfig.CLIENT_ID_CONFIG, KAFKA_HOSTNAME);

    configProps.put(StreamsConfig.APPLICATION_ID_CONFIG, CC_CLIENT_ID);
    configProps.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    configProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    configProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

    return configProps;
  }

  public List<Object> getEdwStatus() {
    StreamsBuilder streamsBuilder = new StreamsBuilder();
    streamsBuilder.stream(STATUS_TOPIC_NAME);
    streamsBuilder.addStateStore(new KeyValueStoreBuilder<>(Stores.inMemoryKeyValueStore(STATUS_STORE), Serdes.String(), Serdes.String(), Time.SYSTEM));

    Topology topology = streamsBuilder.build();
    // topology.addStateStore(new KeyValueStoreBuilder<>(Stores.inMemoryKeyValueStore(STATUS_STORE), Serdes.String(), Serdes.String(), Time.SYSTEM));

    KafkaStreams streams = new KafkaStreams(topology, getProperties());
    streams.start();

    ReadOnlyKeyValueStore<Object, Object> statusStore = streams.store(StoreQueryParameters.fromNameAndType(STATUS_STORE, QueryableStoreTypes.keyValueStore()));

    int i = 0;
    List<Object> list = new ArrayList<>();
    KeyValueIterator<Object, Object> storeIterator = statusStore.all();
    while (storeIterator.hasNext() && i++ < 100) {
      list.add(storeIterator.next());
    }
    return list;
  }
}
