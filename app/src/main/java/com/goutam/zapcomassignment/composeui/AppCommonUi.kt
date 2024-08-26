package com.goutam.zapcomassignment.composeui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.goutam.zapcomassignment.model.Product

@Composable
fun BannerLayout(item: Product) {
    Column(modifier = Modifier.padding(15.dp)){
        Text(
            text = "Banner",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
        )
        Column {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = item.image),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )
            }
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier
                    .padding(15.dp)
            )
        }
    }
}

@Composable
fun HorizontalScrollLayout(items: List<Product>) {
    Column(modifier = Modifier.padding(15.dp)) {
        Text(
            text = "Horizontal Free Scroll",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
        )
        LazyRow {
            items(items) { item ->
                HorizontalScrollItem(item)
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
private fun HorizontalScrollItem(product: Product) {
    Column {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .width(124.dp)
                .height(124.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = product.image),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
    }
}

@Composable
fun SplitBannerLayout(items: List<Product>) {
    Column(modifier = Modifier.padding(15.dp)) {
        Text(
            text = "Split Banner",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            SplitBannerItem(item = items[0], modifier = Modifier.weight(1f))
            SplitBannerItem(item = items[1], modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun SplitBannerItem(item: Product, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .height(240.dp)
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(model = item.image),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                    .padding(8.dp)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
    }
}

@Composable
fun LoadingProgress(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(150.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = Color.DarkGray)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Loading ...", fontSize = 16.sp, color = Color.Black)
            }
        }
    }
}