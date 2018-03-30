package com.allibilli.potluck.controller;

import com.allibilli.potluck.db.repo.ParticipantRepository;
import com.allibilli.potluck.model.JSONRoot;
import com.allibilli.potluck.model.Participant;
import com.allibilli.potluck.model.ParticipantItem;
import com.allibilli.potluck.model.RootParticipant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Gopi.Kancharla@CapitalOne.com
 */

@RestController
@Slf4j
public class Controller {

    @Autowired
    private ParticipantRepository participantRepository;


    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Participant participant, RedirectAttributes attributes) {
        log.info("Saving Input");
        if (ObjectUtils.isEmpty(participant.getName()) || ObjectUtils.isEmpty(participant.getEmail())) {
            attributes.addAttribute("status", "Nothing Saved. Sorry!");
        } else {
            participantRepository.save(participant);
            attributes.addAttribute("status", "SavedSuccessfully");
        }
        return new ModelAndView("redirect:/index.html", new HashMap<>());
    }

    @RequestMapping("/list")
    public JSONRoot list() {
        log.info("Retrieving Data");
        List<Participant> participants = participantRepository.findAll();

        JSONRoot root = new JSONRoot();
        List<RootParticipant> rootParticipants = new ArrayList<>();

        for (Participant participant : participants) {
            RootParticipant rootParticipant = new RootParticipant();
            List<ParticipantItem> participantItems = new ArrayList<>();

            rootParticipant.setEmail(participant.getEmail());
            rootParticipant.setName(participant.getName());

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
