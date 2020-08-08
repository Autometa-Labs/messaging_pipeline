package me.skatz.kafka

import akka.actor.ActorSystem
import akka.kafka.scaladsl.{Consumer, Producer}
import akka.kafka.{CommitterSettings, ProducerMessage, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Keep
import com.sksamuel.avro4s.json.JsonToAvroConverter
import me.skatz.utils.{Configuration, KafkaUtils}
import org.apache.kafka.clients.producer.ProducerRecord
import spray.json.DefaultJsonProtocol

object EnrichmentProc extends App with DefaultJsonProtocol {
  implicit val system: ActorSystem = ActorSystem("EnrichmentProc")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val consumerSettings = KafkaUtils.configureConsumerSettings()
  val producerSettings = KafkaUtils.configureProducerSettings()
  val committerSettings = CommitterSettings(system).withMaxBatch(1L).withParallelism(1)

  Consumer
    .committableSource(consumerSettings, Subscriptions.topics(Configuration.ingestEnrichTopic))
    .map { msg =>
      ProducerMessage.multi(
        List[ProducerRecord[String, String]](
          new ProducerRecord[String, String](Configuration.enrichEsprocTopic, jsonToAvro(msg.record.value)),
          new ProducerRecord[String, String](Configuration.enrichCassTopic, jsonToAvro(msg.record.value))
        ),
        msg.committableOffset
      )
    }
    .toMat(Producer.committableSink(producerSettings, committerSettings))(Keep.both)
    .run()

  def jsonToAvro(msg: String): String = {
    val converter = new JsonToAvroConverter("me.skatz.kafka")
    converter.convert("test", msg).toString(true)
  }
}
