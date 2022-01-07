package com.louzier.todo.tasklist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title : String,
    @SerialName("description")
    val description : String = "Yo le sang de la veine"
) : java.io.Serializable