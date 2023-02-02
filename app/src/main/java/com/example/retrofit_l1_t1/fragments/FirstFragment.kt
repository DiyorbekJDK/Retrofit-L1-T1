package com.example.retrofit_l1_t1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit_l1_t1.R
import com.example.retrofit_l1_t1.adapter.UserAdapter
import com.example.retrofit_l1_t1.databinding.FragmentFirstBinding
import com.example.retrofit_l1_t1.model.UserResponse
import com.example.retrofit_l1_t1.network.RetroInstance
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val adapter2 by lazy { UserAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allCode()
    }

    private fun allCode() {
        binding.floatingActionButton.setOnClickListener {
            showBottomSheetDialog()
        }
        getAllUsers()

    }

    private fun getAllUsers() {
        RetroInstance.apiService().getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    binding.progressBar.isVisible = false
                    binding.recyclerView2.apply {
                        adapter = adapter2
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                    adapter2.submitList(response.body()?.data)
                    adapter2.onClick = {
                        val bundle = bundleOf("id" to it)
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Snackbar.make(requireView(), "Error", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        val btn1: MaterialButton? = bottomSheetDialog.findViewById(R.id.btnFindUserId)
        val btn2: MaterialButton? = bottomSheetDialog.findViewById(R.id.btnFindUser)
        btn1?.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            bottomSheetDialog.dismiss()
        }
        btn2?.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_findByIdFragment)
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }
}