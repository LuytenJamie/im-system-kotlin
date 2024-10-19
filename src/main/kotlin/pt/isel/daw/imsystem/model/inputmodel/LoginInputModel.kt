package pt.isel.daw.imsystem.model.inputmodel

import org.jetbrains.annotations.NotNull
import pt.isel.daw.imsystem.entity.Role

data class LoginInputModel(
    @NotNull val username: String,
    @NotNull val password: String,
) {
}