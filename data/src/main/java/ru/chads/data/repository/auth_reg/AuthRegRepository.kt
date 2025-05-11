package ru.chads.data.repository.auth_reg

interface AuthRegRepository {

    fun auth(login: String, password: String)
    fun reg(login: String, password: String)

}