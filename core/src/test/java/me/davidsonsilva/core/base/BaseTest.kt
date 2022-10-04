package me.davidsonsilva.core.base

import com.example.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

}