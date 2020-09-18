package com.factor8.opUndoor.UI.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.factor8.opUndoor.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_signup.setOnClickListener {
            navRegistration()
        }

        textView_forgetPass.setOnClickListener {
            navForgetPassword()
        }

        focusable_view.requestFocus()
    }

    private fun navRegistration() {
        findNavController().navigate(R.id.action_loginFragment_to_registerP1Fragment)
    }


    private fun navForgetPassword() {
        findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
    }


}