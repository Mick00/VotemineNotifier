package com.votemine;

import java.io.IOException;

public class Main {
    public static void main(String[] args){

        API api = new API("APITOKENHERE");
        try {
            System.out.println(api.getNewVotes().get(0).getVoter().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
