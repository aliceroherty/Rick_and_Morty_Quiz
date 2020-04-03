package com.nbcc.assignment1


import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.nbcc.assignment1.databinding.FragmentCheatBinding
import com.nbcc.quiz.Question

class CheatFragment : Fragment() {
    private lateinit var binding: FragmentCheatBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cheat, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.navigateToGame.observe(viewLifecycleOwner, Observer {
            if (it) {
                NavHostFragment.findNavController(this).navigate(CheatFragmentDirections.actionCheatFragmentToGameFragment())
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.hideAnswer()
    }
}
