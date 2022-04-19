package com.mycompany.springboot.controllers;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import com.mycompany.springboot.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

//Initialize and launch Spring Boot Application
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerIT {

    @LocalServerPort
    private int port;
    
    @Test
    public void testRetrieveSurveyQuestion() {
        String url = "http://localhost:" + port + "/surveys/Survey1/questions/Question1";

        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        System.out.println(response.toString());
        
        assertTrue(response.getBody().contains("id"));
        assertTrue(response.getBody().contains("correctAnswer"));
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));

		String expected = "{id:Question1,description:Largest Country in the World,correctAnswer:Russia}";

		//JSONAssert.assertEquals(expected, response.getBody(), false);

    }
}
