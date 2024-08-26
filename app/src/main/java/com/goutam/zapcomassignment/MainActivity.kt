package com.goutam.zapcomassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goutam.zapcomassignment.composeui.BannerLayout
import com.goutam.zapcomassignment.composeui.HorizontalScrollLayout
import com.goutam.zapcomassignment.composeui.LoadingProgress
import com.goutam.zapcomassignment.composeui.SplitBannerLayout
import com.goutam.zapcomassignment.enum.SectionType
import com.goutam.zapcomassignment.ui.theme.ZapcomAssignmentTheme
import com.goutam.zapcomassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.fetchProducts()
        enableEdgeToEdge()
        setContent {
            ZapcomAssignmentTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Categories", fontSize = 20.sp, color = Color.White) },
                            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                            actions = {
                                IconButton(onClick = { mainViewModel.fetchProducts() }) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = Color.White)
                                }
                            }
                        )
                    },
                    content = { padding ->
                        Surface(modifier = Modifier.padding(padding)) {
                            ProductList(mainViewModel = mainViewModel)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ProductList(
    mainViewModel: MainViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showSnackbar by remember { mutableStateOf(false) }
    val productDetailsList by mainViewModel.productDetailsList.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()
    val errorMessage by mainViewModel.errorMessage.collectAsState()

    if (errorMessage.isNotEmpty()) {
        showSnackbar = true
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center) {
        if (isLoading) {
            LoadingProgress(onDismissRequest = { mainViewModel.dismissProgressDialog() })
        } else {
            if (productDetailsList.isEmpty()) {
                Text(
                    text = "No Product found",
                    style = TextStyle(fontSize = 20.sp, color = Color.Red)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    productDetailsList.forEach { productDetails ->
                        when(productDetails.sectionType) {
                            SectionType.banner.toString() -> {
                                item { BannerLayout(item = productDetails.items[0]) }
                            }

                            SectionType.splitBanner.toString() -> {
                                item { SplitBannerLayout(items = productDetails.items) }
                            }

                            SectionType.horizontalFreeScroll.toString() -> {
                                item { HorizontalScrollLayout(items = productDetails.items) }
                            }
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.TopCenter)
        )

        if (showSnackbar) {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(errorMessage)
                showSnackbar = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    ZapcomAssignmentTheme {
        ProductList()
    }
}