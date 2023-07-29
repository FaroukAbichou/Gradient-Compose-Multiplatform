package com.example.composegradient.rainbowbox

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

@Composable
fun GradyBox(
    modifier: Modifier = Modifier,
    aspectRatio: Float = 4.0f,
    layerModelMatrix: FloatArray = FloatArray(16),
    viewProjMatrix: FloatArray = FloatArray(16),
    shaderProgram: ShaderProgram = ShaderProgram(),
    rectOutlineVao: RectOutlineVao = RectOutlineVao(),
    stretchFactor: Float = 0.1f,
    colors : List<Color>  = listOf(
        Color(1.0f, 0.0f, 0.0f, 1.0f), // Red
        Color(1.0f, 0.0f, 0.0f, 1.0f), // Red
        Color(1.0f, 0.0f, 0.0f, 1.0f), // Red
        Color(0xff111111),
        Color(1.0f, 0.0f, 0.0f, 1.0f), // Red
        Color(1.0f, 0.0f, 0.0f, 1.0f), // Red
    )
) {

    fun setupMatrices() {
        Matrix.setIdentityM(layerModelMatrix, 0)

        Matrix.setIdentityM(layerModelMatrix, 0)
        Matrix.scaleM(layerModelMatrix, 0, 0.5f, 2.5f, 1.0f)
        Matrix.setIdentityM(viewProjMatrix, 0)
        Matrix.scaleM(viewProjMatrix, 0, 2.0f, aspectRatio, 0.0f)
    }

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
                    }

                    override fun onDrawFrame(gl: GL10?) {
                        setupMatrices()

                        rectOutlineVao.bind()
                        shaderProgram.bind()
                        shaderProgram.bindUniforms(
                            aspectRatio,
                            layerModelMatrix,
                            viewProjMatrix,
                            stretchFactor,
                            colors
                        )

                        // draw box
                        glDrawArrays(GL_TRIANGLE_FAN, 0, rectOutlineVao.vertexCount())

                        shaderProgram.unbind()
                        rectOutlineVao.unbind()
                    }
                })
            }

        },
        modifier = modifier
    )
}