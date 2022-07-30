package com.example.a7minutesworkout

object Constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(1, "Jumping Jacks", R.drawable.jumping_jacks, false, false)
        exerciseList.add(jumpingJacks)

        val wallSit = ExerciseModel(2, "Wall Sit", R.drawable.wall_sit, false, false)
        exerciseList.add(wallSit)

        val pushUps = ExerciseModel(3, "Push Ups", R.drawable.push_up, false, false)
        exerciseList.add(pushUps)

        val abdominalCrunches = ExerciseModel(4, "Abdominal Crunches", R.drawable.abdominal_crunch, false, false)
        exerciseList.add(abdominalCrunches)

        val stepUp = ExerciseModel(5, "Step-up onto surface", R.drawable.step_up, false, false)
        exerciseList.add(stepUp)

        val squats = ExerciseModel(6, "Squats", R.drawable.squat, false, false)
        exerciseList.add(squats)

        val tricepsDip = ExerciseModel(7, "Triceps dip on chair", R.drawable.tricep_dip, false, false)
        exerciseList.add(tricepsDip)

        val plank = ExerciseModel(8, "Plank", R.drawable.plank, false, false)
        exerciseList.add(plank)

        val highKneesRun = ExerciseModel(9, "High Knees Running", R.drawable.high_knees, false, false)
        exerciseList.add(highKneesRun)

        val lunge = ExerciseModel(10, "Lunges", R.drawable.lunge, false, false)
        exerciseList.add(lunge)

        val inclinedPushUps = ExerciseModel(11, "Inclined Push-ups", R.drawable.inclined_pushups, false, false)
        exerciseList.add(inclinedPushUps)

        val sidePlank = ExerciseModel(12, "Side Plank", R.drawable.side_plank, false, false)
        exerciseList.add(sidePlank)

        return exerciseList
    }
}