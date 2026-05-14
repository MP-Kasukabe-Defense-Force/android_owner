package com.example.visionmenu.owner

import com.example.myapplication.SupabaseApp
import com.example.visionmenu.owner.data.MenuCategory
import com.example.visionmenu.owner.data.MenuItem
import com.example.visionmenu.owner.data.Store
import com.example.visionmenu.owner.data.StoreInfo
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseRepository {

    suspend fun insertStore(store: Store) {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("Store").insert(store)
        }
    }

    suspend fun getStoresByOwner(ownerId: Int): List<Store> {
        return withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("Store")
                .select {
                    filter { eq("owner_id", ownerId) }
                }
                .decodeList<Store>()
        }
    }

    suspend fun updateStore(store: Store) {
        withContext(Dispatchers.IO) {
            val storeId = requireNotNull(store.store_id) { "store_id가 필요합니다." }

            SupabaseApp.supabase.from("Store")
                .update(store) {
                    filter { eq("store_id", storeId) }
                }
        }
    }

    suspend fun deleteStore(storeId: Int) {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("Store")
                .delete {
                    filter { eq("store_id", storeId) }
                }
        }
    }

    suspend fun insertStoreInfo(storeInfo: StoreInfo) {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("StoreInfo").insert(storeInfo)
        }
    }

    suspend fun getStoreInfo(storeId: Int): StoreInfo? {
        return withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("StoreInfo")
                .select {
                    filter { eq("store_id", storeId) }
                }
                .decodeSingleOrNull<StoreInfo>()
        }
    }

    suspend fun updateStoreInfo(storeInfo: StoreInfo) {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("StoreInfo")
                .update(storeInfo) {
                    filter { eq("store_id", storeInfo.store_id) }
                }
        }
    }

    suspend fun insertMenuCategory(category: MenuCategory) {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("MenuCategory").insert(category)
        }
    }

    suspend fun getMenuCategories(storeId: Int): List<MenuCategory> {
        return withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("MenuCategory")
                .select {
                    filter { eq("store_id", storeId) }
                }
                .decodeList<MenuCategory>()
        }
    }

    suspend fun updateMenuCategory(category: MenuCategory) {
        withContext(Dispatchers.IO) {
            val categoryId = requireNotNull(category.category_id) { "category_id가 필요합니다." }

            SupabaseApp.supabase.from("MenuCategory")
                .update(category) {
                    filter { eq("category_id", categoryId) }
                }
        }
    }

    suspend fun deleteMenuCategory(categoryId: Int) {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("MenuCategory")
                .delete {
                    filter { eq("category_id", categoryId) }
                }
        }
    }

    suspend fun insertMenuItem(menuItem: MenuItem) {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("MenuItem").insert(menuItem)
        }
    }

    suspend fun getMenuItems(categoryId: Int): List<MenuItem> {
        return withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("MenuItem")
                .select {
                    filter { eq("category_id", categoryId) }
                }
                .decodeList<MenuItem>()
        }
    }

    suspend fun updateMenuItem(menuItem: MenuItem) {
        withContext(Dispatchers.IO) {
            val itemId = requireNotNull(menuItem.item_id) { "item_id가 필요합니다." }

            SupabaseApp.supabase.from("MenuItem")
                .update(menuItem) {
                    filter { eq("item_id", itemId) }
                }
        }
    }

    suspend fun deleteMenuItem(itemId: Int) {
        withContext(Dispatchers.IO) {
            SupabaseApp.supabase.from("MenuItem")
                .delete {
                    filter { eq("item_id", itemId) }
                }
        }
    }
}
