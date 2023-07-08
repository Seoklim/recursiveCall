package kr.co.mz.filecleaner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class FileCleanerMain {

    private static final String TARGET_PATH = "/Users/mz01-megalim/downloads";

    public static void main(String[] args) {
        File mainDirectory = new File(TARGET_PATH);


        cycle(mainDirectory);


    }

    private static void cycle(File file) {
        File[] files = file.listFiles();

        if (files == null) {
            System.out.println("시스템 오류 입니다.");
            return;
        }
        if (file.length() == 0) {
            return;
        }
        for (File downFile : files) {

            if (downFile.isDirectory()) {
                cycle(downFile);
            } else {
                moveFile(downFile);


            }

        }
    }

    private static void moveFile(File downFile) {
        Path path = Paths.get(downFile.getAbsolutePath());
        BasicFileAttributes attrs;
        try {
            attrs = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileTime creationTime = attrs.creationTime();
        Instant now = Instant.now();
        Instant creationTimeInstant = creationTime.toInstant();
        long daysDifference = ChronoUnit.DAYS.between(creationTimeInstant, now);
        System.out.println(daysDifference);
    }
}
