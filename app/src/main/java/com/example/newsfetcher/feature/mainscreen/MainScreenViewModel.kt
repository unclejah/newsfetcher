package com.example.newsfetcher.feature.mainscreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.newsfetcher.base.BaseViewModel
import com.example.newsfetcher.base.Event
import com.example.newsfetcher.feature.bookmarks.domain.BookmarksInteractor
import com.example.newsfetcher.feature.domain.ArticlesInteractor
import kotlinx.coroutines.launch

class MainScreenViewModel (private val interactor : ArticlesInteractor,
                           private val bookmarksInteractor: BookmarksInteractor
)
    : BaseViewModel <ViewState> () {

    init {
        processDataEvent(DataEvent.loadArticles)
    }


    override fun initialViewState() = ViewState(
        articleList = emptyList(),
        articlesShown = emptyList(),
        editText = "",
        isSearchEnabled = false,

        )

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is DataEvent.loadArticles -> {
                viewModelScope.launch {
                    interactor.getArticles().fold(
                        onError = {
                            Log.e("Error", it.localizedMessage)
                        },
                        onSuccess = {
                            processDataEvent(DataEvent.onLoadArticlesSoursed(it))

                        }
                    )
                }
                return null
            }
            is DataEvent.onLoadArticlesSoursed -> {
                return previousState.copy(
                    articleList = event.articles, articlesShown = event.articles,
                )
            }

            is UiEvent.OnArticleClicked -> {
                viewModelScope.launch {
                    bookmarksInteractor.create(previousState.articlesShown[event.index])
                }
                return null
            }

            is UiEvent.OnSearchButtonClicked -> {
                return previousState.copy(
                    articlesShown = if (
                        previousState.isSearchEnabled
                        && previousState.editText == ""
                    ) previousState.articleList
                    else previousState.articlesShown,
                    isSearchEnabled = !previousState.isSearchEnabled
                )
            }

            is UiEvent.OnSearchEdit -> {
                previousState.editText = event.text
                return previousState.copy(articlesShown = previousState.articleList.filter {
                    it.title.contains(event.text)
                })
            }

            else -> return null
        }
        return null
    }

}