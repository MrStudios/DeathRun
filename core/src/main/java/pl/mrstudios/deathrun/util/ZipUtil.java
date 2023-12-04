package pl.mrstudios.deathrun.util;

import pl.mrstudios.deathrun.exception.PluginCriticalException;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    private ZipUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    public static void zip(File outFile, Path[] entries) {

        try {

            if (!outFile.exists())
                if (!outFile.getParentFile().exists())
                    if (!outFile.getParentFile().mkdirs())
                        throw new PluginCriticalException("Unable to create " + outFile.getName() + " directory.");

            if (!outFile.exists())
                if (!outFile.createNewFile())
                    throw new PluginCriticalException("Unable to create " + outFile.getName() + " file.");

        } catch (Exception ignored) {}

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outFile))) {
            for (Path path : entries)
                Files.walkFileTree(path, new SimpleFileVisitor<>() {

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                        zipOutputStream.putNextEntry(new ZipEntry(path.relativize(file).toString()));
                        Files.copy(file, zipOutputStream);
                        zipOutputStream.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) throws IOException {
                        zipOutputStream.putNextEntry(new ZipEntry(path.relativize(directory) + "/"));
                        zipOutputStream.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }

                });

        } catch (Exception ignored) {}

    }

    public static void unzip(File file, Path destination) {

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {

            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {

                Path filePath = Paths.get(destination.toString(), zipEntry.getName());

                if (!zipEntry.isDirectory())
                    try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                        int length;
                        byte[] buffer = new byte[4096];
                        while ((length = zipInputStream.read(buffer)) > 0)
                            outputStream.write(buffer, 0, length);
                    }

                if (zipEntry.isDirectory())
                    Files.createDirectories(filePath);

                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();

            }

        } catch (Exception exception) {
            throw new PluginCriticalException("Unable to unzip " + file.getName() + " file to " + destination.toString() + " directory.", exception);
        }

    }

}
