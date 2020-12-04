[![GitHub version](https://badge.fury.io/gh/conventional-changelog%2Fstandard-version.svg)](https://badge.fury.io/gh/conventional-changelog%2Fstandard-version)

# Messaging Pipeline
Messaging pipeline generates messages and allows the user to visualize them using the following components:

- Kafka and Zookeeper
- ELK
- Cassandra
- Grafana

<img src="/src/main/resources/diagram/messages_flow.png" width=75%>

<img src="/src/main/resources/diagram/logs_flow.png" width=75%>

<img src="/src/main/resources/diagram/metrics_flow.png" width=75%>

# Infrastructure deployment using Terraform

## How to deploy

### Pre-reqs

- Install git
- Install awscli
- Install terraform

### Deploy

1. Clone the repo
```
git clone https://github.com/skatz1990/messaging_pipeline.git
```
2. Run Terraform code
```
terraform init
```
```
terraform plan
```
```
terraform apply -var github_token="*******" -var region="AWS region" -var access_key="*******" -var secret_key="*********" -var cluster_name="cluster name" -var app_service_name="service name"
```
## Diagram
<img src="/src/main/resources/diagram/msg_pipeline_tf_v6.png" width=100%>

## Installation Steps for main components:

|          Docker        |                Cassandra                 |         Elasticsearch    |                    Grafana         | 
|          :---:         |                  :---:                   |              :---:       |                     :---:          |
| Copy integration dir   | docker exec -it cassandra cqlsh          |     Add index: logstash* |  Add Elasticsearch as a datasource |
| Run KafkaProducer      | CREATE KEYSPACE kafka WITH REPLICATION   |     Add index: kafka*    |  Use elasticsearch:9200 as the URL |
| Run EnrichmentProc     | REATE TABLE kafka.tweets                 |                          |  Use \*kafka\* as the Index Name   |
| Run ElasticsearchProc  | CREATE TABLE kafka.metrics               |                          |  Use version 7.0+                  |
| Run CassandraProc      |                                          |                          |  Add Cassandra as a datasource     | 
| Run MetricProc         |                                          |                          |  Use cassandra:9042 as the Host    |
| Run MetricProc         |                                          |                          |  Use `kafka` as the keyspace       |