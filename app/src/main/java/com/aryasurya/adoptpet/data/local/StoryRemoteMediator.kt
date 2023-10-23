package com.aryasurya.adoptpet.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.aryasurya.adoptpet.data.local.room.ListStoryDatabase
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.data.remote.retrofit.ApiService
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val database: ListStoryDatabase ,
    private val apiService: ApiService
): RemoteMediator<Int, ListStoryItem>() {
    override suspend fun load(
        loadType: LoadType ,
        state: PagingState<Int , ListStoryItem>
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX

        try {
            val responseData = apiService.getStories(page, state.config.pageSize)

            val endOfPaginationReached = responseData.listStory.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.storyDao().deleteAll()
                }
                database.storyDao().insetStory(responseData.listStory)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}