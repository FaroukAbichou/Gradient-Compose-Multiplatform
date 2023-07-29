package com.example.composegradient.rainbowbox

import android.opengl.GLES11.glBindBuffer
import android.opengl.GLES20
import android.opengl.GLES20.glBufferData
import android.opengl.GLES20.glDeleteBuffers
import android.opengl.GLES20.glGenBuffers

private const val UNINITIALIZED = -1

class RectOutlineBuffer {

    private val _vbo = IntArray(1)
    private val vbo: Int
        get() = _vbo[0]
    var vertexCount = 0
        private set

    init {
        _vbo[0] = UNINITIALIZED
    }

    val numberValuesPerVertex = 8

    private fun ensureInitialized() {
        if (_vbo[0] != UNINITIALIZED) {
            return
        }
        val o = 1.0f

        // @formatter:off
        /* ktlint-disable no-multi-spaces */
        /* ktlint-disable indent */
        val attributeValues = floatArrayOf(
            //position   offset             progress  padding
            -1.0f, -1.0f,  0.0f, 1.0f,      0.0f,0f, 0f, 0f,
            1.0f, -1.0f,   1.0f, 0.0f,      0.0f,0f, 0f, 0f,
            1.0f, 1.0f,    0.0f, 1.0f,      1.0f,0f, 0f, 0f,
            -1.0f, 1.0f,   0.0f, 1.0f,      1.0f,0f, 0f, 0f,
        )
        /* ktlint-disable indent */
        /* ktlint-disable no-multi-spaces */
        // @formatter:on

        vertexCount = attributeValues.size / numberValuesPerVertex
        val attributesBuffer = Util.createFloatBuffer(attributeValues)
        glGenBuffers(1, _vbo, 0)
        glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo)
        glBufferData(
            GLES20.GL_ARRAY_BUFFER, attributeValues.size * Util.FLOAT_SIZE,
            attributesBuffer, GLES20.GL_STATIC_DRAW
        )
        glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
    }

    fun bind() {
        ensureInitialized()
        glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo)
    }

    fun unbind() {
        glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
    }

    fun delete() {
        glDeleteBuffers(1, _vbo, 0)
        _vbo[0] = UNINITIALIZED
    }
}
