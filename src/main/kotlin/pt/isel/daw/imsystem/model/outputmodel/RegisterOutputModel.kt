package pt.isel.daw.imsystem.model.outputmodel

import pt.isel.daw.imsystem.entity.Role

data class RegisterOutputModel(
    val id: Long,
    val username: String,
    val role: Role,
    val status: String
)