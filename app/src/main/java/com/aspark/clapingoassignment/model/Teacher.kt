package com.aspark.clapingoassignment.model

class Teacher {

    var name: String? = null
    var description: String? = null
    var image: String? = null
    var rating: Double = 0.0
    var timeslot:  HashMap<String, ArrayList<TimeSlot>> = hashMapOf()
    var bookedTimings:  HashMap<String, ArrayList<TimeSlot>> = hashMapOf()
}