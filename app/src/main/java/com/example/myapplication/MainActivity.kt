package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.database.DatabaseHelper
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(context = this)

        handleInserts()
        handleUpdates()
        handleDeletes()
        handleViewing()
    }

    private fun handleViewing() {
        with(binding) {
            showBt.setOnClickListener {
                val res = dbHelper.readData()
                if (res.count == 0) {
                    Toast.makeText(this@MainActivity, "No Data Found", Toast.LENGTH_SHORT).show()
                }

                val buffer = StringBuffer()
                if (res.moveToFirst()) {
                    do {
                        buffer.append("ID :" + res.getString(0) + "\n")
                        buffer.append("Name :" + res.getString(1) + "\n")
                        buffer.append("Brand :" + res.getString(2) + "\n")
                        buffer.append("Type Body :" + res.getString(3) + "\n\n")
                    } while (res.moveToNext())
                }
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setCancelable(true)
                builder.setTitle("Data Listing")
                builder.setMessage(buffer.toString())
                builder.show()
            }
        }
    }

    private fun handleDeletes() {
        with(binding) {
            deleteBt.setOnClickListener {
                try {
                    dbHelper.deleteData(etID.text.toString())
                    clearEditTexts()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, e.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun clearEditTexts() {
        with(binding) {
            etID.setText("")
            etName.setText("")
            etBrand.setText("")
            etTypeBody.setText("")
        }
    }

    private fun handleUpdates() {
        with(binding) {
            updateBt.setOnClickListener {
                try {
                    val isUpdate = dbHelper.updateData(
                        id = etID.text.toString(),
                        name = etName.text.toString(),
                        brand = etBrand.text.toString(),
                        typeBody = etTypeBody.text.toString()
                    )
                    if (isUpdate) {
                        Toast.makeText(
                            this@MainActivity,
                            "Data Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Data Not Updated",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, e.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun handleInserts() {
        with(binding) {
            insertBt.setOnClickListener {
                try {
                    dbHelper.createData(
                        name = etName.text.toString(),
                        brand = etBrand.text.toString(),
                        typeBody = etTypeBody.text.toString()
                    )
                    clearEditTexts()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, e.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}