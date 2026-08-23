package com.hdfilmcehennemi

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin
import android.content.Context

@CloudstreamPlugin
class HdFilmCehennemiPlugin : Plugin() {
    override fun load(context: Context) {
        // Sağlayıcımızı (Provider) sisteme kaydediyoruz
        registerMainAPI(HdFilmCehennemiProvider())
    }
}
