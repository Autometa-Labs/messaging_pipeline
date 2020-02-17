package me.skatz.kafka

import java.time.LocalDateTime
import java.util.Properties

import me.skatz.models.Message
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Producer {

  def main(args: Array[String]): Unit = {
    println("Producer started")
    val props = configure()
    produceToKafka(props,  sys.env.getOrElse("topic_name", "our_kafka_topic"))
    println("Producer completed")
  }

  def configure(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", sys.env.getOrElse("bootstrap_servers", "localhost:9092"))
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props
  }

  def produceToKafka(props: Properties, topic: String): Unit = {
    val producer = new KafkaProducer[Nothing, String](props)

    while (true) {
      val message = new Message(LocalDateTime.now().toString)
      val record = new ProducerRecord(topic, message.getData)
      producer.send(record)
      println(s"SENT: ${message.getData}")
      Thread.sleep(1000)
    }

    producer.close()
  }
}
