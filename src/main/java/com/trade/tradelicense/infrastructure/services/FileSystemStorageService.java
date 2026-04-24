package com.trade.tradelicense.infrastructure.services;

import com.trade.tradelicense.domain.services.IFileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * {@link IFileStorageService} implementation that persists files on the local
 * file system under a configurable base directory.
 *
 * <p>The storage path for each file is structured as
 * {@code <basePath>/<uuid>_<originalFileName>}.
 *
 * <p>This implementation is suitable for development and single-node deployments.
 * For production multi-node environments consider replacing it with an object
 * store (e.g. AWS S3, Azure Blob Storage).
 */
@Slf4j
@Service
public class FileSystemStorageService implements IFileStorageService {

    private final Path basePath;

    /**
     * Constructs the service and ensures the base storage directory exists.
     *
     * @param basePath the root directory under which files are stored;
     *                 defaults to {@code ./uploads} if the property is not set
     */
    public FileSystemStorageService(
            @Value("${storage.base-path:./uploads}") String basePath) {
        this.basePath = Paths.get(basePath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.basePath);
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to create storage directory: " + this.basePath, e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The file is stored as {@code <uuid>_<fileName>} inside the base directory.
     * The returned path is relative to the base directory.
     */
    @Override
    public String store(String fileName, String contentType, byte[] content) {
        String storageName = UUID.randomUUID() + "_" + Instant.now().toEpochMilli() + "_" + sanitize(fileName);
        Path target = basePath.resolve(storageName);
        try {
            Files.write(target, content);
            log.info("Stored file '{}' as '{}'", fileName, storageName);
            return storageName;
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to store file: " + fileName, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] retrieve(String storagePath) {
        Path target = basePath.resolve(storagePath).normalize();
        if (!Files.exists(target)) {
            throw new NoSuchElementException("File not found at storage path: " + storagePath);
        }
        try {
            return Files.readAllBytes(target);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read file: " + storagePath, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String storagePath) {
        Path target = basePath.resolve(storagePath).normalize();
        try {
            boolean deleted = Files.deleteIfExists(target);
            if (deleted) {
                log.info("Deleted file at '{}'", storagePath);
            } else {
                log.warn("File not found for deletion at '{}'", storagePath);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to delete file: " + storagePath, e);
        }
    }

    private String sanitize(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
