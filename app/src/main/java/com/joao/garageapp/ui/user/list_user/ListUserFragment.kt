package com.joao.garageapp.ui.user.list_user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.joao.garageapp.databinding.ListUserFragmentBinding
import com.joao.garageapp.domain.model.User
import com.joao.garageapp.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListUserFragment : Fragment() {

    private var _binding: ListUserFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()
    private  val adapter = ListUserAdapter{
        openChat(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListUserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().safeNavigate(ListUserFragmentDirections.actionListUserFragmentToConversesFragment(null))
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.usersRecyclerView.adapter = adapter
        observeVMEvents()
        listUsers()
    }

    private fun listUsers() {
        viewModel.listUsers()
    }

    private fun observeVMEvents() {
        viewModel.listUser.observe(viewLifecycleOwner){ users ->
            adapter.setUserList(users)
        }
    }

    private fun openChat(user: User) {
        val action: NavDirections = ListUserFragmentDirections.actionListUserFragmentToChatFragment(user, null)
        findNavController().navigate(action)
    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        Log.d("clickTag", "Click happened")
        currentDestination?.getAction(direction.actionId)?.run {
            Log.d("clickTag", "Click Propagated")
            navigate(direction)
        }
    }

}