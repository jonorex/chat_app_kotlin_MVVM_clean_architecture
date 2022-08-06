package com.joao.garageapp.ui.user.sign_in


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.joao.garageapp.R
import com.joao.garageapp.databinding.SignInFragmentBinding
import com.joao.garageapp.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignInFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }
    private fun getUserLogged() {
        viewModel.getUserLogged()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeVMEvents()
        getUserLogged()
        setListeners()
    }

    private fun observeVMEvents() {
        viewModel.emailFieldErrorResId.observe(viewLifecycleOwner) { stringResId ->
            if (stringResId != null )
                showToast(stringResId)
        }
        viewModel.passwordFieldErrorResId.observe(viewLifecycleOwner){ stringResId ->
            showToast(stringResId)
        }
        viewModel.userLogged.observe(viewLifecycleOwner){ user ->
            if (user != null && user.userId != "") {
                findNavController().run {
                    safeNavigate(SignInFragmentDirections.actionSignInFragmentToConversesFragment(user))
                }
            }
        }
    }

//    private fun logUser(user: User) {
//        Log.i("user nome", user.name)
//        Log.i("user email", user.email)
//        Log.i("user image", user.image)
//    }

    private fun setListeners(){
        binding.buttonSignIn.setOnClickListener{
            //it.visibility = View.GONE
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            viewModel.loginUser(email, password)
        }
        binding.textCreateNewAccount.setOnClickListener{
            findNavController().safeNavigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }
    }

    private fun showToast(stringResId: Int?) {
        if(stringResId != null)
            Toast.makeText(context, getString(stringResId), Toast.LENGTH_SHORT).show()
    }



    private fun setVisibleFalse(view: View) {
        view.visibility = View.GONE
    }


    private fun NavController.safeNavigate(direction: NavDirections) {
        Log.d("clickTag", "Click happened")
        currentDestination?.getAction(direction.actionId)?.run {
            Log.d("clickTag", "Click Propagated")
            navigate(direction)
        }
    }


}