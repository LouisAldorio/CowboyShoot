package com.catnip.cowboyshoot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.catnip.cowboyshoot.databinding.ActivityMainBinding
import com.catnip.cowboyshoot.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var currentPosition = 1
    private var allPositions = arrayOf<ImageView>()

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
        // Left
        binding.ivPlayerLeft0.visibility = View.INVISIBLE
        binding.ivPlayerLeft1.visibility = View.VISIBLE
        binding.ivPlayerLeft2.visibility = View.INVISIBLE

        // Right
        binding.ivPlayerRight0.visibility = View.INVISIBLE
        binding.ivPlayerRight1.visibility = View.VISIBLE
        binding.ivPlayerRight2.visibility = View.INVISIBLE
    }

    private fun mutatePosition(pos : Int) {
        allPositions.forEachIndexed { index, imageView ->
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
                mutatePosition(currentPosition)
            }
        }
        binding.ivArrowDown.setOnClickListener {
            if (currentPosition < Constants.MAX) {
                currentPosition++
                mutatePosition(currentPosition)
            }
        }
        binding.tvStatusGame.setOnClickListener {
            allPositions.forEachIndexed { index, imageView ->
                if (currentPosition == index) {
                    imageView.visibility = View.VISIBLE
                    imageView.setImageResource(R.drawable.ic_cowboy_left_shoot_true)
                }else {
                    imageView.visibility = View.INVISIBLE
                    imageView.setImageResource(R.drawable.ic_cowboy_left_shoot_false)
                }
            }
        }
    }
}