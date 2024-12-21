package com.akzhey.contacts.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.akzhey.contacts.databinding.FragmentShowNumberBinding
import com.akzhey.contacts.domain.models.PhoneNumber
import com.akzhey.contacts.presentation.viewModel.ShowViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShowFragment : Fragment() {

    private var _binding: FragmentShowNumberBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: ShowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowNumberBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        phoneNumberObserver()
    }

    /**
     * Setting up the views and actions
     */
    private fun setupView() {
        binding.btShow.setOnClickListener {
            viewmodel.getPhoneNumbersFromDb()
        }
    }

    /**
     * Observing phone number from db
     */
    private fun phoneNumberObserver() {
        with(binding) {
            val phoneNumberViewList = listOf(
                numberOne,
                numberTwo,
                numberThree,
                numberFour,
            )
            viewmodel.phoneNumberData.observe(viewLifecycleOwner) { phoneNumber ->
                phoneNumber.forEachIndexed { index: Int, phone: PhoneNumber ->
                    phoneNumberViewList[index].tvShowNumber.text = phone.number
                    phoneNumberViewList[index].tvShowType.text = phone.type
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}