# 📱 CustomCommaEditText

숫자 입력 시 콤마(,)를 자동으로 입력해줘야 하는 경우 사용할 수 있는 Custom EditText

---

## ✨ 주요 기능

- 숫자 입력 시 실시간으로 천 단위 콤마(,) 자동 삽입
- 숫자만 입력 가능 (`inputType="number"` 필수)
- 콤마 없는 원본 숫자 텍스트 반환 기능 제공
- 커서 위치 자동 보정 기능 포함

---

## 📌 사용 방법

### XML 예시
```xml
<com.geek.customcommaedittext.CustomCommaEditText
    android:id="@+id/commaEditText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:inputType="number"
    android:hint="숫자를 입력하세요" />

----------------------------------------------------------

Compose에서 VisualTransformation 활용한 콤마 찍기 추가 예정
