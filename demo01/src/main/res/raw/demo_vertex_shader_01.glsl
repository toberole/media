//# version 120

void main() {
    const vec4 vertices[3] = vec4[3](
    vec4(0.25f, -0.25f, 0.5f, 1.0f),
    vec4(-0.25f, -0.25f, 0.5f, 1.0f),
    vec4(0.25f, 0.25f, 0.5f, 1.0f)
    );

    gl_Position = vertices[gl_VertexID];

}
