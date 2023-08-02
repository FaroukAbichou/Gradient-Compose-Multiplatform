package com.example.composegradient.rainbowbox

import android.opengl.GLES20
import android.os.SystemClock
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

const val FLOAT_SIZE = 4

fun createFloatBuffer(values: FloatArray): FloatBuffer {
    val size = values.size * FLOAT_SIZE
    val buffer = ByteBuffer
        .allocateDirect(size)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
    buffer.put(values, 0, values.size)
    buffer.position(0)
    return buffer
}


const val DASH_LENGTH = 2.0f

// Helper function for the animation
fun timeOffset(dashCount: Float, scale: Float): Float {
    // Why 800? It looks good.
    return (SystemClock.uptimeMillis() % (800 * dashCount * scale)) / (800.0f * dashCount * scale)
}

fun Color.toVec4(): FloatArray {
    val colorInt = this.toArgb()
    val red = ((colorInt shr 16) and 0xFF) / 255.0f
    val green = ((colorInt shr 8) and 0xFF) / 255.0f
    val blue = (colorInt and 0xFF) / 255.0f
    val alpha = ((colorInt shr 24) and 0xFF) / 255.0f
    return floatArrayOf(red, green, blue, alpha)
}

fun compileShader(shaderType: Int, shaderSource: String): Int {
    val shaderId = GLES20.glCreateShader(shaderType)
    GLES20.glShaderSource(shaderId, shaderSource)
    GLES20.glCompileShader(shaderId)

    val compileStatus = IntArray(1)
    GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compileStatus, 0)

    if (compileStatus[0] == 0) {
        val log = GLES20.glGetShaderInfoLog(shaderId)
        GLES20.glDeleteShader(shaderId)
        throw RuntimeException("Failed to compile vertex shader: \n$log")
    }
    return shaderId
}