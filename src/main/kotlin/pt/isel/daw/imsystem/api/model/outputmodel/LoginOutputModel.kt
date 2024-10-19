package pt.isel.daw.imsystem.api.model.outputmodel

import pt.isel.daw.imsystem.entity.Role
import pt.isel.daw.imsystem.api.model.inputmodel.LoginInputModel

class LoginOutputModel(
    val loginInputModel: LoginInputModel,
    val role: Role,
    val jwtToken: String,
    val token: String
) {
}