package com.allibilli.potluck.converter;

import com.allibilli.potluck.model.RootParticipant;
import lombok.Data;

import java.util.List;

/**
 * Created by Gopi.Kancharla@CapitalOne.com
 */
@Data
public class JSONResponse {

    private String name;
    private List<RootParticipant> children;

}
