package com.votemine;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class VotemineConfig {

    private Plugin votemine;
    private FileConfiguration config;
    private String TOKEN_PATH = "ApiToken";
    private String token;

    public VotemineConfig(Plugin votemine){
        this.votemine = votemine;
        this.config = votemine.getConfig();
        setDefaults();
        read();
    }

    private void setDefaults(){
        Bukkit.getLogger().info("Adding defaults");
        config.addDefault(TOKEN_PATH, "YOURAPITOKENHERE");
        config.options().copyDefaults(true);
        votemine.saveConfig();
    }

    public void reload(){
        votemine.reloadConfig();
        this.config = votemine.getConfig();
        read();
    }

    private void read(){
        token = config.getString(TOKEN_PATH);
    }

    public String getToken(){
        return token;
    }
}
