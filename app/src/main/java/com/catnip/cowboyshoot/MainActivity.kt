package com.catnip.cowboyshoot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.catnip.cowboyshoot.databinding.ActivityMainBinding
import com.catnip.cowboyshoot.utils.Constants
import com.catnip.cowboyshoot.utils.Math

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var currentPosition = 1
    private var allPositions = arrayOf<ImageView>()
    private var allComputerPosition = arrayOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setInitialState()
        setListeners()
    }

    private fun setInitialState() {
        allPositions = arrayOf(binding.ivPlayerLeft0, binding.ivPlayerLeft1, binding.ivPlayerLeft2)
        allComputerPosition = arrayOf(binding.ivPlayerRight0, binding.ivPlayerRight1, binding.ivPlayerRight2)
        currentPosition = 1
        // Left
        binding.ivPlayerLeft0.visibility = View.INVISIBLE
        binding.ivPlayerLeft1.visibility = View.VISIBLE
        binding.ivPlayerLeft2.visibility = View.INVISIBLE
        allPositions.forEach {
            it.setImageResource(R.drawable.ic_cowboy_left_shoot_false)
        }

        // Right
        binding.ivPlayerRight0.visibility = View.INVISIBLE
        binding.ivPlayerRight1.visibility = View.VISIBLE
        binding.ivPlayerRight2.visibility = View.INVISIBLE
        allComputerPosition.forEach {
            it.setImageResource(R.drawable.ic_cowboy_right_shoot_false)
        }
    }

    private fun mutatePosition(pos : Int, array : Array<ImageView>) {
        array.forEachIndexed { index, imageView ->
            if (pos == index) {
                imageView.visibility = View.VISIBLE
            }else {
                imageView.visibility = View.INVISIBLE
            }
        }
    }

    private fun setListeners() {
        binding.ivArrowUp.setOnClickListener {
            if(currentPosition > Constants.MIN) {
                currentPosition--
                mutatePosition(currentPosition, allPositions)
            }
        }
        binding.ivArrowDown.setOnClickListener {
            if (currentPosition < Constants.MAX) {
                currentPosition++
                mutatePosition(currentPosition, allPositions)
            }
        }
        binding.tvStatusGame.setOnClickListener {
            val computerPos = Math.random()
            decideWinner(computerPos, currentPosition)
        }
    }

    private fun decideWinner(computerPos : Int, currentPosition : Int) {
        if (currentPosition != computerPos) {

            mutatePosition(computerPos, allComputerPosition)
            mutatePosition(currentPosition, allPositions)
            allPositions[currentPosition].setImageResource(R.drawable.ic_cowboy_left_shoot_true)

            binding.tvWinnerGame.text = "You Win!"

        }else if(currentPosition == computerPos) {

            mutatePosition(computerPos, allComputerPosition)
            mutatePosition(currentPosition, allPositions)
            allComputerPosition[computerPos].setImageResource(R.drawable.ic_cowboy_right_shoot_true)

            binding.tvWinnerGame.text = "You Lose!"
        }
        binding.tvStatusGame.text = "Restart"
        binding.tvStatusGame.setOnClickListener {
            setInitialState()
            setListeners()
            binding.tvStatusGame.text = "Shoot"
            binding.tvWinnerGame.text = null
        }
    }
}