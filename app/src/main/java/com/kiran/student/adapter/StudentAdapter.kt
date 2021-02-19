package com.kiran.student.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kiran.student.R
import com.kiran.student.api.ServiceBuilder
import com.kiran.student.entity.Student
import com.kiran.student.repository.RepoStudent
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentAdapter (
    private val lstStudents:ArrayList<Student>,
    private val context: Context
        ): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder (view:View): RecyclerView.ViewHolder(view) {
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
        val imgProfile: CircleImageView = view.findViewById(R.id.imgProfile)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvAge: TextView = view.findViewById(R.id.tvAge)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val tvGender: TextView = view.findViewById(R.id.tvGender)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_layout, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = lstStudents[position]
        holder.tvName.text = student.fullname
        holder.tvAge.text = student.age
        holder.tvAddress.text = student.address
        holder.tvGender.text = student.gender

        holder.btnDelete.setOnClickListener{
//            lstStudents.removeAt(position)
//            notifyItemRemoved(position)
//            notifyDataSetChanged()
//            notifyItemRangeChanged(position,lstStudents.size)
//            Toast.makeText(context, "Student Deleted!!", Toast.LENGTH_SHORT).show()

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete student")
            builder.setMessage("Are you sure you want to delete ${student.fullname} ??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val studentRepository = RepoStudent()
                            val response = studentRepository.deleteStudent(student._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Student Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            withContext(Main) {
                                lstStudents.remove(student)
                                notifyDataSetChanged()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                ex.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        val imagePath = ServiceBuilder.loadImagePath() + student.photo
        if (!student.photo.equals("no-photo.jpg")) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.imgProfile)
        }

//        when(student.gender){
//            "Male" -> Glide.with(context)
//                .load("https://cdn.wallpapersafari.com/4/72/Ou9IRM.jpg")
//                .into(holder.imgProfile)
//            "Female" -> Glide.with(context)
//                .load("https://media.istockphoto.com/vectors/default-avatar-profile-icon-grey-photo-placeholder-hand-drawn-modern-vector-id1273297923?b=1&k=6&m=1273297923&s=612x612&w=0&h=kCbZRaXozftYrZv44poGI6_RrTg7DMa1lIqz_NtZNis=")
//                .into(holder.imgProfile)
//            "Other" -> Glide.with(context)
//                .load("https://png.pngtree.com/png-vector/20190217/ourmid/pngtree-smile-vector-template-design-illustration-png-image_555082.jpg")
//                .into(holder.imgProfile)
//        }
    }

    override fun getItemCount(): Int {
        return lstStudents.size
    }
}