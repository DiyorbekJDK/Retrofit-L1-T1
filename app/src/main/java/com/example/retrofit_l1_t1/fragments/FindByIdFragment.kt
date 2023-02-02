package com.example.retrofit_l1_t1.fragments


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.retrofit_l1_t1.R
import com.example.retrofit_l1_t1.databinding.FragmentFindByIdBinding
import com.example.retrofit_l1_t1.model.OneUserResponse
import com.example.retrofit_l1_t1.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindByIdFragment : Fragment(R.layout.fragment_find_by_id) {
    private var _binding: FragmentFindByIdBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFindByIdBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allCode()
    }

    private fun allCode() {
        binding.btnFindUserId.setOnClickListener {
            if (binding.id.text?.isNotBlank()!!) {
                getUser()
            } else {
                Toast.makeText(requireContext(), "Enter Id!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUser() {
        RetroInstance.apiService().getUserById(binding.id.text.toString().toInt())
            .enqueue(object : Callback<OneUserResponse> {
                override fun onResponse(
                    call: Call<OneUserResponse>,
                    response: Response<OneUserResponse>
                ) {
                    binding.apply {
                        progressBar2.isVisible = false
                        imageView2.isVisible = true
                        name.isVisible = true
                        lastName.isVisible = true
                        imageView2.setImageURI(Uri.parse(response.body()?.data?.avatar))
                        name.text = response.body()?.data?.first_name
                        lastName.text = response.body()?.data?.last_name
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