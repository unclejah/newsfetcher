package com.example.newsfetcher.feature.data

import com.example.newsfetcher.feature.domain.ArticleModel

class ArticlesRepositoryImpl (private val source: ArticlesRemoteSource) : ArticlesRepository {

    override suspend fun getArticles(): List<ArticleModel> {
        return source.getArticles().articleList.asSequence()
            .map {it.toDomain()}
            .sortedBy { it.publishedAt}
            .toList()

    }
}