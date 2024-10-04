package com.cfh.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cfh.data.utils.Consts

@Entity(tableName = Consts.PAGING_INFO)
data class PagingInfo(
    @PrimaryKey
    val id: Int = 1,
    val lastFetchedPage: Int
)