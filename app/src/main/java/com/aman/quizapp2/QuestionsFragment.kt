package com.aman.quizapp2

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_questions.*


class QuestionsFragment : Fragment() {
    lateinit var database: DatabaseReference
    var index = 0
    var reward = 0

    lateinit var desc: TextView
    lateinit var op1: Button
    lateinit var op2: Button
    lateinit var op3: Button
    lateinit var op4: Button

    lateinit var d: String
    lateinit var o1: String
    lateinit var o2: String
    lateinit var o3: String
    lateinit var o4: String
    lateinit var ans: String
    lateinit var nextButton: Button
    lateinit var prevButton: Button
    lateinit var cnt: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


//        database.child("question1").get().addOnSuccessListener {
//            Log.d("firebase", "Got value ${it.value}")
//        }.addOnFailureListener{
//            Log.d("firebase", "Error getting data", it)
//        }

        database = Firebase.database.reference

        var view = inflater.inflate(R.layout.fragment_questions, container, false)

        render(view)

        if (index == 0) {
            prevButton.setVisibility(View.INVISIBLE)
            op1.setVisibility(View.INVISIBLE)
            op2.setVisibility(View.INVISIBLE)
            op3.setVisibility(View.INVISIBLE)
            op4.setVisibility(View.INVISIBLE)
        }

        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                cnt.setText("seconds : " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                cnt.setText("done!")
                next(view)
            }
        }.start()



        nextButton.setOnClickListener()
        {
            next(view)
        }
        prevButton.setOnClickListener()
        {
            prev(view)
        }

        return view
    }

    private fun next(view: View) {
        index++
        if (index == 11) {
            val bundle = bundleOf("reward" to reward.toString())
            Navigation.findNavController(view)
                .navigate(R.id.action_questionsFragment_to_resultsFragment, bundle)
        }


        var q = "question" + index

        database.child(q).get().addOnSuccessListener {
            Log.d("firebase", "Got value ${it.value}")
            if (it.exists()) {
                prevButton.setVisibility(View.VISIBLE)
                op1.setVisibility(View.VISIBLE)
                op2.setVisibility(View.VISIBLE)
                op3.setVisibility(View.VISIBLE)
                op4.setVisibility(View.VISIBLE)


                 d = it.child("description").value as String
                 o1 = it.child("option1").value as String
                 o2 = it.child("option2").value as String
                 o3 = it.child("option3").value as String
                 o4 = it.child("option4").value as String
                 ans = it.child("answer").value as String


                desc.setText(d)
                op1.setText(o1)
                op2.setText(o2)
                op3.setText(o3)
                op4.setText(o4)

                buttonClick(view)
            }
        }


    }

    private fun prev(view: View) {
        index--
        var q = "question" + index
        database.child(q).get().addOnSuccessListener {
            Log.d("firebase", "Got value ${it.value}")
            if (it.exists()) {
                render(view)

                 d = it.child("description").value as String
                 o1 = it.child("option1").value as String
                 o2 = it.child("option2").value as String
                 o3 = it.child("option3").value as String
                 o4 = it.child("option4").value as String
                 ans = it.child("answer").value as String


                desc.setText(d)
                op1.setText(o1)
                op2.setText(o2)
                op3.setText(o3)
                op4.setText(o4)

                buttonClick(view)
            }
        }
    }

         fun render(view: View) {

            op1 = view.findViewById(R.id.option1)
            op2 = view.findViewById(R.id.option2)
            op3 = view.findViewById(R.id.option3)
            op4 = view.findViewById(R.id.option4)
            desc = view.findViewById(R.id.description)
            prevButton = view.findViewById(R.id.prvBtn)
            nextButton = view.findViewById(R.id.nxtBtn)
            cnt = view.findViewById(R.id.countdownCount)
        }

         fun buttonClick(view: View) {
            op1.setOnClickListener()
            {
                if (ans == o1) {
                    reward++
                }
            }
            op2.setOnClickListener()
            {
                if (ans == o2) {
                    reward++
                }
            }
            op3.setOnClickListener()
            {
                if (ans == o3) {
                    reward++
                }
            }
        }

    }

