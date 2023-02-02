package com.example.retrofit_l1_t1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.retrofit_l1_t1.R
import com.example.retrofit_l1_t1.databinding.FragmentSecondBinding
import com.example.retrofit_l1_t1.model.Data
import com.example.retrofit_l1_t1.model.OneUserResponse
import com.example.retrofit_l1_t1.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondFragment : Fragment(R.layout.fragment_second) {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private var id: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allCode()

    }

    private fun allCode() {
        id = arguments?.getInt("id")
        if (id != null) {
            getUser()
        } else {
            binding.progressBar3.isVisible = false
            binding.btnFindUserId.isVisible = true
            binding.btnFindUserId.setOnClickListener {
                if (binding.name.text?.isNotBlank()!! && binding.lastName.text?.isNotBlank()!! && binding.email.text?.isNotBlank()!!) {
                    addUser()
                } else {
                    Toast.makeText(requireContext(), "Enter Full Information", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }

    }

    private fun addUser() {
        RetroInstance.apiService().postUser(
            Data(
                "https://avatars.mds.yandex.net/i?id=1f1ffb15af20a68049910619774cac2cbecbd974-8497900-images-thumbs&n=13",
                binding.email.text.toString(),
                binding.name.text.toString(),
                12,
                binding.lastName.text.toString()
            )
        )
        Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show()
        binding.email.text?.clear()
        binding.name.text?.clear()
        binding.lastName.text?.clear()
    }

    private fun getUser() {
        RetroInstance.apiService().getUserById(id!!)
            .enqueue(object : Callback<OneUserResponse> {
                override fun onResponse(
                    call: Call<OneUserResponse>,
                    response: Response<OneUserResponse>
                ) {
                    binding.apply {
                        progressBar3.isVisible = false
                        name.setText(response.body()?.data?.first_name)
                        lastName.setText(response.body()?.data?.last_name)
                        email.setText(response.body()?.data?.email)
                    }
                }

                override fun onFailure(call: Call<OneUserResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}