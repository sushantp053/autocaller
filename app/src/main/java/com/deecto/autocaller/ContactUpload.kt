package com.deecto.autocaller

import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.deecto.autocaller.connection.StatusService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream


class ContactUpload : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var button: Button
    lateinit var fileUri: Uri

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        fileUri = it!!
        textView.text = it.path
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_upload)

        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)

        textView.setOnClickListener {
            contract.launch("*/*")
        }
        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                uploadFile()
            }
        }

    }

    private suspend fun uploadFile() {
        val filesDir = applicationContext.filesDir
        val file = File(filesDir, "contact.csv")
        val inputStream = contentResolver.openInputStream(fileUri)
        val outSrteam = FileOutputStream(file)
        inputStream!!.copyTo(outSrteam)

        val requestBody = file.asRequestBody("*/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("contact", file.name)

//        val retrofit = Retrofit.Builder().baseUrl(baseUrl)


        val response = StatusService.statusInstance.uploadFile(part)
        Log.d("Response", response.contentType().toString())
//        StatusService.statusInstance.uploadFile()
        Log.d("File Upload", requestBody.contentType().toString())

    }
}