package org.pongmatcher.web;

import org.junit.jupiter.api.Test;
import org.pongmatcher.domain.Match;
import org.pongmatcher.repositories.MatchRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.hamcrest.Matchers.*;

final class MatchesControllerTest {
    private final MatchRepository matchRepository = mock(MatchRepository.class);
    private final MatchesController matchesController = new MatchesController(matchRepository);
    private final MockMvc mockMvc = standaloneSetup(this.matchesController).build();

    @Test
    void gettingNonExistentMatch404s() throws Exception {
        this.mockMvc.perform(get("/matches/nonexistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void gettingExistentMatch200s() throws Exception {
        Match match = new Match("foo", "bar", "baz");
        when(this.matchRepository.findById("mymatch")).thenReturn(Optional.of(match));

        this.mockMvc.perform(get("/matches/mymatch"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void gettingExistentMatchReturnsJson() throws Exception {
        Match match = new Match("foo", "bar", "baz");
        when(this.matchRepository.findById("mymatch")).thenReturn(Optional.of(match));

        this.mockMvc.perform(get("/matches/mymatch"))
                .andExpect(jsonPath("$.id", is("foo")))
                .andExpect(jsonPath("$.match_request_1_id", is("bar")))
                .andExpect(jsonPath("$.match_request_2_id", is("baz")));
    }
}
