package com.example.marvelapp.presentation.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showLongToast(@StringRes textResId:Int) =
    Toast.makeText(requireContext(), textResId, Toast.LENGTH_LONG).show()

fun Fragment.showShortToast(@StringRes textResId:Int) =
    Toast.makeText(requireContext(), textResId, Toast.LENGTH_SHORT).show()