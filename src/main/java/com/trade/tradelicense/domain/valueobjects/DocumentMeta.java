package com.trade.tradelicense.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Value object capturing the metadata of an uploaded document file.
 *
 * <p>{@code DocumentMeta} is an embeddable value object used within the
 * {@link com.trade.tradelicense.domain.entities.Attachment} entity to group
 * all file-level metadata (name, MIME type, integrity checksum, and size) into
 * a single, cohesive concept.  Being a value object, two {@code DocumentMeta}
 * instances are considered equal if and only if all their fields are equal.
 *
 * <p>The {@code checksum} field holds a SHA-256 hex digest of the file content
 * and can be used to detect duplicate uploads or verify integrity after
 * transmission.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DocumentMeta {

    /**
     * Original file name as provided by the uploader (e.g. {@code "passport.pdf"}).
     */
    @Column(nullable = false)
    private final String fileName;

    /**
     * MIME content type of the uploaded file (e.g. {@code "application/pdf"},
     * {@code "image/jpeg"}).
     */
    @Column(nullable = false)
    private final String contentType;

    /**
     * SHA-256 hex digest of the file content, used for integrity verification
     * and duplicate detection.  May be {@code null} if checksum was not computed
     * at upload time.
     */
    @Column(length = 64)
    private final String checksum;

    /**
     * Size of the file in bytes; used for validation and storage quota
     * enforcement.
     */
    @Column(nullable = false)
    private final long sizeBytes;
}
