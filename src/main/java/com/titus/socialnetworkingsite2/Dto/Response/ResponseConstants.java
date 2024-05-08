package com.titus.socialnetworkingsite2.Dto.Response;

public final class ResponseConstants {

    public static final String SIGN_UP_SUCCESS = "Signed up successfully, " +
            "check your inbox for confirmation email, it may take a while.";
    public static final String PASSWORD_CHANGE_SUCCESS = "Password has been reset successfully";
    public static String AUTH_SUCCESS = "Authentication successful";
    public static String ACCOUNT_ACTIVATION_SUCCESSFULLY = "Account Activated Successfully!";
    public static String SUCCESSFULLY_ADDED_TO_BLACKLIST = "Successfully added to BlackList";
    public static String SUCCESSFULLY_REMOVE_FROM_BLACKLIST = "Successfully removed from BlackList";
    public static String PASSWORD_IS_INCORRECT = "Your current password is incorrect.";
    public static String INVALID_RECIPIENT_EMAIL_ADDRESS = "Invalid recipient email address";
    public static String INVITE_SENT_SUCCESSFULLY = "Invite sent successfully to ";


    private ResponseConstants() {
        throw new IllegalStateException("Utility class");
    }
}