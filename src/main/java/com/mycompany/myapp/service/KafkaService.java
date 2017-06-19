package com.mycompany.myapp.service;


import com.mycompany.myapp.domain.Person;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KafkaService {

    public static void send(Person person) {


        String topicName = "test";

        Properties props = new Properties();

        props.put("bootstrap.servers", "192.168.99.100:9092");

        //Set acknowledgements for producer requests.
        props.put("acks", "all");

            //If the request fails, the producer can automatically retry,
            props.put("retries", 0);

        props.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);

        props.put("key.serializer",
            "org.apache.kafka.common.serialization.StringSerializer");

        props.put("value.serializer",
            "org.springframework.kafka.support.serializer.JsonSerializer");

        Producer<String, Person> producer = new KafkaProducer
            <String, Person>(props);


        producer.send(new ProducerRecord<String, Person>(topicName,
                person.getDbKey(), person));
        System.out.println("Message sent successfully");
        producer.close();
    }
}
