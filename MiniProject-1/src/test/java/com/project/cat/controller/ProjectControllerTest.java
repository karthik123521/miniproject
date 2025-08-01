package com.project.cat.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cat.Binding.Login;
import com.project.cat.Controller.CaseWorkerController;
import com.project.cat.Service.CaseWorkerService;

@WebMvcTest(CaseWorkerController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc; //used to send the request

    @Mock
    private CaseWorkerService caseWorkerService;

    @Test
    public void testGetAllUsers() throws Exception {
    	when(caseWorkerService.getAllUsers()).thenReturn(null);
     MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/caselist"); //it creates the request
     ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder); //used to perform the request
     MvcResult andReturn = perform.andReturn(); //returns the result
     MockHttpServletResponse response = andReturn.getResponse(); //returns the response
     int status2 = response.getStatus(); //returns the status
     assertEquals(500, status2);  //it compare the httpStatus code.
    }
    
    @Test
    public void testsaveCaseWorker() throws Exception {
    	when(caseWorkerService.login(ArgumentMatchers.any())).thenReturn("yes");
    	
    	
    	Login login=new Login("karthik","1235456");
    	ObjectMapper obj=new ObjectMapper();
    	String write = obj.writeValueAsString(login);
    	
    	
    	MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/login")
    																.contentType(MediaType.APPLICATION_JSON)
    																.content(write);
    	
    	ResultActions perform = mockMvc.perform(post);
    	
    	MvcResult andReturn = perform.andReturn();
    	
    	MockHttpServletResponse response = andReturn.getResponse();
    	
    	int status = response.getStatus();
    	
    	assertEquals(200, status);
    }
}

