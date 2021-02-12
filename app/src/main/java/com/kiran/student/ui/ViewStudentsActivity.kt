package com.kiran.student.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiran.student.R
import com.kiran.student.adapter.StudentAdapter
import com.kiran.student.entity.Student
import com.kiran.student.repository.RepoStudent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewStudentsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_students)

        recyclerView = findViewById(R.id.recyclerView)

        loadStudents()

    }

    private fun loadStudents() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentRepository = RepoStudent()
                val response = studentRepository.getAllStudents()
                if(response.success==true){
                    // Put all the student details in lstStudents
                    val lstStudents = response.data
                    withContext(Main){
                        val adapter = StudentAdapter(lstStudents as ArrayList<Student>, this@ViewStudentsActivity)
                        recyclerView.layoutManager = LinearLayoutManager(this@ViewStudentsActivity)
                        recyclerView.adapter = adapter
                    }
                }
            }catch(ex : Exception){
                withContext(Main){
                    Toast.makeText(this@ViewStudentsActivity,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}