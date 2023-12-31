package com.example.finalprojectbinar.util

import android.content.Context
import android.content.SharedPreferences
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SharedPreferenceHelperTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(context.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
    }

    @Test
    fun testWrite() {
        val key = "testKey"
        val value = "testValue"

        SharedPreferenceHelper.init(context)
        SharedPreferenceHelper.write(key, value)

        Mockito.verify(editor).putString(key, value)
        Mockito.verify(editor).apply()
        Mockito.verify(editor, Mockito.never()).commit()
    }


    @Test
    fun testRead() {
        val key = "testKey"
        val defaultValue = "defaultValue"
        val expectedValue = "testValue"

        `when`(sharedPreferences.getString(key, defaultValue)).thenReturn(expectedValue)

        SharedPreferenceHelper.init(context)
        val result = SharedPreferenceHelper.read(key, defaultValue)

        assertEquals(expectedValue, result)
    }
}
