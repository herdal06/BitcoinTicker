package com.herdal.bitcointicker.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.herdal.bitcointicker.core.data.remote.Error
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.data.firebase.datasource.CoinFirebaseDataSourceImpl
import com.herdal.bitcointicker.features.coin.data.firebase.model.FavoriteCoinFirebaseModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CoinFirebaseDataSourceImplTest {
    private lateinit var coinFirebaseDataSource: CoinFirebaseDataSourceImpl
    private lateinit var firestore: FirebaseFirestore
    private val testUserId = "3z1K6XOWU7dvmtIQrVTW3GaF7np2"
    private val testCoinId = "bitcoin"

    private fun <T> mockTask(result: T): Task<T> = mockk {
        coEvery { await() } returns result
    }

    @Before
    fun setup() {
        firestore = mockk()
        coinFirebaseDataSource = CoinFirebaseDataSourceImpl(firestore)
    }

    @Test
    fun `getFavoriteCoins when successful should return Success with list`() = runTest {
        // Given
        val collectionReference = mockk<CollectionReference>()
        val documentReference = mockk<DocumentReference>()
        val querySnapshot = mockk<QuerySnapshot>()
        val documents = listOf(mockk<DocumentSnapshot>())
        val favoriteCoin = FavoriteCoinFirebaseModel(id = testCoinId)

        every { firestore.collection("users") } returns collectionReference
        every { collectionReference.document(testUserId) } returns documentReference
        every { documentReference.collection("favorites") } returns collectionReference
        every { collectionReference.get() } returns mockTask(querySnapshot)
        every { querySnapshot.documents } returns documents
        every { documents[0].toObject(FavoriteCoinFirebaseModel::class.java) } returns favoriteCoin
        every { documents[0].id } returns testCoinId

        // When
        val result = coinFirebaseDataSource.getFavoriteCoins(testUserId)

        // Then
        assertTrue(result is IResult.Success)
        assertEquals(listOf(favoriteCoin), (result as IResult.Success).data)
    }

    @Test
    fun `getFavoriteCoins when error occurs should return Failure`() = runTest {
        // Given
        val errorMessage = "Firestore error"
        every { firestore.collection("users") } throws Exception(errorMessage)

        // When
        val result = coinFirebaseDataSource.getFavoriteCoins(testUserId)

        // Then
        assertTrue(result is IResult.Failure)
        assertTrue((result as IResult.Failure).error is Error.FirestoreError.UnknownFirestoreError)
        assertEquals(
            errorMessage,
            (result.error as Error.FirestoreError.UnknownFirestoreError).message
        )
    }

    @Test
    fun `isCoinFavorite when coin exists should return Success with true`() = runTest {
        // Given
        val collectionReference = mockk<CollectionReference>()
        val documentReference = mockk<DocumentReference>()
        val documentSnapshot = mockk<DocumentSnapshot>()

        every { firestore.collection("users") } returns collectionReference
        every { collectionReference.document(testUserId) } returns documentReference
        every { documentReference.collection("favorites") } returns collectionReference
        every { collectionReference.document(testCoinId) } returns documentReference
        every { documentReference.get() } returns mockTask(documentSnapshot)
        every { documentSnapshot.exists() } returns true

        // When
        val result = coinFirebaseDataSource.isCoinFavorite(testUserId, testCoinId)

        // Then
        assertTrue(result is IResult.Success)
        assertTrue((result as IResult.Success).data)
    }

    @Test
    fun `isCoinFavorite when coin doesn't exist should return Success with false`() = runTest {
        // Given
        val collectionReference = mockk<CollectionReference>()
        val documentReference = mockk<DocumentReference>()
        val documentSnapshot = mockk<DocumentSnapshot>()

        every { firestore.collection("users") } returns collectionReference
        every { collectionReference.document(testUserId) } returns documentReference
        every { documentReference.collection("favorites") } returns collectionReference
        every { collectionReference.document(testCoinId) } returns documentReference
        every { documentReference.get() } returns mockTask(documentSnapshot)
        every { documentSnapshot.exists() } returns false

        // When
        val result = coinFirebaseDataSource.isCoinFavorite(testUserId, testCoinId)

        // Then
        assertTrue(result is IResult.Success)
        assertFalse((result as IResult.Success).data)
    }
}