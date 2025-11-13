package com.example.infohub_telas.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

// CPF: ###.###.###-##
class CpfVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(11)
        val out = StringBuilder()
        for (i in digits.indices) {
            out.append(digits[i])
            if (i == 2 || i == 5) out.append('.')
            if (i == 8) out.append('-')
        }
        val transformed = AnnotatedString(out.toString())
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val add = when {
                    offset <= 3 -> 0
                    offset <= 6 -> 1
                    offset <= 9 -> 2
                    else -> 3
                }
                return (offset + add).coerceAtMost(transformed.text.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                var o = offset
                if (o > 3) o -= 1
                if (o > 6) o -= 1
                if (o > 10) o -= 1
                return o.coerceAtMost(digits.length)
            }
        }
        return TransformedText(transformed, offsetMapping)
    }
}

// CNPJ: ##.###.###/####-##
class CnpjVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(14)
        val out = StringBuilder()
        for (i in digits.indices) {
            out.append(digits[i])
            when (i) {
                1, 4 -> out.append('.')
                7 -> out.append('/')
                11 -> out.append('-')
            }
        }
        val transformed = AnnotatedString(out.toString())
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val add = when {
                    offset <= 2 -> 0
                    offset <= 5 -> 1
                    offset <= 8 -> 2
                    offset <= 12 -> 3
                    else -> 4
                }
                return (offset + add).coerceAtMost(transformed.text.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                var o = offset
                if (o > 2) o -= 1
                if (o > 6) o -= 1
                if (o > 10) o -= 1
                if (o > 15) o -= 1
                return o.coerceAtMost(digits.length)
            }
        }
        return TransformedText(transformed, offsetMapping)
    }
}

// Telefone BR: (##) ####-#### ou (##) #####-####
class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(11)
        val len = digits.length
        val out = StringBuilder()
        for (i in 0 until len) {
            val c = digits[i]
            when (i) {
                0 -> out.append('(').append(c)
                1 -> out.append(c).append(") ")
                else -> {
                    val breakIndex = if (len > 10) 7 else 6
                    out.append(c)
                    if (i == breakIndex) out.append('-')
                }
            }
        }
        val transformed = AnnotatedString(out.toString())
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return 0
                var add = 1 // '('
                if (offset >= 2) add += 2 // ") "
                val breakIndex = if (len > 10) 7 else 6
                if (offset > breakIndex) add += 1 // '-'
                return (offset + add).coerceAtMost(transformed.text.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                var o = offset
                if (o > 0) o -= 1 // '('
                if (o > 3) o -= 2 // ") "
                val breakIndex = if (len > 10) 7 else 6
                // Hyphen appears after index breakIndex in original, but in transformed it's shifted by 1
                if (o > breakIndex + 3) o -= 1
                return o.coerceIn(0, len)
            }
        }
        return TransformedText(transformed, offsetMapping)
    }
}

