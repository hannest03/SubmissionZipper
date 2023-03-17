package it.smallcode.submissionzipper.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
    private final Path filePath;
    private final List<String> files;

    public Zipper(Path filePath, List<String> files) {
        this.filePath = filePath;
        this.files = files;
    }

    public void save(){
        File zipFile = new File(filePath.toUri());
        try {
            zipFile.createNewFile();
        } catch (IOException ignored) {}

        try {
            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFile));

            for(String path : this.files){
                createEntry(path, outputStream);
            }

            outputStream.close();
        } catch (IOException ignored) {}
    }

    private void createEntry(String path, ZipOutputStream zipOutputStream){
        File file = new File(path);
        if(!file.exists()) {
            System.out.println("Couldn't find file: " + path);
            return;
        }
        ZipEntry entry = new ZipEntry(path);

        byte[] data = new byte[]{};
        try {
            data = Files.readAllBytes(Path.of(path));
        } catch (IOException ex) {
            System.out.println("Couldn't read file data: " + ex.getMessage());
        }

        try {
            zipOutputStream.putNextEntry(entry);

            zipOutputStream.write(data, 0, data.length);

            zipOutputStream.closeEntry();
        } catch (IOException ignored) {}
    }

}
