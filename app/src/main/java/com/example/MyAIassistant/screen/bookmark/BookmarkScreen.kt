package com.example.MyAIassistant.screen.bookmark

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MyAIassistant.viewmodel.BookmarkViewModel

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val bookmarks = viewModel.bookmarks.collectAsState(initial = emptyList())

    if(bookmarks.value.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            contentAlignment = Alignment.TopCenter
        ){
            Text(
                text = "⭐ 저장된 북마크가 없어요",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF8EF))
                .padding(16.dp)
        ) {
            items(bookmarks.value) { bookmark ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "🔖 ${bookmark.keyword}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = { viewModel.removeBookmark(bookmark) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "📌 요약", fontWeight = FontWeight.Bold)
                        Text(text = bookmark.summary, fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "💬 감정", fontWeight = FontWeight.Bold)

                        val sentimentColor = when (bookmark.sentiment) {
                            "긍정적" -> Color(0xFF4CAF50)
                            "부정적" -> Color(0xFFF44336)
                            "중립적" -> Color.Gray
                            else -> Color.Black
                        }

                        Text(
                            text = bookmark.sentiment,
                            fontSize = 14.sp,
                            color = sentimentColor,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "📰 관련 뉴스", fontWeight = FontWeight.Bold)

                        bookmark.newsList.forEach { newsItem ->
                            val parts = newsItem.split(" - ")
                            val title = parts.getOrNull(0) ?: "뉴스 제목 없음"
                            val link = parts.getOrNull(1) ?: ""

                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                // 뉴스 제목 (검정)
                                Text(
                                    text = title,
                                    color = Color.Black,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                // 뉴스 링크 (파란 하이퍼링크)
                                Text(
                                    text = link,
                                    color = Color.Blue,
                                    fontSize = 14.sp,
                                    textDecoration = TextDecoration.Underline,
                                    modifier = Modifier
                                        .clickable {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                            context.startActivity(intent)
                                        }
                                        .padding(vertical = 2.dp)
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
