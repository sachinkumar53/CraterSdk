package com.crater.android.core.data.cache

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.crater.android.data.model.profile.UserDetails
import com.crater.android.feature.profile.data.UserDetailSerializer
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import io.github.osipxd.datastore.encrypted.createEncrypted
import io.github.osipxd.datastore.encrypted.encrypted
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SecureCacheManager(context: Context) : CacheManager {

    private val aead = AndroidKeysetManager.Builder()
        .withSharedPref(context, "master_keyset", "master_key_preference")
        .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
        .withMasterKeyUri("android-keystore://master_key")
        .build()
        .keysetHandle
        .getPrimitive(Aead::class.java)


    private val dataStore = PreferenceDataStoreFactory.createEncrypted(aead) {
        context.preferencesDataStoreFile(PREFERENCES_NAME)
    }

    private val userDataStore = DataStoreFactory.create(
        UserDetailSerializer().encrypted(aead)
    ) {
        context.dataStoreFile("user_details.json")
    }


    override suspend fun setUserDetails(userDetails: UserDetails) {
        userDataStore.updateData { userDetails }
    }

    override fun getUserDetails(): Flow<UserDetails> {
        return userDataStore.data
    }

    override fun getPhylloUserId(): Flow<String?> {
        return dataStore.data.map { it[KEY_PHYLLO_USER_ID] }
    }

    override fun getPhylloSdkToken(): Flow<String?> {
        return dataStore.data.map { it[KEY_PHYLLO_SDK_TOKEN] }
    }

    override fun getInstagramAccountId(): Flow<String?> {
        return dataStore.data.map { it[KEY_INSTAGRAM_ACCOUNT_ID] }
    }

    override fun getYoutubeAccountId(): Flow<String?> {
        return dataStore.data.map { it[KEY_YOUTUBE_ACCOUNT_ID] }
    }

    override suspend fun clearCache() {
        dataStore.edit { it.clear() }
        userDataStore.updateData { UserDetails() }
    }

    override suspend fun setPhylloUserId(id: String) {
        dataStore.edit {
            it[KEY_PHYLLO_USER_ID] = id
        }
    }

    override suspend fun setPhylloSdkToken(token: String) {
        dataStore.edit {
            it[KEY_PHYLLO_SDK_TOKEN] = token
        }
    }

    override suspend fun setYoutubeAccountId(id: String) {
        dataStore.edit {
            it[KEY_YOUTUBE_ACCOUNT_ID] = id
        }
    }

    override suspend fun setInstagramAccountId(id: String) {
        dataStore.edit {
            it[KEY_INSTAGRAM_ACCOUNT_ID] = id
        }
    }

    override suspend fun removeInstagramAccountId() {
        dataStore.edit {
            if (it.contains(KEY_INSTAGRAM_ACCOUNT_ID))
                it.remove(KEY_INSTAGRAM_ACCOUNT_ID)
        }
    }

    override suspend fun removeYoutubeAccountId() {
        dataStore.edit {
            if (it.contains(KEY_YOUTUBE_ACCOUNT_ID))
                it.remove(KEY_YOUTUBE_ACCOUNT_ID)
        }
    }

    override suspend fun setAccessToken(token: String) {
        dataStore.edit { it[KEY_ACCESS_TOKEN] = token }
    }

    override fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { it[KEY_ACCESS_TOKEN] }
    }


    /*private object Key {
        const val SIGNUP_KEY = "signup"
        const val PREF_NAME: String = "PF"
        const val PREF_NAME_STATIC = "userData"
        const val USER_ID = "userID"
        const val LOGIN_KEY = "login"
        const val LOGIN_ARRAY_KEY = "loginArray"
        const val ACCESS_TOKEN = "tokenID"
        const val ACCOUNT_ID = "accountID"
        const val KEY_USER_ID = "UserID"

        const val PHYLLO_USER_ID = "phylloUserID"
        const val PHYLLO_SDK_TOKEN = "phylloTokenID"
        const val PHYLLO_KEY_USER = "phylloUser"
        const val AUTH_KEY_USER = "phylloUserAuth"
        const val KEY_INSTA_ACCOUNT_ID = "KEY_INSTA_ACCOUNT_ID"
        const val KEY_YOUTUBE_ACCOUNT_ID = "KEY_YOUTUBE_ACCOUNT_ID"
        const val KEY_SOCIAL_ACCOUNT_FLAG = "KEY_SOCIAL_ACCOUNT_FLAG"
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }*/

    companion object {
        private const val PREFERENCES_NAME = "crater_preferences"
        private val KEY_YOUTUBE_ACCOUNT_ID = stringPreferencesKey("youtube_account_id")
        private val KEY_INSTAGRAM_ACCOUNT_ID = stringPreferencesKey("instagram_account_id")
        private val KEY_PHYLLO_USER_ID = stringPreferencesKey("phyllo_user_id")
        private val KEY_PHYLLO_SDK_TOKEN = stringPreferencesKey("phyllo_sdk_token")
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
    }

}