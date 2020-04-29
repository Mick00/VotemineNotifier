package com.votemine;

import co.aikar.commands.PaperCommandManager;
import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public class Votemine extends JavaPlugin {
    private static TaskChainFactory taskChainFactory;
    private VoteNotifier notifier;
    private API api;
    private VotemineConfig config;

    public void onEnable(){
        taskChainFactory = BukkitTaskChainFactory.create(this);
        registerCommands();
        config = new VotemineConfig(this);
        api = new API(config.getToken());
        notifier = new VoteNotifier(api);
        notifier.runTaskTimerAsynchronously(this,1200,12000);
    }

    private void registerCommands(){
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.addSupportedLanguage(Locale.FRENCH);
        manager.getLocales().setDefaultLocale(Locale.FRENCH);
        manager.registerCommand(new Commands(this));
    }

    public void onDisable(){
        notifier.cancel();
    }

    public void reloadVotemineConfig(){
        config.reload();
        Bukkit.getLogger().info("Token"+config.getToken());
        api.setBearerToken(config.getToken());
    }

    public VoteNotifier getNotifier() { return notifier; }
    public API getApi() { return api; }
    public static <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }
    public static <T> TaskChain<T> newSharedChain(String name) {
        return taskChainFactory.newSharedChain(name);
    }
}
