package ru.nikita.catsapp.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.nikita.catsapp.R

fun showSnackBar(view: View, string: String) {
    Snackbar.make(view, string, Snackbar.LENGTH_LONG).show()
}