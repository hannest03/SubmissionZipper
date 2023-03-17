package it.smallcode.submissionzipper.config;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

public class Config {
    private final HashMap<String, String> settings = new HashMap<>();

    private Config(){}

    public String getSetting(String key){
        if(!this.settings.containsKey(key)) return "";
        return this.settings.get(key);
    }

    protected void addSetting(String key, String value){
        this.settings.put(key, value);
    }

    public static Config load(Path path){
        Config config = new Config();
        try {
            FileReader fileReader = new FileReader(path.toAbsolutePath().toFile());
            try (fileReader; BufferedReader reader = new BufferedReader(fileReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("=");

                    String key = split[0];
                    String value = "";
                    if (split.length >= 2) value = split[1];

                    config.addSetting(key, value);
                }
            } catch (IOException ignored) {}
        } catch (FileNotFoundException ex) {
            System.out.println("Config file not found! " + path.toAbsolutePath());
            return null;
        }
        return config;
    }

}
