package com.bms.rabbit.features.profile

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponse
import com.bms.rabbit.entities.InAppSubscription
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject


/**
BILLING_UNAVAILABLE — возвращается, если версия API не поддерживается для запрашиваемого типа.

DEVELOPER_ERROR — возвращается, когда неверные аргументы были отправлены в Billing API.

ERROR — общий ответ об ошибке, возвращаемый при возникновении ошибки во время выполнения работы.

FEATURE_NOT_SUPPORTED — возвращается, когда запрошенные действия не поддерживаются службами Google Play на данном устройстве.

ITEM_ALREADY_OWNED — возвращается, когда пользователь пытается приобрести товар, который у него уже есть.

ITEM_NOT_OWNED — возвращается, когда пользователь пытается использовать товар, которого у него нет.

ITEM_UNAVAILABLE — возвращается, когда пользователь пытается приобрести товар, недоступный для покупки.

OK — возвращается при успешном выполнении текущего запроса.

SERVICE_DISCONNECTED — возвращается, когда служба Google Play не была подключена.

SERVICE_UNAVAILABLE — возвращается при возникновении ошибки в связи с отсутствием подключения к сети.

USER_CANCELED — возвращается, когда пользователь отменяет запрос, который в настоящее время находится в обработке.
 */

class PaymentService(private val activity: Activity) {
    private val monthly = "com.bms.rabbit.subscribe.monthly"
    private val subject : PublishSubject<Purchase> = PublishSubject.create()
    private val purchasesUpdatedListener = PurchasesUpdatedListener { responseCode, purchases ->
        //ответ от покупки
        //{"orderId":"GPA.3354-7626-0483-74646","packageName":"com.bms.rabbit","productId":"com.bms.rabbit.subscribe.monthly","purchaseTime":1539715225041,"purchaseState":0,"purchaseToken":"ebpbbcbkckolncbkjomlnmhd.AO-J1OzytJ83vbHDl62LAA1ca3-q01E0vGHfX_5fsWsis5DhbbKiRjTf46q0mc7xw6v69xUPCDvHJbaocB1gDkglEh4DArp0vvhBUA0Uenn4H05egP1uykUWyCwebBfXgLXUOjvsAOrx","autoRenewing":true}
        if (responseCode == BillingResponse.OK && purchases != null) {
            for (purchase in purchases) {
                subject.onNext(purchase)
                Log.d("Payment",purchase.sku)
//                handlePurchase(purchase)
            }
        } else if (responseCode == BillingResponse.USER_CANCELED) {
            if(subject.hasObservers()) {
                Log.d("Payment","Operation failed")
                subject.onError(Throwable("Operation failed"))
            }
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            if(subject.hasObservers()) {
                Log.d("Payment","Operation failed")
                subject.onError(Throwable("Operation failed"))
            }
            // Handle any other error codes.
        }
    }

    private var connected = false
    private val billingClient: BillingClient  by lazy {
        BillingClient
                .newBuilder(activity)
                .setListener(purchasesUpdatedListener)
                .build()
    }

    init {
        billingClient.isReady
//        initBilling()
    }

    fun setupConnection():Single<Boolean> {
        return Single.create { emitter ->
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(responseCode: Int) {
                    connected = true
                    emitter.onSuccess(true)
                }

                override fun onBillingServiceDisconnected() {
                    connected = false
                    emitter.onError(Throwable("Operation failed"))
                }
            })
        }
    }

    fun requestSubs(): Single<List<SkuDetails>> {
        val skuList = arrayListOf(monthly)
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)

        return Single.create { emitter ->
            billingClient.querySkuDetailsAsync(params.build()) { responseCode, skuDetailsList ->
                if (responseCode == BillingResponse.OK && skuDetailsList.size > 0) {
                    emitter.onSuccess(skuDetailsList)
                } else {
                    emitter.onError(Throwable("Operation failed"))
                }
            }
        }
    }

    fun buySku(skuDetails: SkuDetails) {
        val mParams = BillingFlowParams.newBuilder().setSku(skuDetails.sku).setType(BillingClient.SkuType.SUBS).build()
        billingClient.launchBillingFlow(activity, mParams)
    }

    fun purchaseUpdate():Observable<Purchase> = subject
}