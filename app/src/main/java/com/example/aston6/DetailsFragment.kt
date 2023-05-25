package com.example.aston6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.aston6.Constants.FIRST_FRAGMENT_BUNDLE
import com.example.aston6.Constants.FIRST_FRAGMENT_BUNDLE_KEY
import com.example.aston6.Constants.FIRST_FRAGMENT_BUNDLE_KEY_NUMBER
import com.example.aston6.Constants.FIRST_FRAGMENT_RESULT_KEY
import com.example.aston6.Constants.NAME_KEY_DETAILS
import com.example.aston6.Constants.NUMBER_KEY_DETAILS
import com.example.aston6.Constants.TELEPHONE_NUMBER_BUNDLE_KEY
import com.example.aston6.Constants.TELEPHONE_NUMBER_RESULT_KEY
import com.example.aston6.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = this.arguments
        val nameOfContact = args?.getString(NAME_KEY_DETAILS)
        val numberOfContact = args?.getString(NUMBER_KEY_DETAILS)
        binding.tvNameDetails.text = nameOfContact
        binding.tvNumberDetails.text = numberOfContact

        binding.bDetails.setOnClickListener {
            with(binding) {
                edNumber.visibility = View.VISIBLE
                edName.visibility = View.VISIBLE
                bDetails.visibility = View.GONE
                bSave.visibility = View.VISIBLE
            }
        }

        binding.bSave.setOnClickListener {
            val bundle = Bundle()
            bundle.apply {
                putString(FIRST_FRAGMENT_BUNDLE_KEY, binding.edName.text.toString())
                putString(FIRST_FRAGMENT_BUNDLE_KEY_NUMBER, binding.edNumber.text.toString())
            }
            setFragmentResult(
                FIRST_FRAGMENT_RESULT_KEY,
                bundle
            )
            parentFragmentManager.popBackStack()
        }
    }

    companion object {

    }
}