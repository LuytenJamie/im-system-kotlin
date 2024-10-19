package pt.isel.daw.imsystem.model.outputmodel

import pt.isel.daw.imsystem.entity.Role
import pt.isel.daw.imsystem.model.inputmodel.LoginInputModel
import pt.isel.daw.imsystem.model.inputmodel.RegisterInputModel

class LoginOutputModel(
    val loginInputModel: LoginInputModel,
    val role: Role,
    val jwtToken: String,
    val token: String
) {
}