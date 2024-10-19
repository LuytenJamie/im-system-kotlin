package pt.isel.daw.imsystem.api.model.outputmodel

data class MessageOutputModel(
    val id: Long,
    val text: String,
    val username: String,
    val createdAt: String
)
