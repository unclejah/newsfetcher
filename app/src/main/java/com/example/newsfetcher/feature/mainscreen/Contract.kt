package com.example.newsfetcher.feature.mainscreen

import com.example.newsfetcher.base.Event
import com.example.newsfetcher.feature.domain.ArticleModel

data class ViewState (
    val isSearchEnabled:Boolean,
    var editText :String,
    val articlesShown:List<ArticleModel>,
    val articleList : List <ArticleModel>,
)

sealed class UiEvent : Event {
    data class OnArticleClicked (val index: Int, ) : UiEvent()
    data class OnArticleClickedForInfo (val index: Int ) : UiEvent()
    object OnSearchButtonClicked   : UiEvent()

    data class OnSearchEdit (val text :String) :UiEvent()
}

sealed class DataEvent : Event {

    object loadArticles :DataEvent()
    data class onLoadArticlesSoursed ( val articles: List<ArticleModel>) : DataEvent()



}