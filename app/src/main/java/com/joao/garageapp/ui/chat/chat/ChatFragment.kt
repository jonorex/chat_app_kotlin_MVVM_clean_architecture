package com.joao.garageapp.ui.chat.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.joao.garageapp.databinding.ChatFragmentBinding
import com.joao.garageapp.domain.model.ChatMessage
import com.joao.garageapp.domain.model.User
import com.joao.garageapp.ui.chat.conversations.ConversationViewModel
import com.joao.garageapp.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {


    private val viewModel: ChatViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val converseViewModel: ConversationViewModel by viewModels()

    private var _binding: ChatFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = ChatAdapter()
    private val args by navArgs<ChatFragmentArgs>()
    private lateinit var userR: User
    private lateinit var userS: User
    private var messagesList = mutableListOf<ChatMessage>()
    private var messagesList2 = mutableListOf<ChatMessage>()
    private var count: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVMUser()
        observeVMEvents()
        userViewModel.getUserLogged()
        init()
        setListeners()


    }



    private fun setListeners() {
        binding.layoutSend.setOnClickListener{
            val txt = binding.inputMessage.text.toString()
            viewModel.sendMessage(userS.userId, userR.userId, txt)
            binding.inputMessage.text.clear()
            //converseViewModel.updateConversation(userS, userR, it.first())
        }

        binding.chatRecyclerView.adapter = adapter
        binding.chatRecyclerView.visibility = View.VISIBLE

        binding.imageInfo.setOnClickListener{

        }
    }

    private fun init() {
        if(args.receiverUser != null){
            userR = args.receiverUser!!
        }
    }


    private fun setAdapter(it: List<ChatMessage>?) {
        if (it?.size!! > 0 ) {
            Log.i("observerMessage", "entrou")
            count += it.size
            if (it.size > 1 && messagesList.size == 0) {
                Log.i("observerMessage", "case 1")
                messagesList.addAll(it)
                adapter.setData(it, userS.userId)
            } else if (it.size == 1 && messagesList.size == 0 ) {
                Log.i("observerMessage", "case 2")
                messagesList.addAll(it)
                adapter.setData(it, userS.userId)
            } else if (messagesList.size > 1 && it.size > 1) {
                Log.i("observerMessage", "case 2")
                adapter.addMessages(it as MutableList<ChatMessage>)
                messagesList.addAll(it)
            } else {
                Log.i("observerMessage", "case 3")
                adapter.addMessage(it.first(), messagesList.size)
                messagesList.add(it.first())
                binding.chatRecyclerView.smoothScrollToPosition(messagesList.size - 1)
            }
        }
    }

    private fun observeVMEvents() {

        viewModel.listMessages.observe(viewLifecycleOwner) {
           // Log.i("ListMessages","size ${it?.size}")
            setAdapter(it)
        }

        viewModel.messageSend.observe(viewLifecycleOwner) {
            converseViewModel.updateConversation(userS, userR, it)
        }

    }

    private fun observeVMUser() {
        userViewModel.userLogged.observe(viewLifecycleOwner) {
            userS = it
            if(args.receiverUser != null){
                userR = args.receiverUser!!
                viewModel.listMessages(userS.userId, userR.userId)
                viewModel.listMessages2(userS.userId, userR.userId)
                Log.i("ListMessages","userSId = ${userS.userId}  userRId = ${userR.userId}")
            }

        }
    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        Log.d("clickTag", "Click happened")
        currentDestination?.getAction(direction.actionId)?.run {
            Log.d("clickTag", "Click Propagated")
            navigate(direction)
        }
    }


}