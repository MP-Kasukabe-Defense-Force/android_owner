package com.example.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myapplication.SupabaseRepository
import com.example.myapplication.data.MenuItem
import kotlinx.coroutines.launch

@Composable
fun MenuItemFormScreen(
    categoryId: Int,
    existingItem: MenuItem? = null,
    onSaved: () -> Unit = {}
) {
    var name by remember { mutableStateOf(existingItem?.name ?: "") }
    var price by remember { mutableStateOf(existingItem?.price?.toString() ?: "") }
    var description by remember { mutableStateOf(existingItem?.description ?: "") }
    var allergyInfo by remember { mutableStateOf(existingItem?.allergy_info ?: "") }
    var sortOrder by remember { mutableStateOf(existingItem?.sort_order?.toString() ?: "0") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (existingItem == null) "메뉴 등록" else "메뉴 수정",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("메뉴 이름") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("가격") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("설명") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )

        OutlinedTextField(
            value = allergyInfo,
            onValueChange = { allergyInfo = it },
            label = { Text("알러지 정보") },
            placeholder = { Text("예: 밀, 우유, 땅콩") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sortOrder,
            onValueChange = { sortOrder = it },
            label = { Text("정렬 순서") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                val priceInt = price.toIntOrNull()
                if (name.isBlank() || priceInt == null) {
                    errorMessage = "이름과 가격을 올바르게 입력하세요."
                    return@Button
                }
                isLoading = true
                errorMessage = null
                scope.launch {
                    runCatching {
                        val item = MenuItem(
                            item_id = existingItem?.item_id,
                            category_id = categoryId,
                            name = name.trim(),
                            price = priceInt,
                            description = description.ifBlank { null },
                            allergy_info = allergyInfo.ifBlank { null },
                            sort_order = sortOrder.toIntOrNull() ?: 0
                        )
                        if (existingItem == null) {
                            SupabaseRepository.insertMenuItem(item)
                        } else {
                            SupabaseRepository.updateMenuItem(item)
                        }
                    }.onSuccess {
                        onSaved()
                    }.onFailure { e ->
                        errorMessage = e.message ?: "저장 실패"
                    }
                    isLoading = false
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "저장 중..." else "저장")
        }
    }
}
