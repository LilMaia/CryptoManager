package com.android.cryptomanager.home.data.repositories

import com.android.cryptomanager.home.data.api.ApiInterface
import com.android.cryptomanager.home.data.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeRepository(private val apiInterface: ApiInterface) {

}
