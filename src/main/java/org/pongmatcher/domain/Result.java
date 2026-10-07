package org.pongmatcher.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class Result {

    @Id
    private volatile String id = UUID.randomUUID().toString();

    private volatile String winnerId;

    private volatile String loserId;

    private volatile String matchId;

    Result() {
    }

    public Result(@JsonProperty("winner") String winnerId,
                  @JsonProperty("loser") String loserId,
                  @JsonProperty("match_id") String matchId) {
        this.winnerId = winnerId;
        this.loserId = loserId;
        this.matchId = matchId;
    }

    @JsonProperty("winner")
    public String getWinnerId() {
        return winnerId;
    }

    @JsonProperty("loser")
    public String getLoserId() {
        return loserId;
    }

    @JsonProperty("match_id")
    public String getMatchId() {
        return matchId;
    }
}
