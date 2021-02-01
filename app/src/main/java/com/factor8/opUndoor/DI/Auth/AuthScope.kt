package com.factor8.opUndoor.DI.Auth


import javax.inject.Scope

/**
 * AuthScope is strictly for login, forgetPassword and registration
 */

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AuthScope