package com.example.composegradient.rainbowbox

import android.opengl.GLES20
import android.os.SystemClock
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class ShaderProgram {

    private var shaderProgram: Int = 0

    private var dashCountUniformLocation = -1
    private var timeOffsetUniformLocation = -1
    private var aspectRatioUniformLocation = -1
    private var strokeWidthUniformLocation = -1
    private var viewProjUniformLocation = -1
    private var modelMatrixUniformLocation = -1
    private var stretchFactorUniformLocation = -1

    private var colorArrayLocation = -1
    private val MAX_COLORS = 5 // Set the maximum number of colors to 5

    fun initialize() {
        compileAndLinkShaders()
        colorArrayLocation = GLES20.glGetUniformLocation(shaderProgram, "uColors")
    }

    fun Color.toVec4(): FloatArray {
        val colorInt = this.toArgb()
        val red = ((colorInt shr 16) and 0xFF) / 255.0f
        val green = ((colorInt shr 8) and 0xFF) / 255.0f
        val blue = (colorInt and 0xFF) / 255.0f
        val alpha = ((colorInt shr 24) and 0xFF) / 255.0f
        return floatArrayOf(red, green, blue, alpha)
    }

    fun setColorsUniform(colors: List<Color>) {
        if (colors.size > MAX_COLORS) {
            throw IllegalArgumentException("Exceeded maximum number of colors")
        }

        val colorArray = FloatArray(MAX_COLORS * 4)
        colors.forEachIndexed { index, color ->
            color.toVec4().copyInto(colorArray, index * 4)
        }
        GLES20.glUniform4fv(colorArrayLocation, MAX_COLORS, colorArray, 0)
    }
    val colors = listOf(
        Color(1.0f, 0.0f, 0.0f, 1.0f), // Red
        Color(0.0f, 1.0f, 0.0f, 1.0f), // Green
        Color(0.0f, 0.0f, 1.0f, 1.0f), // Blue
        Color(1.0f, 1.0f, 0.0f, 1.0f), // Yellow
        Color(1.0f, 0.0f, 0.0f, 1.0f), // Red
    )
    /**
     * `bindUniforms` is setting variables on the shader program.
     * A uniform is a "constant" in the Shaders that doesn't change for each pixel,
     * but we still need to set the constants in order for it to function.
     */
    fun bindUniforms(
        aspectRatio: Float,
        layerModelMatrix: FloatArray,
        viewProjMatrix: FloatArray,
        stretchFactor: Float,
    ) {
        val perimeter = 50.0f
        val scale = 10.0f
        val dashCount = perimeter / DASH_LENGTH * scale
        GLES20.glUniformMatrix4fv(modelMatrixUniformLocation, 1, false, layerModelMatrix, 0)
        GLES20.glUniformMatrix4fv(viewProjUniformLocation, 1, false, viewProjMatrix, 0)
        GLES20.glUniform1f(strokeWidthUniformLocation, 0.02f)
        GLES20.glUniform1f(aspectRatioUniformLocation, aspectRatio)
        GLES20.glUniform1f(dashCountUniformLocation, dashCount)
        GLES20.glUniform1f(timeOffsetUniformLocation, timeOffset(2f, 25.0f))
        GLES20.glUniform1f(stretchFactorUniformLocation, stretchFactor)
        setColorsUniform(colors)
    }

    fun bind() {
        GLES20.glUseProgram(shaderProgram)
    }

    fun unbind() {
        GLES20.glUseProgram(0)
    }

    private fun compileAndLinkShaders() {
        // compile both the vertex and fragment shaders
        val vertexShaderId = compileShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER)
        val fragmentShaderId = compileShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER)

        // attach the shaders to the GL Program
        shaderProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(shaderProgram, vertexShaderId)
        GLES20.glAttachShader(shaderProgram, fragmentShaderId)

        GLES20.glLinkProgram(shaderProgram)

        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(shaderProgram, GLES20.GL_LINK_STATUS, linkStatus, 0)

        if (linkStatus[0] == 0) {
            val log = GLES20.glGetProgramInfoLog(shaderProgram)
            GLES20.glDeleteProgram(shaderProgram)
            throw RuntimeException("Failed to link shader program: \n$log")
        }

        dashCountUniformLocation = GLES20.glGetUniformLocation(shaderProgram, "uDashCount")
        modelMatrixUniformLocation = GLES20.glGetUniformLocation(shaderProgram, "uModelMatrix")
        viewProjUniformLocation = GLES20.glGetUniformLocation(shaderProgram, "uViewProjMatrix")
        strokeWidthUniformLocation = GLES20.glGetUniformLocation(shaderProgram, "uStrokeWidth")
        timeOffsetUniformLocation = GLES20.glGetUniformLocation(shaderProgram, "uTimeOffset")
        aspectRatioUniformLocation = GLES20.glGetUniformLocation(shaderProgram, "uAspectRatio")
        stretchFactorUniformLocation = GLES20.glGetUniformLocation(shaderProgram, "uStretchFactor")
    }
}


private const val DASH_LENGTH = 2.0f


// Helper function for the animation
fun timeOffset(dashCount: Float, scale: Float): Float {
    // Why 800? It looks good.
    return (SystemClock.uptimeMillis() % (800 * dashCount * scale)) / (800.0f * dashCount * scale)
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