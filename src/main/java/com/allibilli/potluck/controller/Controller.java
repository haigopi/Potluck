package com.allibilli.potluck.controller;

import com.allibilli.potluck.db.repo.ParticipantRepository;
import com.allibilli.potluck.model.JSONRoot;
import com.allibilli.potluck.model.Participant;
import com.allibilli.potluck.model.ParticipantItem;
import com.allibilli.potluck.model.RootParticipant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gopi.Kancharla@CapitalOne.com
 */

@RestController
@Slf4j
public class Controller {

    @Autowired
    private ParticipantRepository participantRepository;


    @PostMapping("/save")
    public void save(@ModelAttribute Participant participant) {
        log.info("Saving Input");
        participantRepository.save(participant);
    }

    @RequestMapping("/list")
    public JSONRoot list() {
        log.info("Saving Input");
        List<Participant> participants = participantRepository.findAll();

        JSONRoot root = new JSONRoot();
        List<RootParticipant> rootParticipants = new ArrayList<>();

        for (Participant participant : participants) {
            RootParticipant rootParticipant = new RootParticipant();
            List<ParticipantItem> participantItems = new ArrayList<>();

            rootParticipant.setEmail(participant.getEmail());
            rootParticipant.setEmail(participant.getEmail());

            String list = participant.getItems();
            String[] items = list.split(",");
            for (String item : items) {
                ParticipantItem item_p = new ParticipantItem();
                item_p.setName(item);
                participantItems.add(item_p);
            }
            rootParticipant.setChildren(participantItems);
            rootParticipants.add(rootParticipant);
        }

        root.setChildren(rootParticipants);
        return root;
    }
}
