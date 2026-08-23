package com.hdfilmcehennemi

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin

@CloudstreamPlugin
class HdFilmCehennemiProvider : MainAPI() {
    override var mainUrl = "https://hdfilmcehennemi.com"
    override var name = "HdFilmCehennemi"
    override val supportedTypes = setOf(TvType.Movie)
    override var lang = "tr"
    override val hasMainPage = true

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val document = app.get(mainUrl).document
        val home = document.select("div.film-box").mapNotNull { element ->
            val title = element.select("h3").text()
            val href = element.select("a").attr("href")
            val posterUrl = element.select("img").attr("data-src").ifEmpty { element.select("img").attr("src") }
            
            newMovieSearchResponse(title, href, TvType.Movie) {
                this.posterUrl = posterUrl
            }
        }
        return newHomePageResponse(request.name, home)
    }

    override suspend fun load(url: String): LoadResponse {
        val document = app.get(url).document
        val title = document.select("h1").text()
        val poster = document.select("div.poster img").attr("src")
        val description = document.select("div.icerik-detay").text()

        return newMovieLoadResponse(title, url, TvType.Movie, url) {
            this.posterUrl = poster
            this.plot = description
        }
    }

    override suspend fun loadLinks(data: String, isCasting: Boolean, subtitleCallback: (SubtitleFile) -> Unit, callback: (ExtractorLink) -> Unit): Boolean {
        val document = app.get(data).document
        val videoUrl = document.select("iframe").attr("src")
        
        loadExtractor(videoUrl, data, subtitleCallback, callback)
        return true
    }
}
