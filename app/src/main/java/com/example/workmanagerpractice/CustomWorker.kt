package com.example.workmanagerpractice

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CustomWorker @AssistedInject constructor(
    @Assisted private val api: WebServices,
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters
): CoroutineWorker(
    context,
    params
) {
    override suspend fun doWork(): Result {
        return try {
            val response = api.getPost()
            if (response.isSuccessful) {
                Log.d("CustomWorker", "Success")
                Log.d("CustomWorker", "${response.body()}")
                Result.success()
            }else{
                Result.failure()
            }
        }catch (e: Exception) {
            if (e is java.net.UnknownHostException) {
                Log.d("CustomWorker", "UnknownHostException")
                Result.retry()
            } else {
                Log.d("CustomWorker", "Error")
                Result.failure(Data.Builder().putString("error", e.message).build())
            }
        }

    }

}