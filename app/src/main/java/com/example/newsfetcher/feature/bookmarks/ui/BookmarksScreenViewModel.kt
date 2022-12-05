package com.example.newsfetcher.feature.bookmarks.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.newsfetcher.base.BaseViewModel
import com.example.newsfetcher.base.Event
import com.example.newsfetcher.feature.bookmarks.domain.BookmarksInteractor
import kotlinx.coroutines.launch

class BookmarksScreenViewModel (private val interactor:BookmarksInteractor) : BaseViewModel <ViewState>(){

    init {
        processDataEvent(com.example.newsfetcher.feature.bookmarks.ui.DataEvent.LoadBookmarks)
    }
    override fun initialViewState(): ViewState = ViewState(bookmarksArticles = emptyList())
    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when(event) {
            is DataEvent.LoadBookmarks -> {
                viewModelScope.launch {
                    interactor.read().fold(
                        onError = {},
                        onSuccess = {
                            processDataEvent(DataEvent
                                .OnSuccessBookmarksLoaded(it))
                        }
                    )
                }
                return null
            }
            is DataEvent.OnSuccessBookmarksLoaded -> {
                Log.d("Room", "articleBookmark = ${event.bookmarksArticle}")
                return previousState.copy(bookmarksArticles = event.bookmarksArticle)
            }

            is UiEvent.OnArticleClicked -> {
                viewModelScope.launch {
                    interactor.delete(previousState.bookmarksArticles[event.index])

                }
                return null
            }
            else ->return null
        }

    }

}
