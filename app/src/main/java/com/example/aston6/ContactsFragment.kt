package com.example.aston6

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.SearchView

import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.appcompat.widget.SearchView.OnQueryTextListener

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aston6.Constants.FIRST_FRAGMENT_BUNDLE_KEY
import com.example.aston6.Constants.FIRST_FRAGMENT_BUNDLE_KEY_NUMBER
import com.example.aston6.Constants.FIRST_FRAGMENT_RESULT_KEY
import com.example.aston6.Constants.NAME_KEY_DETAILS
import com.example.aston6.Constants.NUMBER_KEY_DETAILS
import com.example.aston6.Constants.SAVE_STATE_INT
import com.example.aston6.Constants.SAVE_STATE_KEY
import com.example.aston6.adapter.ContentListAdapter
import com.example.aston6.databinding.FragmentContactsBinding
import com.example.aston6.databinding.FragmentDetailsBinding
import com.example.aston6.model.ContentModel
import com.github.javafaker.Faker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class ContactsFragment : Fragment() {

    private val bundle = Bundle()
    private var list: MutableList<ContentModel>? = null
    private val gson = Gson()
    private val faker = Faker()
    private lateinit var binding: FragmentContactsBinding
    private var position = 0

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list = MutableList(100) { index ->
            ContentModel(
                id = "$index",
                content = "${faker.name().firstName()} ${faker.name().lastName()}",
                number = "+7 (${faker.number().randomNumber(3, true)})" +
                        " ${faker.number().randomNumber(3, true)}-" +
                        "${faker.number().randomNumber(2, true)}-" +
                        "${faker.number().randomNumber(2, true)}"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = ContentListAdapter({ contentModel, positionList ->
            bundle.putString(NAME_KEY_DETAILS, contentModel.content)
            bundle.putString(NUMBER_KEY_DETAILS, contentModel.number)
            val detFragment = DetailsFragment()
            detFragment.arguments = bundle
            position = positionList
            launchDetailsFragment(R.id.main_layout, detFragment)
        }, this.requireContext(), {
            list?.removeAt(it)
        })
        recyclerView.adapter = adapter

        if (savedInstanceState != null) {
            val listString = savedInstanceState.getString(SAVE_STATE_KEY)
            val type: Type = object : TypeToken<List<ContentModel>>() {}.getType()
            list = gson.fromJson(listString, type)
            position = savedInstanceState.getInt(SAVE_STATE_INT)
        }

        setFragmentResultListener(FIRST_FRAGMENT_RESULT_KEY) { requestKey, bundle ->
            val nameValue = bundle.getString(FIRST_FRAGMENT_BUNDLE_KEY).orEmpty()
            val numberValue = bundle.getString(FIRST_FRAGMENT_BUNDLE_KEY_NUMBER).orEmpty()
            if (nameValue.isNotEmpty()) list?.get(position)?.content = nameValue
            if (numberValue.isNotEmpty()) list?.get(position)?.number = numberValue
        }
        adapter.submitList(list)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val jsonList = gson.toJson(list)
        outState.putString(SAVE_STATE_KEY, jsonList)
        outState.putInt(SAVE_STATE_INT, position)
    }

    private fun launchDetailsFragment(layout: Int, fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(layout, fragment)
            .addToBackStack(null)
            .commit()
    }
    companion object {

    }
}