import com.example.chill.domain.model.MovieItem
import com.example.chill.domain.model.Result

data class HomeState(
    var movieList: List<Result> = emptyList()
)

