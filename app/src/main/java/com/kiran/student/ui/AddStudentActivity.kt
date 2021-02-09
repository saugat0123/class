package com.kiran.student.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.kiran.student.R
import com.kiran.student.entity.Student
import com.kiran.student.repository.RepoAddStudent
import com.kiran.student.repository.RepoUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddStudentActivity : AppCompatActivity() {

    private lateinit var etFullName: TextInputEditText
    private lateinit var etAge: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var rdoMale: RadioButton
    private lateinit var rdoFemale: RadioButton
    private lateinit var rdoOthers: RadioButton
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        etFullName = findViewById(R.id.etFullName)
        etAge = findViewById(R.id.etAge)
        etAddress = findViewById(R.id.etAddress)
        rdoMale = findViewById(R.id.rdoMale)
        rdoFemale = findViewById(R.id.rdoFemale)
        rdoOthers = findViewById(R.id.rdoOthers)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            saveStudent()
        }
    }

    private fun saveStudent() {
        val fullName = etFullName.text.toString()
        val age = etAge.text.toString()
        val address = etAddress.text.toString()
        var gender = ""
        when {
            rdoFemale.isSelected -> {
                gender = "Female"
            }
            rdoMale.isSelected -> {
                gender = "Male"
            }
            rdoOthers.isSelected -> {
                gender = "Others"
            }
        }

        val student = Student(fullName, age, gender, address)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentRepository = RepoAddStudent()
                val response = studentRepository.addStudent(student)
                if(response.success == true){
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddStudentActivity,
                            "Add Student bhayo", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AddStudentActivity,
                        "Username cannot be duplicate", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}