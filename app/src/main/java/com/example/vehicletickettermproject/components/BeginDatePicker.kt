package com.example.vehicletickettermproject.components

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.vehicletickettermproject.R
import java.util.Calendar

@Composable
fun BeginDatePicker(
    context: Context,
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val calendar = remember { Calendar.getInstance() }

    val firstDayOfWeek = calendar.firstDayOfWeek

    val themedContext = ContextThemeWrapper(context, R.style.CustomDatePickerDialogTheme)

    val datePickerDialog = android.app.DatePickerDialog(
        themedContext,
        { _, year, month, day ->
            onDateSelected(year, month, day)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.setOnShowListener {
        datePickerDialog.datePicker.firstDayOfWeek = firstDayOfWeek
    }

    datePickerDialog.setOnDismissListener { onDismissRequest() }
    datePickerDialog.show()
}