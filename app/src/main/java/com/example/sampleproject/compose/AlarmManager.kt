package com.example.sampleproject.compose

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import com.example.sampleproject.util.AlarmReceiver
import java.util.Calendar
import java.util.Locale

@Composable
fun AlarmManager(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent =
        Intent(context.applicationContext, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context.applicationContext,
                1,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

    val calendar: Calendar = Calendar.getInstance(Locale.KOREAN).apply {
        set(Calendar.HOUR_OF_DAY, 17)
        set(Calendar.MINUTE, 24)
        add(Calendar.MINUTE, -1)
    }

    // If the trigger time you specify is in the past, the alarm triggers immediately.
    // if soo just add one day to required calendar
    // Note: i'm also adding one min cuz if the user clicked on the notification as soon as
    // he receive it it will reschedule the alarm to fire another notification immediately
    if (Calendar.getInstance(Locale.KOREAN)
            .apply { add(Calendar.MINUTE, 1) }.timeInMillis - calendar.timeInMillis > 0
    ) {
        calendar.add(Calendar.DATE, 1)
    }

    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        intent
    )
}

//    fun stopReminder(
//        context: Context,
//        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
//    ) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
//            PendingIntent.getBroadcast(
//                context,
//                reminderId,
//                intent,
//                PendingIntent.FLAG_IMMUTABLE
//            )
//        }
//        alarmManager.cancel(intent)
//    }