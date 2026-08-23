package com.hdfilmcehennemi

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.Plugin
import android.content.Context

@CloudstreamPlugin
class HdFilmCehennemiPlugin : Plugin() {
    override fun load(context: Context) {
        registerMainAPI(HdFilmCehennemiProvider())
    }
}
