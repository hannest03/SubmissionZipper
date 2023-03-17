package it.smallcode.submissionzipper;

import it.smallcode.submissionzipper.config.RequiredFilesConfig;
import it.smallcode.submissionzipper.config.UserConfig;
import it.smallcode.submissionzipper.zip.Zipper;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class SubmissionZipper {
    public static void main(String[] args){
        if(args.length < 2){
            System.out.println("usage: <userConfigPath> <requiredFilePath>");
            return;
        }
        final Path userConfigPath;
        final Path requiredFilePath;
        try{
            userConfigPath = Path.of(args[0]);
            requiredFilePath = Path.of(args[1]);
        }catch(InvalidPathException ex){
            System.out.println("Invalid path: " + ex.getMessage());
            return;
        }

        System.out.println("Loading user config");
        final UserConfig userConfig = UserConfig.load(userConfigPath);

        System.out.println("Loading required files config");
        final RequiredFilesConfig requiredFilesConfig = RequiredFilesConfig.load(requiredFilePath, userConfig);

        System.out.println("\nFiles to zip:");
        for(String l : requiredFilesConfig.getRequiredFiles()){
            System.out.println(l);
        }

        System.out.println("\nStarting zipping");
        Zipper zipper = new Zipper(Path.of("submission.zip"), requiredFilesConfig.getRequiredFiles());
        zipper.save();
        System.out.println("Done.");
    }
}
