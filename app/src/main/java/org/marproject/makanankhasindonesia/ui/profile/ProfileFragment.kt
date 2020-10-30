package org.marproject.makanankhasindonesia.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.marproject.makanankhasindonesia.R
import org.marproject.makanankhasindonesia.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    // binding
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // set profile picture
        Glide.with(this)
            .load(R.drawable.profile)
            .circleCrop()
            .into(binding.imageProfile)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFavorite.setOnClickListener {
            val uri = Uri.parse(FAVORITE_URI)
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FAVORITE_URI = "makanankhasindonesia://favorite"
    }
}