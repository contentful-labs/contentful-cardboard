#version 100
precision mediump float;

varying vec4 v_Color;
varying vec3 v_Grid;
varying vec2 v_TexCoordinate;

void main() {
    float depth = gl_FragCoord.z / gl_FragCoord.w;
    float fog = 1.0 - (depth - 80.0) / 20.0;

    // draw a line?
    if ((mod(abs(v_Grid.x), 10.0) < 0.1) || (mod(abs(v_Grid.z), 10.0) < 0.1)) {
        gl_FragColor = fog* max(0.0, (90.0-depth) / 90.0) * vec4(1.0, 1.0, 1.0, 1.0)
                + min(1.0, depth / 90.0) * v_Color;
    } else {
        gl_FragColor = fog * v_Color;
    }
}
