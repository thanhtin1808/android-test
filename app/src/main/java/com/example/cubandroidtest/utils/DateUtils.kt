package com.example.cubandroidtest.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatIsoDateToReadable(input: String?): String {
        if (input.isNullOrBlank()) return ""

        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            parser.timeZone = TimeZone.getTimeZone("UTC")

            val date = parser.parse(input)

            val formatter = SimpleDateFormat("EEEE, MMM dd, yyyy 'at' h:mm a", Locale.US)
            formatter.timeZone = TimeZone.getDefault()

            formatter.format(date!!)
        } catch (e: Exception) {
            ""
        }
    }
}
