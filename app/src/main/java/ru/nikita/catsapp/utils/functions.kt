package ru.nikita.catsapp.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(view: View, string: String) {
    Snackbar.make(view, string, Snackbar.LENGTH_LONG).show()
}
