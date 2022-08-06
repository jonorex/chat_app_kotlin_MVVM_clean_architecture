package com.joao.garageapp.ui.user.sign_up

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.joao.garageapp.databinding.SignUpFragmentBinding
import com.joao.garageapp.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: SignUpFragmentBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null


    private val viewModel: UserViewModel by viewModels()

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageUri = uri
            binding.imageProfile.setImageURI(uri)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVMEvents()
        setListeners()
    }

    private fun observeVMEvents(){
        viewModel.emailFieldErrorResId.observe(viewLifecycleOwner) { stringResId ->
            if(stringResId!=null)
                showToast(stringResId)
        }
        viewModel.nameFieldErrorResId.observe(viewLifecycleOwner) { stringResId ->
            showToast(stringResId)
        }

        viewModel.imageUriErrorResId.observe(viewLifecycleOwner) { stringResId ->
            showToast(stringResId)
        }

        viewModel.passwordFieldErrorResId.observe(viewLifecycleOwner){ stringResId ->
            showToast(stringResId)
        }

        viewModel.userCreated.observe(viewLifecycleOwner) {
            findNavController().safeNavigate(SignUpFragmentDirections.actionSignUpFragmentToConversesFragment(it))
        }
    }

    private fun setListeners(){
        binding.imageProfile.setOnClickListener {
            chooseImage()
        }

        binding.buttonSignUp.setOnClickListener {
            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val confirmPassword = binding.inputConfirmPassword.text.toString()

            viewModel.createUser(name, imageUri, email, password, confirmPassword)
        }
    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        Log.d("clickTag", "Click happened")
        currentDestination?.getAction(direction.actionId)?.run {
            Log.d("clickTag", "Click Propagated")
            navigate(direction)
        }
    }

    private fun showToast(stringResId: Int?) {
        if(stringResId != null)
            Toast.makeText(context, getString(stringResId), Toast.LENGTH_SHORT).show()
    }


    private fun chooseImage() {
        getContent.launch("image/*")
    }


}