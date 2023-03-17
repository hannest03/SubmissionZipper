package it.smallcode.submissionzipper.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class RequiredFilesConfig {
    protected final List<String> requiredFiles = new LinkedList<>();

    private RequiredFilesConfig(){}

    public List<String> getRequiredFiles(){
        return this.requiredFiles;
    }

    protected void loadConfig(Path path, UserConfig userConfig){
        final String zidUser = userConfig.getZidUser();
        final String groupNr = "g" + userConfig.getGroupNr();

        try {
            FileReader fileReader = new FileReader(path.toAbsolutePath().toFile());
            try (fileReader; BufferedReader reader = new BufferedReader(fileReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("zidUsername", zidUser);
                    line = line.replaceAll("gXX", groupNr);
                    requiredFiles.add(line);
                }
            } catch (IOException ignored) {}
        } catch (FileNotFoundException ex) {
            System.out.println("Config file not found! " + path.toAbsolutePath());
        }
    }

    public static RequiredFilesConfig load(Path path, UserConfig userConfig){
        final RequiredFilesConfig requiredFilesConfig = new RequiredFilesConfig();
        requiredFilesConfig.loadConfig(path, userConfig);
        return requiredFilesConfig;
    }
}
