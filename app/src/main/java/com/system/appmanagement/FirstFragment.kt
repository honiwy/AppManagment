package com.system.appmanagement

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
import com.system.appmanagement.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var listAdapter: AppAdapter
    private val viewModel: FirstViewModel by viewModels()
    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        setupListAdapter()
        setUpSpinner()
        getApplicationList()

        binding.buttonUninstall.setOnClickListener {
            viewModel.typedPackageName.value?.let { packageName ->
                deleteApp(packageName)
            }
        }

        return binding.root
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
        listAdapter = AppAdapter(AppAdapter.OnClickListener {
            activity?.packageManager?.getLaunchIntentForPackage(it.packageName)?.let { intent ->
                startActivity(intent)
            }
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
                            p.packageName,
                            p.loadLabel(it).toString(),
                            p.flags,
                            requireContext().packageManager.getApplicationIcon(p.packageName)
                        )
                    )
                }
            }
            viewModel.separateAppToList(filterResult.toList())
        }
    }
}




