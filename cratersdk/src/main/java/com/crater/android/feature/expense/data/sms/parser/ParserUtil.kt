package com.crater.android.feature.expense.data.sms.parser

object ParserUtil {

    fun getMerchantName(text: String): String? {
        val pattern1 = "(\\d+)([\\w\\s]+)(\\b[Tt]ot\\b)".toRegex()
        val pattern2 = "(\\b[Tt]hrough\\b)([\\w\\s]+)(\\b[Oo]n\\b)".toRegex()
        val pattern3 = "(\\b[Ff]rom\\b)([\\w\\s]+)(\\b[Oo]n\\b)".toRegex()
        val pattern4 = "(\\b[Tt]o\\b)([\\w\\s]+)(\\b[Oo]n\\b)".toRegex()
        val pattern5 = "(\\b[Ff]rom\\b)([\\w\\s]+)(\\b[Ii]n\\b)".toRegex()
        val pattern6 = "(\\b[Aa]t\\b)([\\w\\s]+)(\\b[Oo]n\\b)".toRegex()

        var name: String? = null

        when {
            text.contains(pattern1) -> {
                val matcher = pattern1.toPattern().matcher(text)
                if (matcher.find())
                    name = matcher.group(2)?.trim()
            }
            text.contains(pattern2) -> {
                val matcher = pattern2.toPattern().matcher(text)
                if (matcher.find())
                    name = matcher.group(2)?.trim()
            }
            text.contains(pattern3) -> {
                val matcher = pattern3.toPattern().matcher(text)
                if (matcher.find())
                    name = matcher.group(2)?.trim()
            }
            text.contains(pattern4) -> {
                val matcher = pattern4.toPattern().matcher(text)
                if (matcher.find())
                    name = matcher.group(2)?.trim()
            }
            text.contains(pattern5) -> {
                val matcher = pattern5.toPattern().matcher(text)
                if (matcher.find())
                    name = matcher.group(2)?.trim()
            }
            text.contains(pattern6) -> {
                val matcher = pattern6.toPattern().matcher(text)
                if (matcher.find())
                    name = matcher.group(2)?.trim()
            }
            text.contains("onecard", true) -> {
                name = "OneCard"
            }
        }
        /*if (text.contains(pattern4)) {
            val matcher = pattern4.toPattern().matcher(text)
            if (matcher.find())
                name = matcher.group(2)?.trim()
        } else if (text.contains(pattern5)) {
            val matcher = pattern5.toPattern().matcher(text)
            if (matcher.find())
                name = matcher.group(2)?.trim()
        }*/
        name = removeSpecialChar(name)
        return name
    }

    fun removeSpecialChar(text: String?): String? {
        if (!text.isNullOrEmpty()) {
            val symbols = "^[0-9+,.]|[0-9+,.]$".toRegex()
            if (text.contains(symbols))
                return text.replace(symbols, "").smartCapitalize()
        }
        return text
    }

    fun getMerchantUpiId(text: String): String? {
        val matcher = "(to\\s)(VPA\\s)?([\\w.-]+@[\\w.-]+)".toPattern().matcher(text)
        return if (matcher.find()) {
            matcher.group(3)
        } else null
    }

    fun getRefNo(text: String): String? {
        val refNoPattern = "\\d{12}".toRegex()
        val refMatcher = refNoPattern.toPattern().matcher(text)
        return if (refMatcher.find())
            refMatcher.group(0)
        else null
    }

    fun isUPITransfer(text: String): Boolean {
        return text.lowercase().contains("\\bupi\\b".toRegex())
    }
}

fun String.smartCapitalize(): String {
    val parts = lowercase().split(" ")
    return parts.joinToString(separator = " ") { string ->
        string.replaceFirstChar { it.uppercaseChar() }
    }
}