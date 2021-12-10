package com.system.appmanagement.block

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.system.appmanagement.utils.SharedPreference
import com.system.appmanagement.R
import com.system.appmanagement.databinding.FragmentBlockBinding
import com.system.appmanagement.extension.getVmFactory


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class BlockFragment : Fragment() {

    private lateinit var binding: FragmentBlockBinding
    private val viewModel by viewModels<BlockViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBlockBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerApp.adapter = BlockAppAdapter(viewModel)

        checkPermission()

        viewModel.displayedApp.observe(viewLifecycleOwner, Observer {

            binding.recyclerApp.adapter?.notifyDataSetChanged()
        })

        return binding.root

    }

    private fun checkPermission() {
        val appOpsManager =
            requireContext().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

        val mode = appOpsManager.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            requireContext().packageName
        )
        if (mode != AppOpsManager.MODE_ALLOWED) {
            Toast.makeText(
                requireContext(),
                "Allow access permission to block apps",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.switchMonitorApp.setOnCheckedChangeListener { _, isChecked ->
            viewModel.changeBlockState(isChecked)
        }
    }
}