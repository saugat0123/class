package com.kiran.student.repository

import com.kiran.student.api.MyApiRequest
import com.kiran.student.api.ServiceBuilder
import com.kiran.student.api.StudentAPI
import com.kiran.student.api.UserAPI
import com.kiran.student.entity.Student
import com.kiran.student.response.AddStudentResponse

class RepoAddStudent: MyApiRequest() {

    private val studentAPI = ServiceBuilder.buildService(StudentAPI::class.java)

    //Add Student
    suspend fun addStudent(student: Student): AddStudentResponse{
        return apiRequest {

        }
    }
}