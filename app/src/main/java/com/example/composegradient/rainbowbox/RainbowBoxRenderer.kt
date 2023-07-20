package com.example.composegradient.rainbowbox

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Size
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.math.min

@Composable
fun RainbowBox() {
    var aspectRatio by remember { mutableStateOf(4.0f) }

    // Helper class that wraps the GLSL shaders, binds them etc
     val shaderProgram: ShaderProgram = ShaderProgram()

    // Helper class containing the vertex construction
    // and binding code to use it with GL.
     val rectOutlineVao = RectOutlineVao()

    // Matrices
     val layerModelMatrix = FloatArray(16)
     val viewProjMatrix = FloatArray(16)

    fun setupMatrices() {
        Matrix.setIdentityM(layerModelMatrix, 0)

        Matrix.setIdentityM(layerModelMatrix, 0)
        Matrix.scaleM(layerModelMatrix, 0, 0.5f, 0.5f, 1.0f)
        Matrix.setIdentityM(viewProjMatrix, 0)
        Matrix.scaleM(viewProjMatrix, 0, 2.0f, aspectRatio, 0.0f)
    }

    // Calculate the aspect ratio whenever the size of the Composable changes
    AndroidView(
        factory = { context ->
            GLSurfaceView(context).apply {
                setEGLContextClientVersion(2)
                setEGLConfigChooser(8, 8, 8, 8, 16, 0)
                setRenderer(object : GLSurfaceView.Renderer {
                    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
                        shaderProgram.initialize()
                    }

                    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                        glViewport(0, 0, width, height)
                        aspectRatio = height.toFloat()
                    }

                    override fun onDrawFrame(gl: GL10?) {
                        // set background to white
//                        glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
//                        glClear(GL_COLOR_BUFFER_BIT)

                        setupMatrices()

                        // bind geometry
                        rectOutlineVao.bind()
                        // bind shader
                        shaderProgram.bind()
                        shaderProgram.bindUniforms(aspectRatio, layerModelMatrix, viewProjMatrix)

                        // draw box
                        glDrawArrays(GL_TRIANGLE_FAN, 0, rectOutlineVao.vertexCount())

                        // unbind shader
                        shaderProgram.unbind()
                        // unbind geometry
                        rectOutlineVao.unbind()
                    }
                })
            }
        },
        modifier = Modifier.fillMaxSize()
    )

}