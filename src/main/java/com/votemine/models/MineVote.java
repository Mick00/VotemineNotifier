package com.votemine.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MineVote {
    @JsonProperty("expires_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiresAt;
    @JsonProperty("claimed")
    private boolean claimed;

    @JsonProperty("by_player")
    private Voter byPlayer;

    public static List<MineVote> parseJsonVotes(InputStream stream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stream, new TypeReference<List<MineVote>>(){});
    }

    public MineVote(){}

    public Voter getVoter(){
        return byPlayer;
    }

    public Date getVoteExpirationDate(){
        return expiresAt;
    }

    public boolean isClaimed(){
        return claimed;
    }

}
