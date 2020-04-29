package com.votemine;

import co.aikar.taskchain.TaskChain;
import com.vexsoftware.votifier.NuVotifierBukkit;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import com.vexsoftware.votifier.platform.VotifierPlugin;
import com.votemine.models.NewVotesEvent;
import com.votemine.models.MineVote;
import com.votemine.models.Voter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.List;

public class VoteNotifier extends BukkitRunnable {
    private API api;
    private boolean useVotifier;

    public VoteNotifier(API api){
        this.api = api;
        useVotifier = votifierIsInstalled();
    }

    private boolean votifierIsInstalled(){
        VotifierPlugin votifier = (NuVotifierBukkit) Bukkit.getServer().getPluginManager().getPlugin("Votifier");
        return votifier != null;
    }

    public TaskChain<List<MineVote>> getNotificationChain(){
        return Votemine.newChain()
                .asyncFirst(()-> {
                    try {
                        return api.getNewVotes();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).sync(votes->{
                    votes.forEach(vote -> {
                        if (useVotifier){
                            callVotifierEvent(vote);
                        }
                        callVotemineEvent(vote);
                    });
                    return votes;
                });
    }

    @Override
    public void run() {
        getNotificationChain().execute();
    }

    private void callVotifierEvent(MineVote vote){
        Bukkit.getPluginManager().callEvent(new VotifierEvent(convertToVotifierVote(vote)));
    }
    private Vote convertToVotifierVote(MineVote vote){
        Voter voter = vote.getVoter();
        return new com.vexsoftware.votifier.model.Vote("VoteMine",
                voter.getName(),
                voter.getIpv4()!=null?voter.getIpv4():voter.getIpv6(),
               String.valueOf(System.currentTimeMillis()));

    }

    private void callVotemineEvent(MineVote vote){
        Bukkit.getPluginManager().callEvent(new NewVotesEvent(vote));
    }
}
