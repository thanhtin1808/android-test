import com.example.cubandroidtest.data.model.NewsArticle
import com.example.cubandroidtest.data.remote.ApiService
import com.example.cubandroidtest.repository.NewsRepository
import com.example.cubandroidtest.ui.common.BaseResult
import com.google.gson.Gson
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : NewsRepository {

    override suspend fun getNewsList(
        language: String,
        pageSize: Int,
        page: Int
    ): BaseResult<List<NewsArticle>> = try {
        val response = apiService.getNewsList(language, pageSize, page)

        if (response.isSuccess) {
            BaseResult.Success(response.articles.orEmpty())
        } else {
            BaseResult.Error(throwable = Exception("Unknown error"),
                responseBody = Gson().toJson(response))
        }

    } catch (e: retrofit2.HttpException) {
        BaseResult.Error(e, e.response()?.errorBody()?.string())
    } catch (e: Exception) {
        BaseResult.Error(e)
    }
}
