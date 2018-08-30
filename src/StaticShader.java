public class StaticShader extends ShaderProgram {

    public StaticShader() {
        super(Main.class.getResourceAsStream("StaticVertexShader.txt"), Main.class.getResourceAsStream("StaticFragmentShader.txt"));
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(0, "textureCoords");
    }
}
