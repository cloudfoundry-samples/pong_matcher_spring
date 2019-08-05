package org.pongmatcher.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public final class Result {

    @Id
    private  String id = UUID.randomUUID().toString();

    @JsonProperty("winner")
    private  String winnerId;

    @JsonProperty("loser")
    private  String loserId;

    @JsonProperty("match_id")
    private  String matchId;

    Result() {
    }

    public Result(String winnerId,
                  String loserId,
                  String matchId) {
        this.winnerId = winnerId;
        this.loserId = loserId;
        this.matchId = matchId;
    }


    public String getWinnerId() {
        return winnerId;
    }

    public String getLoserId() {
        return loserId;
    }

    public String getMatchId() {
        return matchId;
    }
}
