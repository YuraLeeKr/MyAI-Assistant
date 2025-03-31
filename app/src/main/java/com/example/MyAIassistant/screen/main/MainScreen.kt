package com.example.MyAIassistant.screen.main

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.MyAIassistant.R
import com.example.MyAIassistant.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextDecoration
import com.example.MyAIassistant.data.model.BookmarkEntity
import com.example.MyAIassistant.viewmodel.BookmarkViewModel


@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    bookmarkViewModel: BookmarkViewModel,
    onNavigateToBookmarks: () -> Unit
) {

    //사용자 입력을 저장
    var keyword by remember { mutableStateOf("") }

    //viewmodel에서 livedata를 observe하여 결과 가져오기
    val results by viewModel.result.observeAsState()
    val error by viewModel.error.observeAsState()

    val josefinFont = FontFamily(
        Font(R.font.josefinsans_bold)
    )

    val currentTime = remember{
        SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(Date())
    }

    val isLoading by viewModel.isLoading.observeAsState(false)

    val keyboardController = LocalSoftwareKeyboardController.current
    val isValid = keyword.trim().isNotEmpty()
    val context = LocalContext.current
    val trendingKeywords by viewModel.trending.observeAsState(emptyList())

    val bookmarks by bookmarkViewModel.bookmarks.collectAsState(emptyList())

    val isBookmarked = results?.let{ res ->
        bookmarks.any{it.keyword == res.keyword}
    } ?: false

    val toggleBookmark: () -> Unit = {
        results?.let {
            val bookmark = BookmarkEntity(
                keyword = it.keyword,
                summary = it.summary,
                sentiment = it.sentiment,
                newsList = it.newsList
            )
            //이미 북마크된 상태 -> 제거
            if (isBookmarked) bookmarkViewModel.removeBookmark(bookmark)

            //북마크 안된 상태 -> 추가
            else bookmarkViewModel.addBookmark(bookmark)
        }
    }



    LaunchedEffect(results) {
        if(results != null){
            keyword = ""
            keyboardController?.hide()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.fetchTrendingKeywords()
    }
    //전체 UI를 세로 scroll가능한 Column으로 구성
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E7))
            .padding(30.dp)
            .verticalScroll(rememberScrollState())//scroll가능하게
    ){

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "🤖 MyAI Assistant",
            fontFamily = josefinFont,
            fontSize = 34.sp,
            color = Color(0xFF333333),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        //키워드 입력 필드
        OutlinedTextField(
            value = keyword,
            onValueChange = { keyword = it },
            label = { Text("키워드를 입력하세요") },
            leadingIcon = {Icon(Icons.Default.Search, contentDescription = null)},
            shape = RoundedCornerShape(16.dp),
            singleLine = true, //한줄 입력만
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 분석하기 버튼
        Button(
            onClick = { viewModel.analyzeKeyword(keyword) },
            enabled = isValid && !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp) ,
            colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF03A9F4),
                    contentColor = Color.White
            )
        ) {
            Text("Gemini로 분석하기")
        }

        Text(
            text = "🔥 실시간 인기 키워드",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ){
            items(trendingKeywords){ trending ->
                AssistChip(
                    onClick = {
                        keyword = trending
                        viewModel.analyzeKeyword(trending) },
                    label = {Text(trending)},
                    modifier = Modifier
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 16.dp)
                )
            }
        }


        if(!isLoading && results != null) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn()
            ) {
                results?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFFFFF)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {

                            //아이콘 + 시간
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Analysis Complete",
                                    tint = Color(0xFF4CAF50)
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = toggleBookmark
                                    ) {
                                        Icon(
                                            imageVector = if (isBookmarked) Icons.Filled.Star else Icons.Outlined.Star,
                                            contentDescription = "북마크",
                                            tint = if (isBookmarked) Color(0xFFFFC107) else Color.Gray
                                        )
                                    }
                                    Text(
                                        text = currentTime,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            //요약 제목
                            Text(
                                text = "📌 요약",
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF212121)
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it.summary,
                                fontSize = 16.sp,
                                color = Color(0xFF444444)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "💬 감정",
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF212121)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // 감정에 따라 색상 다르게 설정
                            val sentimentColor = when (it.sentiment) {
                                "긍정적" -> Color(0xFF4CAF50) // 초록
                                "부정적" -> Color(0xFFF44336) // 빨강
                                "중립적" -> Color.Gray // 회색
                                else -> Color.Black
                            }

                            val sentimentEmoji = when (it.sentiment) {
                                "긍정적" -> "😊"
                                "부정적" -> "😠"
                                "중립" -> "😐"
                                else -> ""
                            }

                            // 감정 텍스트 표시
                            Text(
                                text = "${it.sentiment} $sentimentEmoji",
                                color = sentimentColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "📰 관련 뉴스",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
                            )

                            results?.newsList?.forEach { news ->
                                val parts = news.split(" - ")
                                val title = parts.getOrNull(0) ?: "뉴스 제목 없음"
                                val link = parts.getOrNull(1) ?: ""

                                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                    //제목
                                    Text(
                                        text = title,
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = link,
                                        color = Color.Blue,
                                        fontSize = 14.sp,
                                        textDecoration = TextDecoration.Underline,
                                        modifier = Modifier
                                            .padding(vertical = 2.dp)
                                            .clickable {
                                                val intent =
                                                    Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                                context.startActivity(intent)
                                            }
                                    )
                                }

                            }

                        }

                    }
                }
            }
        }

        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text("❗ $it", color = MaterialTheme.colorScheme.error)
        }
    }
}
