package org.pongmatcher.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MatchRequest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private volatile Long id;

    private volatile String uuid;

    private volatile String requesterId;

    MatchRequest() {
    }

    public MatchRequest(String uuid, String requesterId) {
        this.uuid = uuid;
        this.requesterId = requesterId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getRequesterId() {
        return requesterId;
    }

}
