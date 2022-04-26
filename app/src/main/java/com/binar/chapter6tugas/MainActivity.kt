package com.binar.chapter6tugas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import kotlin.math.pow

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    lateinit var output : String
    var bmia by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        thread()
        htBMI()
    }

    fun thread() {
        Thread(
            Runnable {
                btn1.setOnClickListener {
                    if (editBerat.text.isNotBlank() && editTinggi.text.isNotBlank()) {
                        val berat = editBerat.text.toString().toDouble()
                        val tinggi = editTinggi.text.toString().toDouble()
                        val cmToM: Double = tinggi / 100
                        val tinggi2: Double = cmToM * cmToM
                        var bmi: Double = (berat / tinggi2)

                        if (bmi < 18.5) {
                            output = "Kurus"
                        } else if (bmi >= 18.5 && bmi < 25) {
                            output = "Normal"
                        } else if (bmi >= 25 && bmi < 30) {
                            output = "Overweight"
                        } else {
                            output = "Obesitas"
                        }

                        hasil.post(Runnable { hasil.text = output })
                        bmii.post(Runnable { bmii.text = (bmi.toString()) })
                    } else {
                        Toast.makeText(this, "ISI INPUTAN", Toast.LENGTH_LONG).show()
                    }
                }

            }).start()
    }



    fun htBMI(){

        val handle = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val pesan = msg.obj as String
                hasil.setText(pesan)
            }
        }

        Thread(Runnable {
            btn2.setOnClickListener {
                if (editBerat.text.isNotBlank() && editTinggi.text.isNotBlank()) {
                    val berat = editBerat.text.toString().toDouble()
                    val tinggi = editTinggi.text.toString().toDouble()
                    val cmToM: Double = tinggi / 100
                    val tinggi2: Double = cmToM * cmToM
                    var bmi: Double = (berat / tinggi2)

                    if (bmi < 18.5) {
                        output = "Kurus"
                    } else if (bmi >= 18.5 && bmi < 25) {
                        output = "Normal"
                    } else if (bmi >= 25 && bmi < 30) {
                        output = "Overweight"
                    } else {
                        output = "Obesitas"
                    }

                    val pesan = Message.obtain()
                    pesan.obj = output
                    pesan.target = handle
                    pesan.sendToTarget()
                } else {
                    Toast.makeText(this, "ISI INPUTAN", Toast.LENGTH_LONG).show()
                }

            }
        }).start()
    }
}