package edu.university.equipment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void validEquipmentIdentifierReturnsOk() throws Exception {
        mockMvc.perform(get("/api/equipment/LAPTOP01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("LAPTOP01"));
    }

    @Test
    void unknownEquipmentIdentifierReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/equipment/UNKNOWN"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }

    @Test
    void bookingUnknownEquipmentDoesNotChangeInventory() throws Exception {
        String before = mockMvc.perform(get("/api/equipment/LAPTOP01"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "borrowerId": "STU1001",
                                  "equipmentId": "UNKNOWN",
                                  "quantity": 1
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));

        String after = mockMvc.perform(get("/api/equipment/LAPTOP01"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(before, after);

        mockMvc.perform(get("/api/equipment/UNKNOWN"))
                .andExpect(status().isNotFound());
    }
}
