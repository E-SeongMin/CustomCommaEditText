package com.geek.customcommaedittext

import android.content.Context
import android.icu.text.DecimalFormat
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CustomCommaEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var isEditing = false

    private val commaTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (isEditing) return

            isEditing = true

            val originalText = s?.toString() ?: ""
            val cleanText = originalText.replace(",", "")

            if (cleanText.isNotEmpty()) {
                try {
                    val formattedText = formatWithComma(cleanText)
                    val cursorPosition = calculateCursorPosition(originalText, formattedText, selectionStart)
                    setText(formattedText)
                    setSelection(cursorPosition.coerceIn(0, formattedText.length))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            isEditing = false
        }
    }

    init {
        addTextChangedListener(commaTextWatcher)
    }

    fun getRawText(): String = text?.toString()?.replace(",", "").orEmpty()

    fun setClearFocus() {
        clearFocus()
    }

    private fun formatWithComma(input: String) : String {
        val decimalFormat = DecimalFormat("#,###")
        return decimalFormat.format(input.toLong())
    }

    private fun calculateCursorPosition(original: String, formatted: String, originalCursor: Int) : Int {
        var offset = 0
        val commaCountOriginal = original.take(originalCursor).count { it == ',' }
        var commaCountFormatted = formatted.take(originalCursor + offset).count { it == ',' }

        while (commaCountOriginal != commaCountFormatted && originalCursor + offset < formatted.length) {
            offset++
            commaCountFormatted = formatted.take(originalCursor + offset).count { it == ',' }
        }

        return originalCursor + offset
    }
}