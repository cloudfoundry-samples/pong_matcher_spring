package org.pongmatcher.web;

import org.pongmatcher.domain.Result;
import org.pongmatcher.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
class ResultsController {

    private final ResultRepository resultRepository;

    @Autowired
    ResultsController(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, value = "/results")
    ResponseEntity<Result> save(@RequestBody Result result) {
        this.resultRepository.saveAndFlush(result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
