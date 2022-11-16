package com.example.newsfetcher.feature.data

import com.example.newsfetcher.feature.data.ArticlesRepository
import com.example.newsfetcher.feature.domain.ArticleModel

class ArticlesRepoImp( private val source : ArticlesRemoteSource): ArticlesRepository {
    override suspend fun getArticles(): List<ArticleModel> {
        return source.getArticles().articlesList.map{
            it.toDomain()
        }
    }
}