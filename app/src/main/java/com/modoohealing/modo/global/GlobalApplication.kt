package com.modoohealing.modo.global

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this,"a0a8a5bcf51e0b266321bda0f1c0bcbf")
    }
}