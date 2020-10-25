# kafka_playground
This repository contains the following components:

- Kafka infrastructure (including Zookeeper)
- Elasticsearch 
- Kibana
- Cassandra
- Grafana

<img src="/src/main/resources/diagram/messages_flow.png" width=75%>

<img src="/src/main/resources/diagram/logs_flow.png" width=75%>



Future Plans:
- [x] Add Producer
- [x] Add Consumer
- [x] Add Elastic Search
- [x] Integrate Kibana
- [x] Add Cassandra
- [x] Improve Readme with an ifrastructure diagram
- [x] Add installation instructions in Readme
- [x] Change Producer to produce a more realistic data, for instance - mouse movement from the user, or keyboard input
- [x] Use Helm Charts
- [x] Use multiple Kafka topics
- [ ] Implement metrics


## Installation Steps for Docker:

``` 
- cd kafka_playground/.integration
- ./setup-env.sh
- Run KafkaProducer
- Run EnrichmentProc
- Run ElasticsearchProc
- Run CassandraProc
- Run MetricProc
```

## Installation Steps for Cassandra:

```
- docker exec -it cassandra cqlsh
- CREATE KEYSPACE kafka WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};
- CREATE TABLE kafka.tweets(
    date text,
    tweet text,
    firstName text,
    lastName text,
    PRIMARY KEY ((firstName, lastName), date)
   );

- CREATE TABLE kafka.metrics(
    key text,
    aggregator text,
    date text,
    value double,
    PRIMARY KEY (key, date, aggregator)
    );
```

## Installation Steps for Elasticsearch:
- Add the following indexes:
```
- logstash*
- kafka*
```
- For better visualization, use the appropriate timestamp fields

## Installation Steps for Cassandra:
- Follow the instructions in `setup-cassandra.sh` file

## Installation Steps for Grafana:
- Follow the instructions in the `setup-grafana` file
- Add Elasticsearch as a datasource:
    - Use elasticsearch:9200 as the URL
    - Use \*kafka\* as the Index Name
    - Use version 7.0+
- Add Cassandra as a datasource:
    - Use cassandra:9042 as the Host
    - Use `kafka` as the keyspace
