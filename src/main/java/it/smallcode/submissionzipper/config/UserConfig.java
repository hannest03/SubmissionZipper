package it.smallcode.submissionzipper.config;

import java.nio.file.Path;

public class UserConfig{
    private Config config;

    private UserConfig(){}

    public String getZidUser(){
        if(config == null) return "";
        return config.getSetting("zidUsername");
    }

    public String getGroupNr(){
        if(config == null) return "";
        int number = Integer.parseInt(config.getSetting("gXX"));
        return String.format("%02d", number);
    }

    protected void loadConfig(Path path){
        this.config = Config.load(path);
    }

    public static UserConfig load(Path path){
        UserConfig userConfig = new UserConfig();
        userConfig.loadConfig(path);
        return userConfig;
    }
}
