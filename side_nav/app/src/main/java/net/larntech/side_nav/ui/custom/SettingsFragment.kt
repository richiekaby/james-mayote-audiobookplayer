package net.larntech.side_nav.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.larntech.side_nav.databinding.CustomFragmentBinding

class CustomFragment : Fragment() {

    private lateinit var customViewModel: CustomViewModel;
    private lateinit var binding: CustomFragmentBinding;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CustomFragmentBinding.inflate(inflater,container,false)
        var root = binding.root;
        customViewModel = ViewModelProvider(this).get(CustomViewModel::class.java)

        val text = customViewModel.text;

        customViewModel.text.observe(this, Observer {
            binding.textCustomFragment.text = it;
        })


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}