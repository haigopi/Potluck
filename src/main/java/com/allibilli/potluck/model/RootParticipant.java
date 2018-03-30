package com.allibilli.potluck.model;

import lombok.Data;

import java.util.List;

/**
 * Created by Gopi.Kancharla@CapitalOne.com
 */
@Data
public class RootParticipant {

    private String email;

    private String name;

    private List<ParticipantItem> children;
}
