package com.example.hreactivejetpack

import android.graphics.drawable.VectorDrawable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val description: String,
    val navId: Int,
    val image: ImageVector
)
