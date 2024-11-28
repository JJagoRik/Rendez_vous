package com.example.rendez_vous_test.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.rendez_vous_test.presentation.ui.components.BottomMenu
import com.example.rendez_vous_test.presentation.ui.navigation.AppNavHost
import com.example.rendez_vous_test.presentation.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                val viewModel = hiltViewModel<MoviesViewModel>()
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomMenu(navController = navController)
                    },
                    content = { padding ->
                        Box(
                            modifier = Modifier.padding(padding)
                        ) {
                            AppNavHost(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                    }
                )
            }
        }
    }
}