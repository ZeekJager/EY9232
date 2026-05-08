package com.trade.tradelicense.api.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Generic API response envelope used by all REST endpoints.
 *
 * <p>Wraps the actual response payload ({@code data}), a human-readable
 * {@code message}, and a {@code success} flag so that clients can uniformly
 * inspect the outcome of any request.
 *
 * @param <T> the type of the response payload
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /** The response payload; may be {@code null} for error responses. */
    private T data;

    /** A human-readable description of the outcome. */
    private String message;

    /** {@code true} if the operation succeeded; {@code false} otherwise. */
    private boolean success;

    /**
     * Creates a successful response with the given data and a default message.
     *
     * @param data the payload to include
     * @param <T>  the payload type
     * @return a successful {@code ApiResponse}
     */
    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .message("Operation successful")
                .success(true)
                .build();
    }

    /**
     * Creates a successful response with the given data and a custom message.
     *
     * @param data    the payload to include
     * @param message a human-readable success message
     * @param <T>     the payload type
     * @return a successful {@code ApiResponse}
     */
    public static <T> ApiResponse<T> ok(T data, String message) {
        return ApiResponse.<T>builder()
                .data(data)
                .message(message)
                .success(true)
                .build();
    }

    /**
     * Creates a failed response with no payload and the given error message.
     *
     * @param message a human-readable error description
     * @param <T>     the (absent) payload type
     * @return a failed {@code ApiResponse}
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .data(null)
                .message(message)
                .success(false)
                .build();
    }
}
