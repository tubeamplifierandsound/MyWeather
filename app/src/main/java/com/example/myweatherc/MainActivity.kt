//package com.example.myweatherc
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.myweatherc.ui.theme.MyWeatherCTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            MyWeatherCTheme {
//
//
//
//            }
//        }
//    }
//}



package com.example.myweatherc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myweatherc.ui.theme.base_screen.bottom_nav.BottomNavItem
//import com.example.myweatherc.ui.theme.screens.*
import com.example.myweatherc.navigation.*
import com.example.myweatherc.ui.base_screen.BaseScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen()

        }
    }
}