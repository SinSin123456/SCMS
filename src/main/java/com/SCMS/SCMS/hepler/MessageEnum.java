package com.SCMS.SCMS.hepler;

public enum MessageEnum {
    SUCCESS("Success."),
    NO_DATA("No data found."),
    INTERNAL_SERVER_ERROR("Internal server error."),
    USER_CREATED_SUCCESS("User created successfully."),
    USER_UPDATED_SUCCESS("User updated successfully."),
    USER_NOT_FOUND("User not found."),
    USER_CREATION_FAILED("Failed to create user."),
    INVALID_INPUT("Invalid input provided.");

    private final String message;

    MessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
