package com.votemine;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import com.votemine.models.MineVote;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

@CommandAlias("votemine")
@CommandPermission("votemine.admin")
public class Commands extends BaseCommand {

    private Votemine votemine;

    public Commands(Votemine votemine){
        this.votemine = votemine;
    }

    @Subcommand("reload")
    public void reloadConfig(@Optional Player player, String[] args){
        votemine.reloadVotemineConfig();
        Bukkit.getLogger().info("Minevote settings reloaded");
    }

    @Subcommand("fetch")
    public void fetchNewVotes(CommandSender player, String[] args){
        Bukkit.getLogger().info("Fetching new votes ");
        votemine.getNotifier().getNotificationChain()
                .syncLast(voters->{
                    player.sendMessage("We have fetch the new votes");
                }).execute();
    }

    @Subcommand("link|url|lien")
    public void sendLink(CommandSender sender, String[] args){
        //TODO: fetch server's link
        sender.sendMessage("Vote on this site");
    }
}
