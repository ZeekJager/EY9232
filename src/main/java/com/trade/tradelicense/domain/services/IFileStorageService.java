package com.trade.tradelicense.domain.services;

/**
 * Domain service interface for storing and retrieving binary file content.
 *
 * <p>Implementations are responsible for persisting uploaded documents (e.g.
 * to the local file system, an object store, or a cloud bucket) and returning
 * a stable path/URL that can later be used to retrieve or delete the content.
 */
public interface IFileStorageService {

    /**
     * Stores the given binary content and returns a stable path or URL that
     * identifies the stored object.
     *
     * @param fileName    the original file name (used for metadata / extension hints);
     *                    must not be {@code null}
     * @param contentType the MIME type of the content (e.g. {@code "application/pdf"});
     *                    must not be {@code null}
     * @param content     the raw file bytes; must not be {@code null}
     * @return a storage path or URL that can be passed back to {@link #retrieve} or
     *         {@link #delete}
     */
    String store(String fileName, String contentType, byte[] content);

    /**
     * Retrieves the binary content previously stored at the given path.
     *
     * @param storagePath the path or URL returned by a prior {@link #store} call;
     *                    must not be {@code null}
     * @return the raw file bytes
     * @throws java.util.NoSuchElementException if no file exists at the given path
     */
    byte[] retrieve(String storagePath);

    /**
     * Permanently removes the content stored at the given path.
     *
     * @param storagePath the path or URL returned by a prior {@link #store} call;
     *                    must not be {@code null}
     */
    void delete(String storagePath);
}
