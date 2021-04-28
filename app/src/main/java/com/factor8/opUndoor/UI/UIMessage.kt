package com.factor8.opUndoor.UI

data class UIMessage(
        val message: String,
        val uiMessageType: UIMessageType,
        val switchVal: String = "-1"
)

sealed class UIMessageType{

    class Toast: UIMessageType()

    class Dialog: UIMessageType()

    class AreYouSureDialog(
            val callback: AreYouSureCallback
    ): UIMessageType()

    class MakeAccountPublic(
            val callback: MakeAccountPublicCallback
    ): UIMessageType()

    class None: UIMessageType()

}