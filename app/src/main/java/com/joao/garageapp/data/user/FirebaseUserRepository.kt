package com.joao.garageapp.data.user

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.joao.garageapp.domain.model.User
import com.joao.garageapp.util.COLLECTION_USER
import com.joao.garageapp.util.USER_STORAGE_IMAGE
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FirebaseUserRepository @Inject constructor(
    firebaseFirestore: FirebaseFirestore,
    firebaseStorage: FirebaseStorage,
    firebaseAuth: FirebaseAuth,
    ): UserDataSource {

    private val collectionReference = firebaseFirestore
        .collection(COLLECTION_USER)

    private val storageReference = firebaseStorage.reference

    private val mAuth = firebaseAuth



    override suspend fun uploadUserImage(userImg: Uri): String {
       return suspendCoroutine {  continuation ->
           val randomKey = UUID.randomUUID()
           val childReference = storageReference.child(
               "$USER_STORAGE_IMAGE/$randomKey"
           )
           childReference.putFile(userImg)
               .addOnSuccessListener { taskSnapshot ->
                   taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                       val path = uri.toString()
                       continuation.resumeWith(Result.success(path))
                   }
               }.addOnFailureListener { exception ->
                   continuation.resumeWith(Result.failure(exception))
               }
       }
    }



    override suspend fun createUserAuth(email: String, password: String): String? {
        return suspendCoroutine { continuation ->
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                     continuation.resumeWith(Result.success(mAuth.currentUser?.uid))
                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }

    override suspend fun createUser(user: User): User {
        return suspendCoroutine{ continuation ->
            collectionReference.document(user.userId)
                .set(user)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(user))
                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }

    override suspend fun loginUser(email: String, password: String): FirebaseUser? {
        return suspendCoroutine { continuation ->
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(mAuth.currentUser))
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }

    override suspend fun signOut() {
            mAuth.signOut()
    }

    override suspend fun getUser(userId: String): User {
        return suspendCoroutine { continuation ->
                collectionReference.document(userId)
                    .get()
                    .addOnSuccessListener { document ->
                        var user = User()
                        document?.toObject(User::class.java)?.run {
                            user = this
                        }
                        continuation.resumeWith(Result.success(user))
                    }
                    .addOnFailureListener { e ->
                        continuation.resumeWith(Result.failure(e))
                    }
        }
    }

    override suspend fun getUserLogged(): User? {
        return suspendCoroutine { continuation ->
            val user = mAuth.currentUser
            if (user != null) {
                val userId = user.uid
                collectionReference.document(userId)
                    .get()
                    .addOnSuccessListener { document ->
                        var u = User()
                        document?.toObject(User::class.java)?.run {
                            u = this
                        }
                        continuation.resumeWith(Result.success(u))
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWith(Result.failure(exception))
                    }
            } else {
                continuation.resumeWith(Result.success(null))
            }

        }

    }

    override suspend fun listUser(): List<User> {
        return suspendCoroutine { continuation ->
            collectionReference
                .get()
                .addOnSuccessListener { documents ->
                    val users = mutableListOf<User>()
                    for(document in documents) {
                       // if(document.id == ) {
                       //     continue
                       // }
                        document.toObject(User::class.java).run {
                            users.add(this)
                        }
                    }
                    continuation.resumeWith(Result.success(users))
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }

    }
}