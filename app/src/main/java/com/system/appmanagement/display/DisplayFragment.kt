package com.system.appmanagement.display

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.system.appmanagement.R
import com.system.appmanagement.data.App
import com.system.appmanagement.databinding.FragmentDisplayBinding
import com.system.appmanagement.extension.getVmFactory


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DisplayFragment : Fragment() {

    private lateinit var listAdapter: DisplayAppAdapter
    private lateinit var binding: FragmentDisplayBinding
    private val viewModel by viewModels<DisplayViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDisplayBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupListAdapter()
        setUpSpinner()
        getApplicationList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun deleteApp(packageName: String) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun setUpSpinner() {
        binding.spinner.adapter = CategorySpinnerAdapter(
            requireContext().resources.getStringArray(
                R.array.app_category_list
            )
        )
        viewModel.selectedAppCategoryPosition.observe(viewLifecycleOwner, Observer {
            viewModel.prepareDisplayApps()
            binding.recyclerApp.adapter?.notifyDataSetChanged()
        })
    }

    private fun setupListAdapter() {
        listAdapter = DisplayAppAdapter(DisplayAppAdapter.OnClickListener {
            activity?.packageManager?.getLaunchIntentForPackage(it.packageName)?.let { intent ->
                startActivity(intent)
            }
        }, DisplayAppAdapter.OnLongClickListener {
            deleteApp(it.packageName)
        })
        binding.recyclerApp.adapter = listAdapter
        viewModel.displayedApp.observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(it)
        })
    }

    private fun getApplicationList() {
        val pm = activity?.packageManager
        pm?.let {
            val packages = it.getInstalledApplications(PackageManager.GET_META_DATA)
            val filterResult = mutableListOf<App>()
            for (p in packages) {
                if (pm.getLaunchIntentForPackage(p.packageName) != null) {
                    filterResult.add(
                        App(
                            packageName = p.packageName,
                            appName = p.loadLabel(it).toString(),
                            flags = p.flags,
                            icon = requireContext().packageManager.getApplicationIcon(p.packageName)
                        )
                    )
                }
            }
            viewModel.separateAppToList(filterResult.toList())
        }
    }
}




