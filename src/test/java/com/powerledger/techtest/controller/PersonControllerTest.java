package com.powerledger.techtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerledger.techtest.dto.NamesDTO;
import com.powerledger.techtest.exception.TechTestException;
import com.powerledger.techtest.service.PersonService;
import com.powerledger.techtest.utils.TestDataUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService service;

    /**
     * Given: PersonController
     * When: retrieving names from a valid range of postcodes
     * Then: return HTTP 200 and NamesDTO(list of alphabetically ordered names, total number of characters)
     * @throws Exception
     */
    @Test
    public void givenPersonController_whenRetrieveNamesBetweenRange_returns_successful_then200() throws Exception {
        // preparing test mock data
        NamesDTO namesDTO = new NamesDTO(58, Arrays.asList(TestDataUtils.prepareOrderedNames()));
        when(service.retrieveNamesBetweenRange(Mockito.anyLong(), Mockito.anyLong())).thenReturn(namesDTO);
        String expectedResult = new String("{\"totalNumberOfCharacters\":58,\"names\":[\"Ashley Buchanan\",\"Ashley Rogers\",\"Ashlley Rogers\",\"Brian Sanders\",\"John Doe\"]}");

        // execution and assertion
        this.mockMvc.perform(get("/person/retrieveBetweenRange?startRange=6000&endRange=6002")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(expectedResult)));
    }

    /**
     * Given: PersonController
     * When: retrieving names between invalid postcode range
     * Then: return HTTP 409 and error message, code and timestamp
     * @throws Exception
     */
    @Test
    public void givenPersonController_whenRetrieveNamesBetweenInvalidRange_then409() throws Exception {
        // preparing test mock data
        NamesDTO namesDTO = new NamesDTO(58, Arrays.asList(TestDataUtils.prepareOrderedNames()));
        String exceptionMessage = "End range is lesser than start range";
        when(service.retrieveNamesBetweenRange(Mockito.anyLong(), Mockito.anyLong())).thenThrow(new TechTestException(exceptionMessage));


        // execution and assertion
        this.mockMvc.perform(get("/person/retrieveBetweenRange?startRange=60000&endRange=6002")).andDo(print()).andExpect(status().isConflict())
                .andExpect(content().string(containsString(exceptionMessage)));
    }

    /**
     * Given: PersonController
     * When: saving a valid list of names and corresponding postcodes
     * Then: persist in database and return HTTP 201 with the list of inserted entities
     * @throws Exception
     */
    @Test
    public void givenPersonController_whenCreateAll_returns_successful_then201() throws Exception {
        // preparing test mock data
        when(service.createAll(Mockito.any())).thenReturn(TestDataUtils.preparePersonList());

        // execution and assertion
        this.mockMvc.perform(post("/person/createAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(TestDataUtils.prepareMapData().values())))
                .andDo(print()).andExpect(status().isCreated());
    }

    /**
     * Given: PersonController
     * When: saving an invalid list of names and postcodes
     * Then: return HTTP 422 with error message, code and timestamp
     * @throws Exception
     */
    @Test
    public void givenPersonController_whenCreateAll_returns_exception_then422() throws Exception {
        // preparing test mock data
        when(service.createAll(Mockito.any())).thenThrow(ConstraintViolationException.class);

        // execution and assertion
        this.mockMvc.perform(post("/person/createAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(TestDataUtils.prepareMapData().values())))
                .andDo(print()).andExpect(status().isUnprocessableEntity());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}