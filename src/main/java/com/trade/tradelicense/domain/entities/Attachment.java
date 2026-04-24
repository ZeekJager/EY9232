package com.trade.tradelicense.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a document uploaded and attached to a
 * {@link TradeLicenseApplication} as evidence or supporting material.
 *
 * <p>{@code Attachment} is owned by the {@link TradeLicenseApplication}
 * aggregate and must not be shared across multiple applications. The
 * {@link DocumentType} categorises the attachment so that the aggregate can
 * verify that all documents required by the chosen {@link TradeLicenseType}
 * have been provided before submission.
 */
@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {

    /**
     * Surrogate primary key identifying this attachment instance.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Original file name as supplied by the uploader (e.g. "passport.pdf").
     */
    @Column(nullable = false)
    private String fileName;

    /**
     * MIME content type of the uploaded file (e.g. "application/pdf",
     * "image/jpeg").
     */
    @Column(nullable = false)
    private String contentType;

    /**
     * SHA-256 hex digest of the file content, used for integrity verification
     * and duplicate detection.  May be {@code null} if not computed at upload time.
     */
    @Column(length = 64)
    private String checksum;

    /**
     * File size in bytes, used for validation and storage quota enforcement.
     */
    @Column(nullable = false)
    private long sizeBytes;

    /**
     * Logical category of this document within the workflow.
     * The {@link TradeLicenseApplication} aggregate uses this to determine
     * whether all {@link TradeLicenseType#getRequiredDocumentTypes()} have
     * been satisfied.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    /**
     * Timestamp at which the file was uploaded to the system.
     */
    @Column(nullable = false)
    private LocalDateTime uploadedAt;
}
