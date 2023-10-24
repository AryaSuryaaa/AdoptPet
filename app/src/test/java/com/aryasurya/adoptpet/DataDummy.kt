package com.aryasurya.adoptpet

import com.aryasurya.adoptpet.data.remote.response.ListStoryItem

object DataDummy {

    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "name + $i",
                "desc $i",
                "photo $i",
                "created $i",
            )
            items.add(story)
        }
        return items
    }
}