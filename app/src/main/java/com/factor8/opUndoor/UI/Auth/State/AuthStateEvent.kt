package com.factor8.opUndoor.UI.Auth.State

sealed class AuthStateEvent{

    data class LoginAttemptEvent(
            val email: String,
            val password: String
    ): AuthStateEvent()

    data class RegisterAttemptEvent(
            val email: String,
            val username: String,
            val password: String,
            val confirm_password: String
    ): AuthStateEvent()

    class CheckPreviousAuthEvent(): AuthStateEvent()
}