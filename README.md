# Messaging Pipeline
Messaging pipeline generates messages and allows the user to visualize them using the following components:

- Kafka and Zookeeper
- ELK stack
- Cassandra
- Grafana
- Jupyter notebooks + Spark
- Mock S3 

<img src="/src/main/resources/diagram/messages_flow.png" width=75%>

<img src="/src/main/resources/diagram/logs_flow.png" width=75%>

<img src="/src/main/resources/diagram/metrics_flow.png" width=75%>


## Installation Steps for Docker:

``` 
- cd messaging_pipeline/.integration
- ./setup-env.sh
- Run KafkaProducer
- Run EnrichmentProc
- Run ElasticsearchProc
- Run CassandraProc
- Run MetricProc
- Run LTProc
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

## Installation Steps for Jupyter:
If bash is your thing:
- docker exec -it -u root jupyter bash
- cd $SPARK_HOME/jars/
- wget https://repo1.maven.org/maven2/com/databricks/spark-xml_2.12/0.10.0/spark-xml_2.12-0.10.0.jar
- pyspark --packages com.databricks:spark-xml_2.12:0.10.0


- Run docker exec -it jupyter jupyter notebook list
- Copy the token provided to the clipboard to login

Sample Python playbook:
```
from pyspark import SparkContext
from pyspark import SparkConf
from re import Match

# Currently not able to get files directly from S3 mock, therefore 
# this requires downloading the file first
url = "untitled.txt"

conf = SparkConf()
sc = SparkContext.getOrCreate(conf=conf)
text_file = sc.textFile(url)

res = text_file.flatMap(lambda line: line.split(":")) \
                .filter(lambda w: len(w.split(" ")) == 1) \
                .map(lambda w: (w, 1)) \
                .reduceByKey(lambda a, b: a + b)

print(res.collect())
```
