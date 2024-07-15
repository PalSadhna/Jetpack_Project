package com.example.hreactivejetpack.utils


import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class CallDatePicker {



/*
   CallDatePicker().MyDatePickerDialog(
                    onDateSelected = { date = it },
                    onDismiss = { isDobOpen = false }
                )
  @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyDatePickerDialog() {
        var date by remember {
            mutableStateOf("Open date picker dialog")
        }

        var showDatePicker by remember {
            mutableStateOf(false)
        }

        Box(contentAlignment = Alignment.Center) {
            Button(onClick = { showDatePicker = true }) {
                Text(text = date)
            }
        }

        if (showDatePicker) {
            MyDatePickerDialog(
                onDateSelected = { date = it },
                onDismiss = { showDatePicker = false }
            )
        }
    }*/


   /* @ExperimentalMaterial3Api
    @Composable
    fun MyDatePickerDialog(
        onDateSelected: (String) -> Unit,
        onDismiss: () -> Unit
    ) {
        val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        })
        val customColors = lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC6)
            // Add other colors as needed
        )

        val selectedDate = datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: ""
            DatePickerDialog(
               modifier = Modifier.fillMaxWidth().padding(16.dp),
                onDismissRequest = { onDismiss() },
                confirmButton = {
                    Button(onClick = {
                        onDateSelected(selectedDate)
                        onDismiss()
                    }

                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        onDismiss()
                    }) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                )
            }

    }

    @Composable
    fun CustomTheme(
        content: @Composable () -> Unit
    ) {
        val customColors = lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC6)
            // Add other colors as needed
        )
        MaterialTheme(
            colors = customColors,
            content = content
        )
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(millis))
    }
*/
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomDatePickerDialog(onDateSelected: (String) -> Unit){
        val mContext = LocalContext.current
        val mCalendar = Calendar.getInstance()
        val mYear = mCalendar.get(Calendar.YEAR)
       val  mMonth = mCalendar.get(Calendar.MONTH)
        val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()
       val datePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
               val updatedDate  = "$mDayOfMonth/${mMonth+1}/$mYear"
                onDateSelected(updatedDate)
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()

    }
}