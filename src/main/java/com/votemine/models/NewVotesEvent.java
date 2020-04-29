package com.votemine.models;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NewVotesEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private MineVote vote;

    public NewVotesEvent(MineVote vote) {
        this.vote = vote;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public MineVote getVote(){
        return this.vote;
    }
}
