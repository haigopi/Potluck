package com.allibilli.potluck.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created by Gopi.Kancharla@CapitalOne.com
 */
@Data
public class Participant {

    @Id
    private String email;

    private String name;

    private String items;

}
