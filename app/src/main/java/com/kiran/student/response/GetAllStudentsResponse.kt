package com.kiran.student.response

import com.kiran.student.entity.Student

data class GetAllStudentsResponse (
     val success: Boolean? =null,
     val count: Int? = null,
     val data: MutableList<Student>? = null
         )
