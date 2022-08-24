package com.mondialrelay.chucknorrisapp.application.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mondialrelay.chucknorrisapp.R
import com.mondialrelay.chucknorrisapp.databinding.FragmentLoginBinding
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by inject()
    private lateinit var viewBinding: FragmentLoginBinding

    // region -------- INITIALISATION

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserversListeners()
    }

    // endregion

    // region -------- UI

    private fun initObserversListeners() {
        with(viewModel) {
            liveUser.observe(viewLifecycleOwner) { (user, _) ->
                navValidate()
            }
        }
        with(viewBinding) {
            button2.setOnClickListener {
                viewModel.actionLogin.invoke(editTextTextPersonName.text.toString(), editTextTextPassword.text.toString())
            }
        }
    }

    private fun navValidate() {
        findNavController().navigate(R.id.action_loginFragment_to_swipeAndRecyclerFragment)
    }

}