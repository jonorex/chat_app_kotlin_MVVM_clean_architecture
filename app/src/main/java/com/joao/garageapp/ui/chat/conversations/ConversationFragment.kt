package com.joao.garageapp.ui.chat.conversations


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.joao.garageapp.databinding.ConversesFragmentBinding
import com.joao.garageapp.domain.model.User
import com.joao.garageapp.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ConversationFragment : Fragment() {

    private var _binding: ConversesFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ConversationViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    private var adapter: ConversationAdapter? = null
    private lateinit var userS: User

    private var count: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConversesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            count = 0
            adapter = ConversationAdapter()
            observeVMEvents()
            userViewModel.getUserLogged()

        setListeners()
    }

    private fun listConversations(user: User) {
        viewModel.listSendConverses(user.userId)
        viewModel.listReceivesConverses(user.userId)
    }

    private fun observeVMEvents() {
        userViewModel.userLogged.observe(viewLifecycleOwner) { user ->
            if (user != null && user.userId != "") {
                userS = user
                binding.textName.text = user.name
                Glide.with(requireContext()).load(user.image).into(binding.imageProfile)
                listConversations(userS)
            } else {
                // findNavController().popBackStack()
                findNavController().safeNavigate(ConversationFragmentDirections.actionConversesFragmentToSignInFragment())
            }

        }
        viewModel.listConversations.observe(viewLifecycleOwner) {
            if (it?.size!! > 0) {
                if (it.size > 1 && count == 0) {
                    count += it.size
                    adapter!!.setData(it, userS.userId)
                } else if (it.size == 1 && count == 0) {
                    count += it.size
                    adapter!!.setData(it, userS.userId)
                } else if (count > 1 && it.size > 1) {
                    count += it.size
                    adapter!!.addConversations(it)
                } else {
                    if (!adapter!!.tryUpdate(it.first())){
                        adapter!!.addConversation(it.first())
                        count += it.size
                        binding.conversionsRecyclerView.smoothScrollToPosition(count - 1)
                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.imageSignOut.setOnClickListener {
            findNavController().safeNavigate(ConversationFragmentDirections.actionConversesFragmentToSignInFragment())
            viewModel.signOut()
        }
        binding.fabNewChat.setOnClickListener {
            findNavController().safeNavigate(ConversationFragmentDirections.actionConversesFragmentToListUserFragment())
        }

        binding.conversionsRecyclerView.adapter = adapter
        binding.conversionsRecyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE


    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction)
        }
    }

}