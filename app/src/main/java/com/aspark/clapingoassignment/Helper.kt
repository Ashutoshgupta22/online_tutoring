package com.aspark.clapingoassignment

import com.aspark.clapingoassignment.model.TimeSlot
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Helper {

    val weekdays = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday")

    fun convertDateFormat(day: Int, month: Int, year: Int): String {

        var sDay: String = day.toString()
        var sMonth: String = month.toString()

        if(sDay.length == 1)
            sDay = "0$sDay"

        if (sMonth.length == 1)
            sMonth = "0$sMonth"

        val date = "$sDay $sMonth, $year"

        val inputFormat = DateTimeFormatter.ofPattern("dd MM, yyyy")
        val outputFormat = DateTimeFormatter.ofPattern("dd MMM, yyyy")
        val parsedDate = LocalDate.parse(date, inputFormat)
        return outputFormat.format(parsedDate)
    }

     fun splitTimeSlots(time: String): List<TimeSlot> {

        val slots = arrayListOf<TimeSlot>()
        val parts = time.split(" - ")

        var startTime = parts[0]
        var endTime = parts[1]

        if (startTime.length == 2)
            startTime += ":00"
        if (endTime.length == 2)
            endTime += ":00"

        val slotDuration = 15 // 15 minutes
        val breakDuration = 5
        var currentStartTime = startTime
        var currentEndTime = addMinutesToTime(startTime, slotDuration)

        while (compareTime(currentEndTime, endTime) <= 0) {

            val timeSlot = TimeSlot(currentStartTime, currentEndTime)
            slots.add(timeSlot)

            currentStartTime = addMinutesToTime(currentEndTime, breakDuration)
            currentEndTime = addMinutesToTime(currentStartTime, slotDuration)
        }

        return slots
    }

    private fun addMinutesToTime(time: String, minutes: Int): String {

        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val parsedTime =  LocalTime.parse(time, formatter)

        val updatedTime = parsedTime.plusMinutes(minutes.toLong())

//        return if (time == "00:00") {
//            updatedTime.format(formatter).replace("24:", "00:")
//        } else {
//            updatedTime.format(formatter)
//        }

        return updatedTime.format(formatter)
    }

    fun convertBookedTimingToTimeSlot(booked: String): TimeSlot {
        val parts = booked.split(" - ")
        return TimeSlot(parts[0], parts[1])
    }

    fun isTimeSlotOverlap(timeSlot: TimeSlot, bookedSlot: TimeSlot): Boolean {
        val slotStartTime = timeSlot.startTime
        val slotEndTime = timeSlot.endTime
        val bookedStartTime = bookedSlot.startTime
        val bookedEndTime = bookedSlot.endTime

        //return (compareTime(startTime1, endTime2) < 0 && compareTime(startTime2, endTime1) < 0)
        return (compareTime(bookedStartTime, slotStartTime) <= 0 &&
                compareTime(slotEndTime, bookedEndTime) <= 0)
    }

    private fun compareTime(time1: String, time2: String): Int {

        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        val parsedTime1 = LocalTime.parse(time1, formatter)
        val parsedTime2 = LocalTime.parse(time2, formatter)

        return parsedTime1.compareTo(parsedTime2)
    }
}