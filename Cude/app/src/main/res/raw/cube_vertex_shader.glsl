attribute vec4 aPosition;
//attribute vec4 aColor;
//varying vec4 vColor;
uniform mat4 uMVPMatrix;

void main() {
    //vColor = aColor;
    gl_Position = uMVPMatrix * aPosition;
}

