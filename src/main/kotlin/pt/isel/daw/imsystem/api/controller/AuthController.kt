package pt.isel.daw.imsystem.api.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.imsystem.exception.RegisterException
import pt.isel.daw.imsystem.exception.UserAlreadyExistsException
import pt.isel.daw.imsystem.api.model.inputmodel.LoginInputModel
import pt.isel.daw.imsystem.api.model.inputmodel.RegisterInputModel
import pt.isel.daw.imsystem.api.model.outputmodel.LoginOutputModel
import pt.isel.daw.imsystem.api.model.outputmodel.RegisterOutputModel
import pt.isel.daw.imsystem.service.AuthService
import java.lang.Exception

@RestController
@RequestMapping("api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterInputModel): ResponseEntity<Any> {
        println("Received registration request for user: ${request.username}")
        return try {
            val registerOutputModel: RegisterOutputModel = authService.registerUser(request)
            println("User registered successfully: ${registerOutputModel.username}")
            ResponseEntity.status(HttpStatus.CREATED).body(registerOutputModel)
        } catch (e: UserAlreadyExistsException) {
            println("Registration failed: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        } catch (e: RegisterException) {
            println("Registration error: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        } catch (e: Exception) {
            println("Unexpected error during registration: ${e.message}")
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during registration")
        }
    }
    @PostMapping("/login")
    fun loginUser(@RequestBody request: LoginInputModel): ResponseEntity<Any>  {
        return try {
            val loginResponse: LoginOutputModel = authService.loginUser(request)
            ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, loginResponse.jwtToken)
                .body(loginResponse)
        } catch (e: RuntimeException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}