package com.lenox.enjoy

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.annotation.NonNull
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.lenox.enjoy.MainActivity.Companion.findMainActivity
import com.lenox.enjoy.flutter.createFlutterFragment
import com.lenox.enjoy.ui.theme.EnjoyAppTheme
import io.flutter.embedding.android.FlutterFragment
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {
    companion object {
        fun Context.findMainActivity(): MainActivity? {
            return when (this) {
                is MainActivity -> this
                is ContextWrapper -> baseContext.findMainActivity()
                else -> null
            }
        }
    }

    // Declare a local variable to reference the FlutterFragment so that you
    // can forward calls to it later.
    val flutterFragment: FlutterFragment by lazy { createFlutterFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            EnjoyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        flutterFragment.onPostResume()
    }

    override fun onNewIntent(@NonNull intent: Intent) {
        super.onNewIntent(intent)
        flutterFragment.onNewIntent(intent)
    }

    override fun onBackPressed() {
        flutterFragment.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        flutterFragment.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    override fun onUserLeaveHint() {
        flutterFragment.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        flutterFragment.onTrimMemory(level)
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_jetpack_compose),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(
                    width = 1.5.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                )
        )

        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shadowElevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) }
            )
        },
        content = { innerPadding ->
            Column {
                val pagerState = rememberPagerState()
                val currentPage = pagerState.currentPage
                val rememberCoroutineScope = rememberCoroutineScope()

                HorizontalPager(
                    count = 2,
                    modifier = Modifier.weight(1f),
                    contentPadding = innerPadding,
                    state = pagerState,
                ) { page ->
                    // Our page content
                    if (page == 0) {
                        val dataList =
                            (0..15).map { Message(author = "Android", body = "Jetpack Compose") }
                        LazyColumn(
                            contentPadding = innerPadding,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            items(dataList.size) { index ->
                                MessageCard(msg = dataList[index])
                            }
                        }
                    } else {
                        AndroidView(
                            factory = { context ->
                                val container = FrameLayout(context)
                                container.id = View.generateViewId()

                                val mainActivity = context.findMainActivity()
                                mainActivity?.supportFragmentManager
                                    ?.beginTransaction()
                                    ?.add(container.id, mainActivity.flutterFragment)
                                    ?.commit()

                                container
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    }

                }

                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text(text = stringResource(R.string.android)) },
                        selected = currentPage == 0,
                        onClick = {
                            rememberCoroutineScope.launch {
                                pagerState.scrollToPage(page = 0)
                            }
                        }
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(text = stringResource(R.string.flutter)) },
                        selected = currentPage == 1,
                        onClick = {
                            rememberCoroutineScope.launch {
                                pagerState.scrollToPage(page = 1)
                            }
                        }
                    )
                }
            }
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview() {
    EnjoyAppTheme {
        MainScreen()
    }
}