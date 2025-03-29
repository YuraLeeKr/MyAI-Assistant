package com.example.myai_assistant.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myai_assistant.data.BookmarkRepository
import com.example.myai_assistant.data.DatabaseProvider
import com.example.myai_assistant.viewmodel.BookmarkViewModel
import com.example.myai_assistant.viewmodel.BookmarkViewModelFactory
import com.example.myai_assistant.viewmodel.MainViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
){

    val context = LocalContext.current

    //mainviewmodel은 기본 생성자 사용
    val mainViewModel: MainViewModel = viewModel()

    //bookmarkviewmodel은 직접 주입 필요
    val bookmarkViewModel: BookmarkViewModel = getBookmarkViewModel(context)

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.name,
        modifier = modifier
    ){
        composable(Screen.MAIN.name){
            MainScreen (
                viewModel = mainViewModel,
                bookmarkViewModel = bookmarkViewModel,
                onNavigateToBookmarks = {
                    navController.navigate(Screen.BOOKMARK.name)
                }
            )
        }

        composable(Screen.BOOKMARK.name){
            BookmarkScreen(
                viewModel = bookmarkViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun getBookmarkViewModel(context: Context): BookmarkViewModel {
    val bookmarkDao = DatabaseProvider.getDatabase(context).bookmarkDao()
    val repository = BookmarkRepository(bookmarkDao)
    val factory = BookmarkViewModelFactory(repository)
    return viewModel(factory = factory)
}