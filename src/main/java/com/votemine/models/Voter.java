package com.votemine.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vexsoftware.votifier.model.Vote;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Voter {
    private String name;
    private String ipv4;
    private String ipv6;

    public String getName() {
        return name;
    }

    public String getIpv4() {
        return ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public static List<Voter> parseJsonVoters(InputStream stream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stream, new TypeReference<List<Voter>>(){});
    }

}
