package org.pongmatcher.web;

import org.junit.jupiter.api.Test;
import org.pongmatcher.domain.Result;
import org.pongmatcher.repositories.ResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

final class ResultsControllerTest {

    private final ResultRepository resultRepository = mock(ResultRepository.class);

    private final ResultsController resultsController = new ResultsController(resultRepository);

    private final MockMvc mockMvc = standaloneSetup(this.resultsController).build();

    @Test
    void saveMvc() throws Exception {
        this.mockMvc.perform(post("/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"winner\": \"test-winner\", \"loser\": \"test-loser\", \"match_id\": \"test-match-id\"}"))
                .andExpect(status().isCreated());

        verify(this.resultRepository).saveAndFlush(any(Result.class));
    }

    @Test
    void saveUnit() {
        ResponseEntity<Result> response = this.resultsController.save(
                new Result("test-winner", "test-loser", "test-match-id"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(this.resultRepository).saveAndFlush(any(Result.class));
    }
}
