package ru.nikita.catsapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(view: View, string: String) {
    Snackbar.make(view, string, Snackbar.LENGTH_LONG).show()
}
