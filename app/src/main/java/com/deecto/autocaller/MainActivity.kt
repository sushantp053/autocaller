package com.deecto.autocaller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.deecto.autocaller.connection.StatusService
import com.deecto.autocaller.data.CampStatus
import com.deecto.autocaller.data.Status
import com.deecto.autocaller.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            val intent = Intent(this, ContactUpload::class.java)
            startActivity(intent)
        }


        getStatus()


    }

    private fun getStatus() {
        val status: Call<Status> = StatusService.statusInstance.getStatus()
        status.enqueue(object : Callback<Status> {
            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                val stat: Status? = response.body()
                if (stat != null) {
                    val statusTextView = findViewById<TextView>(R.id.statusId)
                    statusTextView.setText(stat.kitstatus).toString()

                    val campStatusTextView = findViewById<TextView>(R.id.campStatus)
                    campStatusTextView.setText(stat.campaignstatus).toString()

                    Log.d("Sushant", stat.campaignstatus)
                }

            }

            override fun onFailure(call: Call<Status>, t: Throwable) {
                Log.d("Sushant", "Error  $t")
            }

        })

        val campStatus: Call<CampStatus> = StatusService.statusInstance.getCampaignStatus()
        campStatus.enqueue(object : Callback<CampStatus> {
            override fun onResponse(call: Call<CampStatus>, response: Response<CampStatus>) {

                val stat: CampStatus? = response.body()
                if (stat != null) {
                    val statusTextView = findViewById<TextView>(R.id.statusText)
                    statusTextView.setText(stat.completed.toString())

                    Log.d("Sushant 123", stat.answered.toString())
                }

            }

            override fun onFailure(call: Call<CampStatus>, t: Throwable) {
                Log.d("Sushant", "Error  $t")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}