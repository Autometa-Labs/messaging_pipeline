package me.skatz.producer.utils

import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

import me.skatz.cassandraProc.database.TweeterMessage
import me.skatz.shared.Configuration

object MessageGenerator {
  val numOfTweets: Integer = 10
  val tweetLength: Integer = 70
  val fullDirPath: String = s"${System.getProperty("user.dir")}${Configuration.filesDir}"
  val fnameFm: FileManager = new FileManager(new File(s"${fullDirPath}/${Configuration.firstNamesFile}"))
  val surnameFm: FileManager = new FileManager(new File(s"${fullDirPath}/${Configuration.lastNamesFile}"))
  val wordsFm: FileManager = new FileManager(new File(s"${fullDirPath}/${Configuration.wordsFile}"))

  def generateTweetMsg(): TweeterMessage = {
    val tweet = new StringBuilder
    val append = (str1: StringBuilder, str2: String) => str1.append(str2 + ' ')

    for (_ <- 0 to tweetLength) {
      append(tweet, wordsFm.getRandomElement)
    }
    TweeterMessage(fnameFm.getRandomElement, surnameFm.getRandomElement, tweet.toString.trim, currentDate)
  }

  // One of the supported formats by Elasticsearch
  def currentDate: String = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ").format(Calendar.getInstance.getTime)
}