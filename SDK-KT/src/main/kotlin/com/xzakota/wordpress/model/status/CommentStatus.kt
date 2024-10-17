package com.xzakota.wordpress.model.status

object CommentStatus {
    private const val COMMENT_STATUS_OPEN_NAME = "open"
    private const val COMMENT_STATUS_CLOSED_NAME = "closed"

    @JvmField
    val OPEN = Status(COMMENT_STATUS_OPEN_NAME)
    @JvmField
    val CLOSED = Status(COMMENT_STATUS_CLOSED_NAME)

    @JvmStatic
    fun of(name : String?) : Status {
        return if (COMMENT_STATUS_OPEN_NAME == name) {
            OPEN
        } else {
            CLOSED
        }
    }
}
