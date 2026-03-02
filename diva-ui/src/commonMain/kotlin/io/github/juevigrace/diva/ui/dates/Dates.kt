package io.github.juevigrace.diva.ui.dates

import androidx.compose.material3.SelectableDates
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

object Dates {
    val selectableDatesFromToday: SelectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            val utcTime: LocalDateTime = Instant.fromEpochMilliseconds(utcTimeMillis).toLocalDateTime(TimeZone.UTC)
            return utcTime.date >= currentTime.date
        }

        override fun isSelectableYear(year: Int): Boolean {
            val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            return year >= currentTime.year
        }
    }

    val selectableDatesBeforeToday: SelectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            val utcTime: LocalDateTime = Instant.fromEpochMilliseconds(utcTimeMillis).toLocalDateTime(TimeZone.UTC)
            return utcTime.date <= currentTime.date
        }

        override fun isSelectableYear(year: Int): Boolean {
            val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            return year <= currentTime.year
        }
    }

    fun rangeSelectableDates(
        isInitialDate: Boolean = false,
        isTargetDate: Boolean = false,
    ): SelectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            val utcTime: LocalDateTime = Instant.fromEpochMilliseconds(utcTimeMillis).toLocalDateTime(TimeZone.UTC)
            return when {
                isInitialDate -> {
                    utcTime.date <= currentTime.date
                }
                isTargetDate -> {
                    utcTime.date >= currentTime.date
                }
                else -> {
                    utcTime.date == currentTime.date
                }
            }
        }

        override fun isSelectableYear(year: Int): Boolean = true
    }
}
