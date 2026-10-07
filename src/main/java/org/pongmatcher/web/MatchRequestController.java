package org.pongmatcher.web;

import org.pongmatcher.domain.Match;
import org.pongmatcher.domain.MatchRequest;
import org.pongmatcher.repositories.MatchRepository;
import org.pongmatcher.repositories.MatchRequestRepository;
import org.pongmatcher.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
class MatchRequestController {

    private final OpenMatchFinder openMatchFinder;


    private final MatchRequestRepository matchRequestRepository;

    private final MatchRepository matchRepository;

    private final ResultRepository resultRepository;

    @Autowired
    MatchRequestController(OpenMatchFinder openMatchFinder, MatchRequestRepository matchRequestRepository,
                           MatchRepository matchRepository, ResultRepository resultRepository) {
        this.openMatchFinder = openMatchFinder;
        this.matchRequestRepository = matchRequestRepository;
        this.matchRepository = matchRepository;
        this.resultRepository = resultRepository;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/match_requests/{id}")
    MatchRequest save(@PathVariable String id, @RequestBody Map<String, String> body) {
        MatchRequest matchRequest = new MatchRequest(id, body.get("player"));
        matchRequestRepository.save(matchRequest);

        Optional<MatchRequest> openMatchRequest = this.openMatchFinder.firstOpenMatchRequest(
                matchRequest.getRequesterId());

        if (openMatchRequest.isPresent()) {
            recordMatch(openMatchRequest.get(), matchRequest);
        }

        return matchRequest;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/match_requests/{id}")
    ResponseEntity<Map<String, String>> show(@PathVariable String id) {
        MatchRequest matchRequest = matchRequestRepository.findByUuid(id);

        if (matchRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Match match = matchRepository.findByMatchRequest1IdOrMatchRequest2Id(id, id);
            Map<String, String> body = new HashMap<>();

            body.put("id", matchRequest.getUuid());
            body.put("player", matchRequest.getRequesterId());

            if (match == null) {
                body.put("match_id", null);
            } else {
                body.put("match_id", match.getId());
            }

            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }

    private void recordMatch(MatchRequest openMatchRequest, MatchRequest newMatchRequest) {
        matchRepository.save(
                new Match(UUID.randomUUID().toString(), openMatchRequest.getUuid(), newMatchRequest.getUuid())
        );
    }
}
