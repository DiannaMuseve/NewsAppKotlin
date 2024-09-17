package com.example.newsapp.api

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.models.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles() // No return value for DELETE

//    @Query("DELETE FROM articles")
//    suspend fun deleteAllArticlesAndReturnCount(): Int // Returns the number of rows deleted

}
