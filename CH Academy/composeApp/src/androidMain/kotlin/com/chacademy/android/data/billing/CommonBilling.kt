package com.chacademy.android.data.billing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class CommonBilling {
    var billingState: BillingState = BillingState.NotPurchased()
        private set(value) {
            field = value
            updateState(value)
        }
    private val _billingStateFlow: MutableStateFlow<BillingState> =
        MutableStateFlow(BillingState.NotPurchased())
    val billingStateFlow: StateFlow<BillingState> =
        _billingStateFlow.asStateFlow()

    private val _billingCommandFlow: MutableSharedFlow<BillingCommand> =
        MutableSharedFlow()
    private val _billingMessageFlow =
        MutableStateFlow("")

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    sealed class BillingState {
        data class NotPurchased(val lastBillingMessage: String? = null) : BillingState()
        data object Pending : BillingState()
        data object Purchased : BillingState()
        data class Error(val errorMessage: String) : BillingState()
        data object Disabled : BillingState()
    }

    sealed class BillingCommand {
        data class Purchase(val productId: String) : BillingCommand()
        data class Consume(val productId: String) : BillingCommand()
    }

    fun purchaseProCommand() {
        coroutineScope.launch {
            _billingCommandFlow.emit(
                BillingCommand.Purchase(kProProductId)
            )
        }
    }

    // For testing purposes in this particular app. Release app doesn't need this.
    fun consumeProCommand() {
        coroutineScope.launch {
            _billingCommandFlow.emit(
                BillingCommand.Consume(kProProductId)
            )
        }
    }

    fun updateState(billingState: BillingState) {
        coroutineScope.launch {
            _billingStateFlow.emit(billingState)
        }
    }

    fun updateMessage(message: String) {
        coroutineScope.launch {
            _billingMessageFlow.emit(message)
        }
    }

    // Receive commands from the shared code
    fun commandFlow(): CommonFlow<BillingCommand> {
        return _billingCommandFlow.asCommonFlow()
    }

    // Receive billing state updates from the platform specific code
    fun billingStateFlow(): CommonFlow<BillingState> {
        return _billingStateFlow.asCommonFlow()
    }

    // Receive billing messages from the platform specific code
    fun billingMessageFlow(): CommonFlow<String> {
        return _billingMessageFlow.asCommonFlow()
    }

    companion object {
        const val kProProductId = "pro"
    }
}