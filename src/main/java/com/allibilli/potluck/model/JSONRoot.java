package com.allibilli.potluck.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Gopi.Kancharla@CapitalOne.com
 */
@Slf4j
@Data
public class JSONRoot {
    private String name;
    private List<RootParticipant> children;
}
