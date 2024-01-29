package com.example.atinsnlc.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.atinsnlc.R
import com.example.atinsnlc.data.news_events.room.NewsEntity
import com.example.atinsnlc.viewModel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NewsEventsScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    ScreenContent(navController, mainViewModel)
    BackHandler {
        navController.popBackStack()
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ScreenContent(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    var newsString by remember { mutableStateOf("") }
    val news = mainViewModel.news.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        delay(3000)
    scope.launch {
        val data = mainViewModel.extractData(news)
        newsString = data
    }

    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "News & Events")
            },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back", modifier = Modifier.padding(5.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color("#33691E".toColorInt()),
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    if (newsString=="") {"Welcome To Applied Technologies Institute Mandra"}
                    else {newsString},
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .background(Color.Green)
                        .padding(10.dp)
                        .fillMaxWidth()
                        .basicMarquee(
                        )
                )

                Image(
                    painter = painterResource(id = R.drawable.college_gate),
                    contentDescription = "Welcome"
                )
                Text(
                    text = "Announcements and Important Updates",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color("#33691E".toColorInt()))
                        .padding(10.dp)
                )
                Text(
                    text = stringResource(id = R.string.news_description),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
        }
    }
}