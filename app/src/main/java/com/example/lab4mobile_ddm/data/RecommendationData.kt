package com.example.lab4mobile_ddm.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.lab4mobile_ddm.R

data class Recommendation(
    val id: String,
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int,
    val category: String,
    @StringRes val addressResId: Int,
    val workingHours: String,
    val rating: Float,
    @DrawableRes val imageResId: Int? = null
)

enum class Category(val id: String, val titleResId: Int) {
    COFFEE_SHOPS("coffee", R.string.category_coffee),
    RESTAURANTS("restaurants", R.string.category_restaurants),
    FAMILY("family", R.string.category_family),
    PARKS("parks", R.string.category_parks),
    MALLS("malls", R.string.category_malls)
}

object RecommendationData {
    val recommendations = listOf(
        // ========== КОФЕЙНИ (5 штук) ==========
        Recommendation(
            id = "1",
            titleResId = R.string.rec_coffee_1,
            descriptionResId = R.string.desc_coffee_1,
            category = "coffee",
            addressResId = R.string.address_lenina_50,
            workingHours = "8:00 - 22:00",
            rating = 4.8f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "2",
            titleResId = R.string.rec_coffee_2,
            descriptionResId = R.string.desc_coffee_2,
            category = "coffee",
            addressResId = R.string.address_kompros_34,
            workingHours = "9:00 - 21:00",
            rating = 4.6f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "8",
            titleResId = R.string.rec_coffee_3,
            descriptionResId = R.string.desc_coffee_3,
            category = "coffee",
            addressResId = R.string.address_pushkina_15,
            workingHours = "9:00 - 23:00",
            rating = 4.7f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "9",
            titleResId = R.string.rec_coffee_4,
            descriptionResId = R.string.desc_coffee_4,
            category = "coffee",
            addressResId = R.string.address_gagarina_42,
            workingHours = "8:30 - 21:30",
            rating = 4.5f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "10",
            titleResId = R.string.rec_coffee_5,
            descriptionResId = R.string.desc_coffee_5,
            category = "coffee",
            addressResId = R.string.address_monastyrskaya_12,
            workingHours = "7:00 - 20:00",
            rating = 4.9f,
            imageResId = R.drawable.ic_placeholder
        ),

        // ========== РЕСТОРАНЫ (5 штук) ==========
        Recommendation(
            id = "3",
            titleResId = R.string.rec_rest_1,
            descriptionResId = R.string.desc_rest_1,
            category = "restaurants",
            addressResId = R.string.address_sibirskaya_15,
            workingHours = "12:00 - 00:00",
            rating = 4.7f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "4",
            titleResId = R.string.rec_rest_2,
            descriptionResId = R.string.desc_rest_2,
            category = "restaurants",
            addressResId = R.string.address_mira_88,
            workingHours = "11:00 - 23:00",
            rating = 4.5f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "11",
            titleResId = R.string.rec_rest_3,
            descriptionResId = R.string.desc_rest_3,
            category = "restaurants",
            addressResId = R.string.address_kuibysheva_25,
            workingHours = "11:00 - 22:00",
            rating = 4.6f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "12",
            titleResId = R.string.rec_rest_4,
            descriptionResId = R.string.desc_rest_4,
            category = "restaurants",
            addressResId = R.string.address_lenina_100,
            workingHours = "12:00 - 23:00",
            rating = 4.8f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "13",
            titleResId = R.string.rec_rest_5,
            descriptionResId = R.string.desc_rest_5,
            category = "restaurants",
            addressResId = R.string.address_kosmonavtov_55,
            workingHours = "12:00 - 00:00",
            rating = 4.9f,
            imageResId = R.drawable.ic_placeholder
        ),

        // ========== ДЛЯ ДЕТЕЙ (5 штук) ==========
        Recommendation(
            id = "5",
            titleResId = R.string.rec_family_1,
            descriptionResId = R.string.desc_family_1,
            category = "family",
            addressResId = R.string.address_krupskoy_23,
            workingHours = "10:00 - 20:00",
            rating = 4.9f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "14",
            titleResId = R.string.rec_family_2,
            descriptionResId = R.string.desc_family_2,
            category = "family",
            addressResId = R.string.address_mira_120,
            workingHours = "10:00 - 19:00",
            rating = 4.8f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "15",
            titleResId = R.string.rec_family_3,
            descriptionResId = R.string.desc_family_3,
            category = "family",
            addressResId = R.string.address_petropavlovskaya_45,
            workingHours = "11:00 - 21:00",
            rating = 4.6f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "16",
            titleResId = R.string.rec_family_4,
            descriptionResId = R.string.desc_family_4,
            category = "family",
            addressResId = R.string.address_lenina_60,
            workingHours = "10:00 - 22:00",
            rating = 4.7f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "17",
            titleResId = R.string.rec_family_5,
            descriptionResId = R.string.desc_family_5,
            category = "family",
            addressResId = R.string.address_sibirskaya_65,
            workingHours = "9:00 - 18:00",
            rating = 4.8f,
            imageResId = R.drawable.ic_placeholder
        ),

        // ========== ПАРКИ (5 штук) ==========
        Recommendation(
            id = "6",
            titleResId = R.string.rec_park_1,
            descriptionResId = R.string.desc_park_1,
            category = "parks",
            addressResId = R.string.address_petropavlovskaya_77,
            workingHours = "Круглосуточно",
            rating = 4.7f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "18",
            titleResId = R.string.rec_park_2,
            descriptionResId = R.string.desc_park_2,
            category = "parks",
            addressResId = R.string.address_gagarina_100,
            workingHours = "9:00 - 20:00",
            rating = 4.8f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "19",
            titleResId = R.string.rec_park_3,
            descriptionResId = R.string.desc_park_3,
            category = "parks",
            addressResId = R.string.address_mira_50,
            workingHours = "Круглосуточно",
            rating = 4.5f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "20",
            titleResId = R.string.rec_park_4,
            descriptionResId = R.string.desc_park_4,
            category = "parks",
            addressResId = R.string.address_naberezhnaya_1,
            workingHours = "Круглосуточно",
            rating = 4.9f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "21",
            titleResId = R.string.rec_park_5,
            descriptionResId = R.string.desc_park_5,
            category = "parks",
            addressResId = R.string.address_lesnaya_30,
            workingHours = "Круглосуточно",
            rating = 4.6f,
            imageResId = R.drawable.ic_placeholder
        ),

        // ========== ТОРГОВЫЕ ЦЕНТРЫ (5 штук) ==========
        Recommendation(
            id = "7",
            titleResId = R.string.rec_mall_1,
            descriptionResId = R.string.desc_mall_1,
            category = "malls",
            addressResId = R.string.address_kosmonavtov_111,
            workingHours = "10:00 - 22:00",
            rating = 4.6f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "22",
            titleResId = R.string.rec_mall_2,
            descriptionResId = R.string.desc_mall_2,
            category = "malls",
            addressResId = R.string.address_revolyutsii_1,
            workingHours = "10:00 - 21:00",
            rating = 4.7f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "23",
            titleResId = R.string.rec_mall_3,
            descriptionResId = R.string.desc_mall_3,
            category = "malls",
            addressResId = R.string.address_lenina_200,
            workingHours = "10:00 - 22:00",
            rating = 4.8f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "24",
            titleResId = R.string.rec_mall_4,
            descriptionResId = R.string.desc_mall_4,
            category = "malls",
            addressResId = R.string.address_pushkina_80,
            workingHours = "10:00 - 21:00",
            rating = 4.4f,
            imageResId = R.drawable.ic_placeholder
        ),
        Recommendation(
            id = "25",
            titleResId = R.string.rec_mall_5,
            descriptionResId = R.string.desc_mall_5,
            category = "malls",
            addressResId = R.string.address_kosmonavtov_45,
            workingHours = "10:00 - 22:00",
            rating = 4.5f,
            imageResId = R.drawable.ic_placeholder
        )
    )

    fun getByCategory(categoryId: String): List<Recommendation> {
        return recommendations.filter { it.category == categoryId }
    }

    fun getById(id: String): Recommendation? {
        return recommendations.find { it.id == id }
    }
}