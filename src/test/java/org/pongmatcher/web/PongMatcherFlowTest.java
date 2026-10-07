package org.pongmatcher.web;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Runs the interaction instructions from the README against the app with an in-memory database.
 */
@SpringBootTest
@AutoConfigureMockMvc
final class PongMatcherFlowTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void matchesTwoPlayersAndRecordsResult() throws Exception {
        mockMvc.perform(delete("/all")).andExpect(status().isOk());

        mockMvc.perform(put("/match_requests/firstrequest")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"player\": \"andrew\"}"))
                .andExpect(status().isOk());
        mockMvc.perform(put("/match_requests/secondrequest")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"player\": \"navratilova\"}"))
                .andExpect(status().isOk());

        String body = mockMvc.perform(get("/match_requests/firstrequest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player").value("andrew"))
                .andExpect(jsonPath("$.match_id", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        String matchId = JsonPath.read(body, "$.match_id");

        mockMvc.perform(get("/matches/" + matchId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.match_request_1_id").value("firstrequest"))
                .andExpect(jsonPath("$.match_request_2_id").value("secondrequest"));

        mockMvc.perform(post("/results").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"match_id\": \"" + matchId + "\", \"winner\": \"andrew\", \"loser\": \"navratilova\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/match_requests/unknown")).andExpect(status().isNotFound());
    }
}
