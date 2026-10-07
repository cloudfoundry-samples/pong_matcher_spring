package org.pongmatcher.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pong_match")
public class Match {
    @Id
    private volatile String id;

    @JsonProperty("match_request_1_id")
    private volatile String matchRequest1Id;

    @JsonProperty("match_request_2_id")
    private volatile String matchRequest2Id;

    Match() {
    }

    public Match(String id, String matchRequest1Id, String matchRequest2Id) {
        this.id = id;
        this.matchRequest1Id = matchRequest1Id;
        this.matchRequest2Id = matchRequest2Id;
    }

    public String getId() {
        return id;
    }

    public String getMatchRequest1Id() {
        return matchRequest1Id;
    }

    public String getMatchRequest2Id() {
        return matchRequest2Id;
    }

}
