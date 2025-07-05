package com.example.guessit.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.guessit.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.guessit.databinding.ScoreFragmentBinding
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController


class ScoreFragment : Fragment() {
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.score_fragment, container, false
        )

        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        val finalScore = scoreFragmentArgs.score

//        val finalScore = ScoreFragmentArgs.fromBundle(requireArguments()).score
        viewModelFactory = ScoreViewModelFactory(finalScore)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)

        // Get args using by navArgs property delegate
        binding.scoreText.text = finalScore.toString()
        binding.playAgainButton.setOnClickListener { onPlayAgain() }

        return binding.root
    }

    private fun onPlayAgain() {
        findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToGameFragment())
    }

}