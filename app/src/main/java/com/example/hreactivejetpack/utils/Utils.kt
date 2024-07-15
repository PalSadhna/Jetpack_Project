package com.example.hreactivejetpack.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.hreactivejetpack.R

object Utils {
    @Composable
    public fun CircularProgressBar(
        percentage: Float,
        number: Int,
        fontSize: TextUnit = 15.sp,
        radius: Dp = 50.dp,
        color: Color = colorResource(id = R.color.light_green_color),
        strokeWidth: Dp = 10.dp,
        animDuration: Int = 1000,
        animDelay: Int = 0,

        ){
        var animationPlayed by remember {
            mutableStateOf(false)
        }

        val curPercentage = animateFloatAsState(targetValue = if (animationPlayed) percentage else 0f,
            animationSpec = tween(durationMillis = animDuration, delayMillis = animDelay),
            label = ""
        )
        
        LaunchedEffect(key1 = true){
            animationPlayed  = true
        }

        Box(contentAlignment = Alignment.Center ,modifier = Modifier.size(radius * 2f)){
            Canvas(modifier = Modifier.size(radius * 2f)){
                drawArc(color = Color.Red, -90f, 360 * 100f,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                drawArc(color = color, -90f, 360 * curPercentage.value,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = (curPercentage.value * number).toInt().toString(),
                    color = Color.White,
                    fontSize = fontSize,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Present",
                    color = Color.White,
                    fontSize = fontSize,
                )
            }
        }
    }

    @Composable
    fun FragmentContainer(
        modifier: Modifier = Modifier,
        fragmentManager: FragmentManager,
        commit: FragmentTransaction.(containerId: Int) -> Unit
    ) {
        val containerId by rememberSaveable { mutableStateOf(View.generateViewId()) }
        var initialized by rememberSaveable { mutableStateOf(false) }
        AndroidView(
            modifier = modifier,
            factory = { context ->
                FragmentContainerView(context)
                    .apply { id = containerId }
            },
            update = { view ->
                if (!initialized) {
                    fragmentManager.commit { commit(view.id) }
                    initialized = true
                } else {
                    fragmentManager.onContainerAvailable(view)
                }
            }
        )
    }

    /** Access to package-private method in FragmentManager through reflection */
    private fun FragmentManager.onContainerAvailable(view: FragmentContainerView) {
        val method = FragmentManager::class.java.getDeclaredMethod(
            "onContainerAvailable",
            FragmentContainerView::class.java
        )
        method.isAccessible = true
        method.invoke(this, view)
    }

    fun showToast(context: Context,msg: String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
}