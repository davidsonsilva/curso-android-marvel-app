package com.example.marvelapp.base

import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Rule

open class BaseFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

}