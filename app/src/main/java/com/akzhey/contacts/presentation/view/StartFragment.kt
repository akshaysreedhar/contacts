package com.akzhey.contacts.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.akzhey.contacts.common.Condition
import com.akzhey.contacts.common.Response
import com.akzhey.contacts.common.hide
import com.akzhey.contacts.common.show
import com.akzhey.contacts.databinding.FragmentStartBinding
import com.akzhey.contacts.presentation.viewModel.StartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        numberObserver()
    }

    /**
     * Setting up the view and actions
     */
    private fun setupView() {
        binding.btStart.setOnClickListener {
            viewmodel.getPhoneNumbers()
        }
    }

    /**
     * Observer for phone number response
     */
    private fun numberObserver() {
        viewmodel.phoneNumber.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    gotoSaveScreen(it.data ?: emptyList())
                }

                is Response.Error -> {
                    showLayout(Condition.ERROR, it.message ?: "")
                }

                is Response.Loading -> {
                    showLayout(Condition.LOADING)
                }

                null -> {
                    showLayout(Condition.SHOW_BUTTON)
                }
            }
        }
    }

    /**
     * Navigate to save contact screen
     */
    private fun gotoSaveScreen(phoneNumberList: List<String>) {
        viewmodel.setResponseToNull()
        val action = StartFragmentDirections.startToSave(phoneNumberList.toTypedArray())
        findNavController().navigate(action)
    }


    /**
     * Show progress or error text based on received response
     */
    private fun showLayout(condition: Condition = Condition.SHOW_BUTTON, text: String = "") {
        with(binding) {
            when (condition) {
                Condition.SHOW_BUTTON -> {
                    cpProgress.hide()
                    tvError.hide()
                    btStart.show()
                }

                Condition.ERROR -> {
                    tvError.show()
                    cpProgress.hide()
                    btStart.hide()
                    tvError.text = text
                }

                Condition.LOADING -> {
                    cpProgress.show()
                    tvError.hide()
                    btStart.hide()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

