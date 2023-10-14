package com.aryasurya.adoptpet.ui.register

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.aryasurya.adoptpet.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MyEditText : TextInputLayout {

    private lateinit var hideButtonImage: Drawable

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        // Inflate tampilan custom view dari layout XML
        inflate(context, R.layout.custom_text_input_view, this)

        // Inisialisasi elemen-elemen UI dan logika sesuai kebutuhan
        val textInputEditText = findViewById<TextInputEditText>(R.id.customTextInputEditText)
    }
}