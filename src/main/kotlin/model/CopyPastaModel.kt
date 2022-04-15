package model

import kotlinx.serialization.Serializable

@Serializable
data class CopyPastaModel(
    val IDcopy: Int,
    val title: String,
    val body: String,
    val IDgroup: Int,
    val deleted: Boolean
)