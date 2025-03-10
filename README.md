# 📱 CustomCommaEditText

숫자 입력 시 콤마(,)를 자동으로 입력해줘야 하는 경우 사용할 수 있는 Custom EditText

---

## ✨ 주요 기능

- 숫자 입력 시 실시간으로 천 단위 콤마(,) 자동 삽입
- 숫자만 입력 가능 (`inputType="number"` 필수)
- 콤마 없는 원본 숫자 텍스트 반환 기능 제공
- 커서 위치 자동 보정 기능 포함

---

## 📌 컴포즈 활용 시 VisualTransformation 을 상속받아 간단히 구현 가능

### 
```kotlin
class DecimalMarkedNumberVisualTransformation(
    val prefix: String
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val defaultValue = "0"
        val formattedText =
            NumberUtil.convertNumberToDecimalMarkedString(
                text.text
                    .filter { "^[0-9]".toRegex().matches(it.toString()) }
                    .ifEmpty { defaultValue }
                    .toLong()
            )

        val offsetMapping = object : OffsetMapping {
            val initSize = prefix.length + defaultValue.length

            override fun originalToTransformed(offset: Int): Int {
                val commas = formattedText.count { it == ',' }
                return if (offset == 0) initSize else offset + commas + prefix.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                val commas = formattedText.count { it == ',' }
                return offset + commas + prefix.length
            }
        }

        return TransformedText(
            text = AnnotatedString("$prefix$formattedText"),
            offsetMapping = offsetMapping
        )
    }
}

object NumberUtil {
    fun convertNumberToDecimalMarkedString(number: Long): String {
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
    }
}

@Composable
fun NumberTextField(
	...
) {
    TextField(
        ...
        visualTransformation = DecimalMarkedNumberVisualTransformation("Some Prefix"),
        ...
    )
}
```
