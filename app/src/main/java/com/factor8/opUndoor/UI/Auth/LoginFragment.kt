package com.factor8.opUndoor.UI.Auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.factor8.opUndoor.Models.AuthToken
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Auth.State.AuthStateEvent
import com.factor8.opUndoor.UI.Auth.State.LoginFields
import com.factor8.opUndoor.Util.ApiEmptyResponse
import com.factor8.opUndoor.Util.ApiErrorResponse
import com.factor8.opUndoor.Util.ApiSuccessResponse
import com.factor8.opUndoor.Util.GenericApiResponse
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseAuthFragment() {




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

        button_signin.setOnClickListener {
                login()
        }

        focusable_view.requestFocus()
        subscribeObservers()
    }

    private fun login(){
        viewModel.setStateEvent(
                AuthStateEvent.LoginAttemptEvent(editText_username.text.toString(), editText_password.getText().toString())
        )
    }

    private fun navRegistration() {
        findNavController().navigate(R.id.action_loginFragment_to_registerP1Fragment)
    }


    private fun navForgetPassword() {
        //findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
    }

    fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { AuthViewState ->
            AuthViewState.loginFields?.let { loginFields ->
                {
                    loginFields.login_email?.let { login_email ->
                        {
                            editText_username.setText(login_email)
                        }
                    }
                    loginFields.login_password?.let { login_password ->
                            {
                                editText_password.setText(login_password)
                            }

                        }
                    }
                }
            })
        }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoginFields( LoginFields(editText_username.text.toString(), editText_password.text.toString()))
    }

    }