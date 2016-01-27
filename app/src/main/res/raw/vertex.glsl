#version 100
precision mediump float;

uniform mat4 u_Model;
uniform mat4 u_MVP;
uniform mat4 u_MVMatrix;
uniform vec3 u_LightPosition;
uniform vec3 u_LightColor;

attribute vec4 a_Position;
attribute vec3 a_Normal;
attribute vec2 a_TexCoordinate;

varying vec4 v_Color;
varying vec3 v_Grid;
varying vec2 v_TexCoordinate;


void main() {
   v_Grid = vec3(u_Model * a_Position);

   vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);
   vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));

   float distance = length(u_LightPosition - modelViewVertex);
   vec3 lightVector = normalize(u_LightPosition - modelViewVertex);
   float diffuse = max(dot(modelViewNormal, lightVector), 0.5);

   diffuse = diffuse * (1.0 / (1.0 + (0.00001 * distance * distance)));
   v_Color = vec4(u_LightColor[0] * diffuse, u_LightColor[1] * diffuse, u_LightColor[2] * diffuse, 1.0);
   gl_Position = u_MVP * a_Position;
   v_TexCoordinate = a_TexCoordinate;
}
