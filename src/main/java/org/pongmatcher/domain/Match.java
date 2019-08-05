package org.pongmatcher.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`match`")
public final class Match {
    @Id
    private String id;

    @JsonProperty("match_request_1_id")
    private String matchRequest1Id;

    @JsonProperty("match_request_2_id")
    private String matchRequest2Id;

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
