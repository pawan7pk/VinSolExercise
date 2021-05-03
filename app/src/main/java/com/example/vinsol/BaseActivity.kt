package com.example.vinsol

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun showMessageDialog(msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Vin Solution")
        builder.setMessage(msg)
        builder.setPositiveButton("OK", null)
        builder.show()
    }
}