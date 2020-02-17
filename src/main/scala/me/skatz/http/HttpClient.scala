package me.skatz.http

import scalaj.http.{Http, HttpOptions}

object HttpClient {

  def get(url: String, params: Map[String, String]): String = {
    val result = Http(url).params(params).asString
    result.body
  }

  def post(url: String, postData: String): String = {
    val result = Http(url)
      .method("POST")
      .postData(postData)
      .header("Content-Type", "application/x-ndjson")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000)).asString
    result.body
  }
}
