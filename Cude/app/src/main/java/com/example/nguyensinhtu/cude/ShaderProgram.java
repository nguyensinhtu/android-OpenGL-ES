package com.example.nguyensinhtu.cude;

import android.content.Context;

import static android.opengl.GLES20.glUseProgram;

/**
 * Created by NGUYENSINHTU on 12/14/2017.
 */

abstract  public class ShaderProgram {
    // Uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    // Attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    // Shader program
    protected final int program;
    protected ShaderProgram(Context context, int vertexShaderResourceId,
                            int fragmentShaderResourceId) {
        // Compile the shaders and link the program.
        String vertexShaderSource = Util.readTextFileFromResource(context, R.raw.texture_vertex_shader);
        String fragmentShaderSource = Util.readTextFileFromResource(context, R.raw.texture_fragment_shader);
        int vertexShader = Util.compileVertexShader(vertexShaderSource);
        int fragmentShader = Util.compileFragmentShader(fragmentShaderSource);
        program = Util.linkProgram(vertexShader, fragmentShader);
    }

    public void useProgram() {
        // Set the current OpenGL shader program to this program.
        glUseProgram(program);
    }
}
