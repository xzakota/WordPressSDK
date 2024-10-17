package com.xzakota.wordpress.model.status

object ItemStatus {
    private const val ITEM_STATUS_DRAFT_NAME = "draft"
    private const val ITEM_STATUS_PUBLISH_NAME = "publish"
    private const val ITEM_STATUS_PRIVATE_NAME = "private"

    @JvmField
    val DRAFT = Status(ITEM_STATUS_DRAFT_NAME)
    @JvmField
    val PUBLISH = Status(ITEM_STATUS_PUBLISH_NAME)
    @JvmField
    val PRIVATE = Status(ITEM_STATUS_PRIVATE_NAME)

    @JvmStatic
    fun of(name : String?) : Status {
        return when (name) {
            ITEM_STATUS_DRAFT_NAME -> DRAFT
            ITEM_STATUS_PRIVATE_NAME -> PRIVATE
            else -> PUBLISH
        }
    }
}
