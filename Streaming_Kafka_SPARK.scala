// Databricks notebook source
// MAGIC %md ## Set up Connection to Kafka and VISUALIZE the received information
// MAGIC - ### we will use Scala Programing language https://www.scala-lang.org/
// MAGIC 
// MAGIC ### https://docs.databricks.com/spark/latest/structured-streaming/kafka.html
// MAGIC 
// MAGIC ---
// MAGIC #### ETSIT-UPM: Application Projects Course
// MAGIC #### Master Programmes MUIT - MUTSC

// COMMAND ----------

// MAGIC %md #### First check you can connect to your Google Cloud Platform Compute Engine

// COMMAND ----------

// MAGIC %sh ping  34.94.232.62

// COMMAND ----------

import org.apache.spark.sql.functions.{explode, split}

// Setup connection to Kafka
val kafka = spark.readStream
  .format("kafka")
  .option("kafka.bootstrap.servers", "34.94.232.62:9092")   // comma separated list of broker:host
  .option("subscribe", "twitterstream")    // comma separated list of topics
  .option("startingOffsets", "latest") // read data from the end of the stream
  .load()

// COMMAND ----------

// split lines by whitespace and explode the array as rows of `word`
val df = kafka.select(explode(split($"value".cast("string"), "\\s+")).as("word"))
  .groupBy($"word")
  .count

// COMMAND ----------

// follow the word counts as it updates
display(df.select($"word", $"count"))
