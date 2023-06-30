package com.aspark.clapingoassignment.repository

import com.aspark.clapingoassignment.Helper
import com.aspark.clapingoassignment.model.Teacher
import com.aspark.clapingoassignment.model.TimeSlot
import org.json.JSONObject


class Repository {

    private val helper = Helper()

    private val jsonData = "{\n" +
            "\"statusCode\": 200, \"teacher\": {\n" +
            "\"isYoutubeConsentSigned\": false,\n" +
            "\"_id\": \"6141ca763a648704b9399029\",\n" +
            "\"name\": \"Rishabh Jain\",\n" +
            "\"description\": \"Hi there! I'm a Java software developer and an Android Engineer. Also, I can develop backend using NodeJS. Apart from this, I have an excellent grasp in the design field also from UI/UX, Wireframing, prototyping and video editing. My strength is devoted to hard work, self-motivation and my work.\",\n" +
            "\"image\": \"https://clapngobucket.s3.amazonaws.com/Images/1643833854632.png\" },\n" +
            "\"rating\": 4.6, \"isBlocked\": false, \"timeslot\": {\n" +
            "\"Sunday\": [ \"00 - 01\", \"01 - 02\", \"02 - 03\", \"03 - 04\", \"04 - 05\", \"05 - 06\", \"06 - 07\", \"07 - 08\", \"08 - 09\", \"09 - 10\", \"10 - 11\", \"11 - 12\", \"12 - 13\", \"13 - 14\", \"14 - 15\", \"15 - 16\", \"16 - 17\", \"17 - 18\", \"18 - 19\", \"19 - 20\", \"20 - 21\", \"21 - 22\", \"22 - 23\",\n" +
            "\"23 - 00\" ],\n" +
            "\"Monday\": [ \"10 - 11\", \"11 - 12\", \"12 - 13\", \"13 - 14\",\n" +
            " \"14 - 15\", \"15 - 16\", \"16 - 17\", \"17 - 18\", \"18 - 19\", \"19 - 20\", \"20 - 21\", \"21 - 22\", \"22 - 23\", \"23 - 00\"\n" +
            "], \"Tuesday\": [\n" +
            "\"12 - 13\", \"13 - 14\", \"14 - 15\", \"15 - 16\", \"16 - 17\", \"17 - 18\", \"18 - 19\", \"19 - 20\", \"20 - 21\", \"21 - 22\", \"22 - 23\", \"23 - 00\"\n" +
            "],\n" +
            "\"Wednesday\": [], \"Thursday\": [\n" +
            "\"00 - 01\", \"01 - 02\", \"02 - 03\", \"03 - 04\", \"04 - 05\", \"05 - 06\", \"06 - 07\", \"07 - 08\", \"08 - 09\", \"09 - 10\", \"10 - 11\", \"11 - 12\", \"12 - 13\", \"13 - 14\", \"14 - 15\", \"15 - 16\", \"16 - 17\", \"17 - 18\", \"18 - 19\", \"19 - 20\", \"20 - 21\",\n" +
            "\n" +
            " \"21 - 22\", \"22 - 23\", \"23 - 00\"\n" +
            "], \"Friday\": [\n" +
            "\"00 - 01\", \"01 - 02\", \"02 - 03\", \"03 - 04\", \"04 - 05\", \"05 - 06\", \"06 - 07\", \"07 - 08\", \"08 - 09\", \"09 - 10\", \"10 - 11\", \"11 - 12\", \"12 - 13\", \"13 - 14\", \"14 - 15\", \"15 - 16\", \"16 - 17\", \"17 - 18\", \"18 - 19\", \"19 - 20\", \"20 - 21\", \"21 - 22\", \"22 - 23\", \"23 - 00\"\n" +
            "], \"Saturday\": [\n" +
            "\"00 - 01\", \"01 - 02\", \"02 - 03\", \"03 - 04\", \"04 - 05\", \"05 - 06\", \"06 - 07\", \"07 - 08\", \"08 - 09\", \"09 - 10\", \"10 - 11\", \"11 - 12\", \"12 - 13\", \"13 - 14\", \"14 - 15\", \"15 - 16\", \"16 - 17\",\n" +
            "\n" +
            "\"17 - 18\", \"18 - 19\", \"19 - 20\", \"20 - 21\", \"21 - 22\", \"22 - 23\", \"23 - 00\"\n" +
            "] },\n" +
            "\"bookedTimings\": { \"Sunday\": [], \"Monday\": [], \"Tuesday\": [], \"Wednesday\": [], \"Thursday\": [\n" +
            "\"16:30 - 16:55\",\n" +
            "\"17:00 - 17:25\" ],\n" +
            "\"Friday\": [\n" +
            "\"00:30 - 00:55\", \"05:00 - 05:25\"\n" +
            "],\n" +
            "\"Saturday\": [] }\n" +
            "}"

    fun getTeacherData(callback: (Teacher) -> Unit) {

        val jsonObj = JSONObject(jsonData)
        val teacherObj = jsonObj.getJSONObject("teacher")
        val timeSlotObj = jsonObj.getJSONObject("timeslot")
        val bookedTimeObj = jsonObj.getJSONObject("bookedTimings")

        val teacher = Teacher()
        teacher.name = teacherObj.getString("name")
        teacher.description = teacherObj.getString("description")
        teacher.image = teacherObj.getString("image")
        teacher.rating = jsonObj.getDouble("rating")

        val daysOfWeek = helper.weekdays

        for(day in daysOfWeek) {

            val availableTimeSlots = timeSlotObj.getJSONArray(day)
            val bookedTimeSlots = bookedTimeObj.getJSONArray(day)
            val timeSlotList = arrayListOf<TimeSlot>()
            val bookedList = arrayListOf<TimeSlot>()

            for ( i in 0 until availableTimeSlots.length()) {

                val time = availableTimeSlots[i] as String
                val slots = helper.splitTimeSlots(time)
                timeSlotList.addAll(slots)
            }

            for (i in 0 until bookedTimeSlots.length()) {

                val booked = bookedTimeSlots[i] as String
                val bookedSlot = helper.convertBookedTimingToTimeSlot(booked)

                for (timeSlot in timeSlotList) {

                    if (helper.isTimeSlotOverlap(timeSlot, bookedSlot)) {
                        timeSlot.isBooked = true
                        break
                    }
                }
            }

            teacher.timeslot[day] = timeSlotList
            teacher.bookedTimings[day] = bookedList
        }

        callback(teacher)
    }
}


