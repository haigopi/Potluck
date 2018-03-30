package com.allibilli.potluck.db;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Gopi.Kancharla@CapitalOne.com
 */
//@Component
public class MongoConnection {

    @PostConstruct
    private void init() {
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://potluck:potluck@cluster0.mongodb.net/");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("test");
    }

}
