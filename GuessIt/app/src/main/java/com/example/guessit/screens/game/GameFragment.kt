package com.example.guessit.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.guessit.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guessit.databinding.GameFragmentBinding
import androidx.navigation.fragment.findNavController


class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater, R.layout.game_fragment, container, false
        )

        Log.i("GameViewModel", "Called ViewModelProvider")
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }

        viewModel.score.observe(
            viewLifecycleOwner, Observer { newScore ->
                binding.scoreText.text = newScore.toString()
            })
        viewModel.word.observe(
            viewLifecycleOwner, Observer { newWord -> binding.wordText.text = newWord })
        viewModel.eventGameFinish.observe(
            viewLifecycleOwner, Observer { hasFinish ->
                if (hasFinish) {
                    gameFinished()
                    viewModel.onGameFinishComplete()
                }
            })
        viewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
        })

        return binding.root

    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action =
            GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value ?: 0)
        findNavController().navigate(action)
        Log.i("GameFragment", "Game finished.")
    }

}