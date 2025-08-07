package com.project.rep.Binding;

import lombok.Data;

@Data
public class SearchRequest {
	
	
    private String planName;
    private String planStatus;

    public SearchRequest(String planName, String planStatus) {
        this.planName = planName;
        this.planStatus = planStatus;
    }

}

