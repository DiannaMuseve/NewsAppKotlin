import com.example.newsapp.models.Article

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)


