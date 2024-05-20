package com.example.vehicletickettermproject.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.util.Calendar

@Composable
fun BeginDatePicker(
    context: Context,
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val calendar = remember { Calendar.getInstance() }

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, day ->
            onDateSelected(year, month, day)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.setOnDismissListener { onDismissRequest() }
    datePickerDialog.show()
}