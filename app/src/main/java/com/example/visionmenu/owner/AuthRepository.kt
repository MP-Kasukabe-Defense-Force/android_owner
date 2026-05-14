package com.example.visionmenu.owner

import com.example.myapplication.SupabaseApp
import com.example.visionmenu.owner.data.Owner
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AuthRepository {

    suspend fun signUp(email: String, password: String, name: String) {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            val authId = SupabaseApp.supabase.auth.currentUserOrNull()?.id
                ?: throw IllegalStateException("회원가입 후 auth_id를 가져오지 못했습니다. Supabase 이메일 인증 설정을 확인하세요.")

            SupabaseApp.supabase.from("Owner").insert(
                Owner(
                    auth_id = authId,
                    name = name
                )
            )
        }
    }

    suspend fun signIn(email: String, password: String) {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    suspend fun signOut() {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.auth.signOut()
        }
    }

    suspend fun getCurrentOwnerId(): Int? {
        return withContext(Dispatchers.IO) {
            val authId = SupabaseApp.supabase.auth.currentUserOrNull()?.id
                ?: return@withContext null

            SupabaseApp.supabase.from("Owner")
                .select {
                    filter { eq("auth_id", authId) }
                }
                .decodeSingleOrNull<Owner>()
                ?.owner_id
        }
    }

    fun isLoggedIn(): Boolean {
        return SupabaseApp.supabase.auth.currentUserOrNull() != null
    }
}
