package org.pongmatcher.web;

import org.pongmatcher.domain.Match;
import org.pongmatcher.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MatchesController {

    private final MatchRepository matchRepository;

    @Autowired
    MatchesController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/matches/{id}")
    ResponseEntity<Match> show(@PathVariable String id) {
        Match match = matchRepository.findById(id).orElse(null);

        if (match == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(match, HttpStatus.OK);
        }
    }

}
