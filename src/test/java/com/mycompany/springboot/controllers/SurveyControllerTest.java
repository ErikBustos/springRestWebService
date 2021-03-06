package com.mycompany.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.util.Arrays;

import com.mycompany.springboot.models.Question;
import com.mycompany.springboot.service.SurveyService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;

//Initialize and launch Spring Boot Application
@RunWith(SpringRunner.class)
@WebMvcTest(value=SurveyController.class)
@WithMockUser
//@AutoConfigureMockMvc(addFilters = false) // Other way to disable security
public class SurveyControllerTest {

    @Autowired
	private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;
    
    @Test
    public void retrieveDetailsForQuestion() throws Exception {
        Question mockQuestion = new Question("Question1",
        "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));
                
        when(surveyService.retrieveQuestion(anyString(), anyString()))
            .thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
            "/surveys/Survey1/questions/Question1").accept(
            MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{id:Question1,description:\"Largest Country in the World\",correctAnswer:Russia}";

        JSONAssert.assertEquals(expected, 
            result.getResponse().getContentAsString(), 
            false);

    }

    @Test
    public void createSurveyQuestion() throws Exception{
        Question mockQuestion = new Question("1", "Smallest Number", "1",
        Arrays.asList("1", "2", "3", "4"));

        String questionJson = "{\"description\":\"Smallest Number\",\"correctAnswer\":\"1\",\"options\":[\"1\",\"2\",\"3\",\"4\"]}";

        //surveyService.addQuestion to respond back with mockQuestion
        when(surveyService.addQuestion(anyString(), 
            Mockito.any(Question.class))).thenReturn(mockQuestion);

		//Send question as body to /surveys/Survey1/questions
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/surveys/Survey1/questions")
				.accept(MediaType.APPLICATION_JSON).content(questionJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/surveys/Survey1/questions/1", response
				.getHeader(org.springframework.http.HttpHeaders.LOCATION));

    }
}

