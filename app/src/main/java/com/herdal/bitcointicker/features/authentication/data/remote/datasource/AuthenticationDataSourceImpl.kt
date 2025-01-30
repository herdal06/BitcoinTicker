package com.herdal.bitcointicker.features.authentication.data.remote.datasource

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.SignInMethodQueryResult
import com.herdal.bitcointicker.core.data.remote.Error
import com.herdal.bitcointicker.core.data.remote.IResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthenticationDataSource {
    override suspend fun registerUser(email: String, password: String): IResult<FirebaseUser> {
        return try {
            val currentUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            currentUser.user?.let {
                IResult.Success(it)
            } ?: IResult.Failure(Error.AuthError.UnknownAuthError("User registration failed"))
        } catch (e: FirebaseAuthException) {
            IResult.Failure(
                Error.AuthError.UnknownAuthError(
                    e.localizedMessage ?: "Registration failed"
                )
            )
        } catch (e: Exception) {
            IResult.Failure(Error.AuthError.UnknownAuthError(e.localizedMessage ?: "Unknown error"))
        }
    }

    override suspend fun loginUser(email: String, password: String): IResult<FirebaseUser> {
        return try {
            val currentUser = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            currentUser.user?.let {
                IResult.Success(it)
            } ?: IResult.Failure(Error.AuthError.UnknownAuthError("Login failed"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            IResult.Failure(Error.AuthError.InvalidCredentials("Invalid credentials"))
        } catch (e: FirebaseAuthInvalidUserException) {
            IResult.Failure(Error.AuthError.UserNotFound("User not found"))
        } catch (e: FirebaseAuthException) {
            IResult.Failure(Error.AuthError.UnknownAuthError(e.localizedMessage ?: "Login failed"))
        } catch (e: Exception) {
            IResult.Failure(Error.AuthError.UnknownAuthError(e.localizedMessage ?: "Unknown error"))
        }
    }

    override suspend fun checkIfEmailExists(email: String): IResult<Boolean> {
        return try {
            val task: Task<SignInMethodQueryResult> = firebaseAuth.fetchSignInMethodsForEmail(email)
            val result: SignInMethodQueryResult = Tasks.await(task)
            val signInMethods = result.signInMethods
            IResult.Success(!signInMethods.isNullOrEmpty())
        } catch (e: FirebaseAuthException) {
            IResult.Failure(
                Error.AuthError.UnknownAuthError(
                    e.localizedMessage ?: "Error checking email"
                )
            )
        } catch (e: Exception) {
            IResult.Failure(Error.AuthError.UnknownAuthError(e.localizedMessage ?: "Unknown error"))
        }
    }
}