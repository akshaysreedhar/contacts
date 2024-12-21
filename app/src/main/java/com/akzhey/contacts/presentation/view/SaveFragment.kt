package com.akzhey.contacts.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.akzhey.contacts.R
import com.akzhey.contacts.databinding.FragmentSaveNumberBinding
import com.akzhey.contacts.domain.models.PhoneNumber
import com.akzhey.contacts.presentation.view.adapter.NumberAdapter
import com.akzhey.contacts.presentation.viewModel.SaveViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SaveFragment : Fragment() {

    private var _binding: FragmentSaveNumberBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: SaveViewModel by viewModels()
    private val args: SaveFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveNumberBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAdapter()
    }

    /**
     * Setting up the view and actions
     */
    private fun setupView() {
        with(binding) {
            val phoneNumberViewList = listOf(
                saveNumberOne,
                saveNumberTwo,
                saveNumberThree,
                saveNumberFour
            )
            phoneNumberViewList.forEachIndexed { index, linearLayout ->
                linearLayout.tvNumber.text = args.numberList[index]
            }

            btSave.setOnClickListener {
                val listOfPhoneNumbersToBeSaved = listOf(
                    PhoneNumber(
                        args.numberList[0],
                        phoneNumberViewList[0].spNumber.selectedItem.toString()
                    ),
                    PhoneNumber(
                        args.numberList[1],
                        phoneNumberViewList[1].spNumber.selectedItem.toString()
                    ),
                    PhoneNumber(
                        args.numberList[2],
                        phoneNumberViewList[2].spNumber.selectedItem.toString() + " 1"
                    ),
                    PhoneNumber(
                        args.numberList[3],
                        phoneNumberViewList[3].spNumber.selectedItem.toString() + " 2"
                    ),
                )
                val canProceed = viewmodel.validateType(listOfPhoneNumbersToBeSaved)
                if (canProceed) {
                    viewmodel.savePhoneNumbersToDb(listOfPhoneNumbersToBeSaved) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            gotoShowFragment()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Please choose a type", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    /**
     * Navigate to show contact screen
     */
    private fun gotoShowFragment() {
        val action = SaveFragmentDirections.saveToShow()
        val options = NavOptions.Builder().setPopUpTo(R.id.navigation_save, true).build()
        findNavController().navigate(action, navOptions = options)
    }

    /**
     * Setting up the adapter for spinner
     */
    private fun setupAdapter() {
        with(binding) {
            val spinnerList = listOf(
                saveNumberOne.spNumber,
                saveNumberTwo.spNumber,
                saveNumberThree.spNumber,
                saveNumberFour.spNumber,
            )

            spinnerList.forEach {
                it.adapter =
                    NumberAdapter(
                        requireContext(),
                        viewmodel.phoneNumberTypeList,
                        viewmodel.selectedTypePositionList
                    )
                it.setSelection(4)
                it.onItemSelectedListener = spinnerClickedListener
            }
        }
    }


    private val spinnerClickedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            //Do nothing
        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            viewmodel.selectedTypePositionList.add(position) //On selection of item, add it to a set, for disabling
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}