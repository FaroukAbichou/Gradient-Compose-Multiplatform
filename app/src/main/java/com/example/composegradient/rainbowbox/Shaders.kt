package com.example.composegradient.rainbowbox

import org.intellij.lang.annotations.Language

// Shader that runs for each vertex geometry that is passed in
@Language("glsl")
val VERTEX_SHADER = """
    #version 300 es
    precision highp float;
    precision highp int;
    
    uniform highp mat4 uModelMatrix;
    uniform highp mat4 uViewProjMatrix;
    uniform highp float uStrokeWidth;
    uniform highp float uAspectRatio;
    
    layout (location = 0) in vec2 aPosition;
    
    // Offset represents the direction in which this point should be shifted to form the border
    layout (location = 1) in vec2 aOffset;
    
    // Progress changes from 0.0 to 1.0 along the perimeter (does not account for scaling, not yet).
    layout (location = 2) in float aProgress;
    
    out float vProgress;
    
    // This version of normalize() 'correctly' handles zero-length vectors
    vec2 safeNormalize(vec2 v) {
        if (length(v) == 0.0) return v;
        return normalize(v);
    }
    
    void main() {
        float aspectRatio = uAspectRatio;
        vProgress = aProgress;
        vec4 worldPosition = uModelMatrix * vec4(aPosition, 0.0, 1.0);
    
        // We need to get the correct direction for the offset that forms the border (the thickness of the bounding box).
        // For that we see where the point ends up in the 'world' coordinates, then correct by aspect ratio to account for scaling,
        // and then normalize. Ta-da, offset direction!
        vec4 offsetPosition = uModelMatrix * vec4(aPosition + aOffset, 0.0, 1.0);
        vec2 difference = offsetPosition.xy - worldPosition.xy;
        vec4 offset = vec4(safeNormalize(difference) * uStrokeWidth, 0.0, 0.0);
        gl_Position = uViewProjMatrix * (worldPosition + offset);
    }
    """.trimIndent()

// Shader that runs for each pixel of the computed area - derived from the vertex shader output
@Language("glsl")
val FRAGMENT_SHADER = """
    #version 300 es

    precision highp float;

    uniform highp float uAspectRatio;
    uniform highp float uDashCount;
    uniform highp float uTimeOffset;
    uniform highp float uStretchFactor;
    uniform highp int uNumberOfColors;
    
    in highp float vProgress;
    
    uniform vec4 uColors[6]; // Uniform array for colors

    out vec4 oColor;
    
    float isInRange(float x, float start, float end) {
        return step(start, x) * (1.0 - step(end, x));
    }

    void main() {
        float aspectRatio = uAspectRatio ;

        float progress = (vProgress + uTimeOffset * 16.0f) * uStretchFactor;
        int numColors = uNumberOfColors; // Replace MAX_COLORS with the maximum number of colors

        float colorIndex = mod(uDashCount * progress / float(numColors), float(numColors) );

        vec4 currentColor = uColors[int(colorIndex)];
        vec4 nextColor = uColors[int(colorIndex) + 1];

        oColor = mix(currentColor, nextColor, fract(colorIndex));
    }
    """.trimIndent()