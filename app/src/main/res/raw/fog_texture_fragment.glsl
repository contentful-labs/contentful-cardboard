#version 100
precision mediump float;

uniform sampler2D u_Texture;

varying vec4 v_Color;
varying vec2 v_TexCoordinate;

void main() {
    float depth = gl_FragCoord.z / gl_FragCoord.w;
    float fog = 1.0 - (depth - 80.0) / 20.0;
    fog = clamp(fog, 0.0, 1.0);

    vec4 texture = texture2D(u_Texture, v_TexCoordinate);
    vec4 textureAndLight = texture + v_Color;
    vec4 textureAndLightWithFog = fog * textureAndLight;
    gl_FragColor = vec4(textureAndLightWithFog.r, textureAndLightWithFog.g, textureAndLightWithFog.b, texture.a);
}
