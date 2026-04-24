package com.trade.tradelicense.domain.factory;

import com.trade.tradelicense.domain.entities.Attachment;
import com.trade.tradelicense.domain.entities.DocumentType;
import com.trade.tradelicense.domain.valueobjects.DocumentMeta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Factory for creating {@link Attachment} instances from raw upload input.
 *
 * <p>Building an {@link Attachment} through this factory ensures that file
 * metadata is captured in a consistent way and that all required fields
 * (file name, content type, size, document type, and upload timestamp) are
 * always populated at construction time.
 *
 * <p>The factory does <em>not</em> persist the attachment; the caller must add
 * the returned object to the owning
 * {@link com.trade.tradelicense.domain.entities.TradeLicenseApplication}'s
 * attachment collection.
 *
 * @see Attachment
 * @see DocumentMeta
 */
@Component
public class AttachmentFactory {

    /**
     * Creates and returns a new {@link Attachment} for the supplied document
     * metadata and document type.
     *
     * @param meta         value object carrying the file metadata (name,
     *                     content-type, checksum, size); must not be {@code null}
     * @param documentType the logical category of the document within the workflow;
     *                     must not be {@code null}
     * @return a new, unsaved {@link Attachment}
     */
    public Attachment create(DocumentMeta meta, DocumentType documentType) {
        return Attachment.builder()
                .fileName(meta.getFileName())
                .contentType(meta.getContentType())
                .checksum(meta.getChecksum())
                .sizeBytes(meta.getSizeBytes())
                .documentType(documentType)
                .uploadedAt(LocalDateTime.now())
                .build();
    }
}
