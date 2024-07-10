package com.example.workmanagerpractice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanagerpractice.ui.theme.WorkManagerPracticeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.time.Duration
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
//            LaunchedEffect(Unit) {
//                val workRequest = OneTimeWorkRequestBuilder<CustomWorker>()
//                .setInitialDelay(Duration.ofSeconds(5))
//                .setBackoffCriteria(
//                    backoffPolicy = BackoffPolicy.LINEAR,
//                    duration = Duration.ofSeconds(5)
//                )
//                .build()
//            }

            LaunchedEffect(Unit) {

                val workRequest = PeriodicWorkRequestBuilder<CustomWorker>(
                    PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
                    TimeUnit.MINUTES
                ).setBackoffCriteria(
                    backoffPolicy = BackoffPolicy.LINEAR,
                    duration = Duration.ofSeconds(5)
                ).setInitialDelay(Duration.ofSeconds(5))
                    .build()

                val workManager = WorkManager.getInstance(applicationContext)
                workManager
                    .enqueueUniquePeriodicWork(
                        "MyWorker",
                        ExistingPeriodicWorkPolicy.KEEP,
                        workRequest
                    )

                workManager.getWorkInfosForUniqueWorkLiveData("MyWorker")
                    .observe(this@MainActivity){
                        it.forEach { workInfo ->
                            Log.d("MyWorker", workInfo.state.toString())
                        }
                    }
                delay(5000)
                workManager.cancelUniqueWork("CustomWorker")
            }

            WorkManagerPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WorkManagerPracticeTheme {
        Greeting("Android")
    }
}