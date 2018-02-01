package com.klibre.services

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle


/**
 * Created by Miguel on 31/1/2018.
 */

class BookDownloadService {
    private var activity: Activity

    constructor(params: HashMap<String, Any>) : super() {
        activity = params.get("handleActivity") as Activity
    }

    fun execute(title: String, path: String) {
        val uris = Uri.parse(path)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        activity.startActivity(intents)
    }
}