package com.aryasurya.adoptpet.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aryasurya.adoptpet.data.pref.UserPreference
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.data.remote.retrofit.ApiConfig
import java.lang.Exception

class StoryPagingSource(private val userPreference: UserPreference) : PagingSource<Int , ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int , ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int , ListStoryItem> {
        val token = userPreference.getToken()
        val apiService = ApiConfig.getApiService(token.toString())
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStories(position, params.loadSize)
            val story = responseData.listStory

            LoadResult.Page(
                data = story,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (story.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}