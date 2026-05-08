package com.trade.tradelicense.application.exceptions;

/**
 * Thrown when a requested workflow action is not permitted given the current
 * state of the application or the role of the acting user.
 *
 * <p>The {@link com.trade.tradelicense.api.common.GlobalExceptionHandler} maps this
 * exception to an HTTP {@code 409 Conflict} response.
 */
public class InvalidWorkflowActionException extends RuntimeException {

    /**
     * Constructs the exception with a descriptive message.
     *
     * @param message human-readable explanation of why the action is invalid
     */
    public InvalidWorkflowActionException(String message) {
        super(message);
    }

    /**
     * Convenience factory for a status-mismatch scenario.
     *
     * @param action         the action that was attempted
     * @param currentStatus  the current application status
     * @return a new {@code InvalidWorkflowActionException}
     */
    public static InvalidWorkflowActionException statusMismatch(Object action, Object currentStatus) {
        return new InvalidWorkflowActionException(
                "Action '" + action + "' is not permitted when status is '" + currentStatus + "'");
    }
}
