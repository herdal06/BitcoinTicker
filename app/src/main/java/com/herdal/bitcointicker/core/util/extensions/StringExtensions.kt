package com.herdal.bitcointicker.core.util.extensions

import androidx.core.text.HtmlCompat

fun String?.parseHtml(): String {
    if (this == null) return ""
    return HtmlCompat.fromHtml(
        this,
        HtmlCompat.FROM_HTML_MODE_COMPACT
    ).toString()
}