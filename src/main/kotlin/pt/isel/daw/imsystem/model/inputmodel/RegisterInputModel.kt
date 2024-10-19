package pt.isel.daw.imsystem.model.inputmodel

import pt.isel.daw.imsystem.entity.Role

data class RegisterInputModel(
    val username: String,
    val password: String,
    val role: Role
)