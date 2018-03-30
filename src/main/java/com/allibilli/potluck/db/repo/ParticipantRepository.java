package com.allibilli.potluck.db.repo;

import com.allibilli.potluck.model.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Gopi.Kancharla@CapitalOne.com
 */
public interface ParticipantRepository extends MongoRepository<Participant, String> {

    public Participant findByName(String firstName);

}
