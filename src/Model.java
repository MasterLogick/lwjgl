import org.newdawn.slick.opengl.Texture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;

public class Model {
    private Texture texture;
    private Vector<Float[]> vectors = new Vector<>();
    private Vector<Float[]> texCords = new Vector<>();
    private Vector<Float[]> normals = new Vector<>();
    private HashMap<Integer, Integer[]> faces = new HashMap<>();
    private HashMap<Integer, Integer[]> triangles = new HashMap<>();


    public Model(InputStream file, Texture texture) throws IOException {
        this.texture = texture;
        BufferedReader bf = new BufferedReader(new InputStreamReader(file));
        String s;
        int i = 0;
        while ((s = bf.readLine()) != null) {
            System.out.println(++i + " " + s);
            String[] arr = s.split(" ");
            switch (arr[0]) {
                case "v":
                    vectors.add(new Float[]{Float.parseFloat(arr[2]), Float.parseFloat(arr[3]), Float.parseFloat(arr[4])});
                    break;
                case "vt":
                    texCords.add(new Float[]{Float.parseFloat(arr[1]), Float.parseFloat(arr[2]), Float.parseFloat(arr[3])});
                    break;
                case "vn":
                    normals.add(new Float[]{Float.parseFloat(arr[1]), Float.parseFloat(arr[2]), Float.parseFloat(arr[3])});
                    break;
                case "f":
                    for (String a :
                            arr) {
                        if (a.equals("f")) continue;
                        String[] b = a.split("[/]");
                        if (arr.length == 4) {
                            triangles.put(triangles.size(), new Integer[]{Integer.parseInt(b[0]) - 1, Integer.parseInt(b[1]) - 1, Integer.parseInt(b[2]) - 1});
                        } else if (arr.length == 5) {
                            faces.put(triangles.size(), new Integer[]{Integer.parseInt(b[0]) - 1, Integer.parseInt(b[1]) - 1, Integer.parseInt(b[2]) - 1});
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void draw() {
        if (texture != null) {
            texture.bind();
            glBegin(GL_QUADS);
            for (int i = 0; i < faces.size(); i++) {
                glNormal3f(normals.get(faces.get(i)[2])[0], normals.get(faces.get(i)[2])[1], normals.get(faces.get(i)[2])[2]);
                glTexCoord3f(texCords.get(faces.get(i)[1])[0], texCords.get(faces.get(i)[1])[1], texCords.get(faces.get(i)[1])[2]);
                glVertex3f(vectors.get(faces.get(i)[0])[0], vectors.get(faces.get(i)[0])[1], vectors.get(faces.get(i)[0])[2]);
            }
            glEnd();
            glBegin(GL_TRIANGLES);
            for (int i = 0; i < triangles.size(); i++) {
                glNormal3f(normals.get(triangles.get(i)[2])[0], normals.get(triangles.get(i)[2])[1], normals.get(triangles.get(i)[2])[2]);
                glTexCoord3f(texCords.get(triangles.get(i)[1])[0], texCords.get(triangles.get(i)[1])[1], texCords.get(triangles.get(i)[1])[2]);
                glVertex3f(vectors.get(triangles.get(i)[0])[0], vectors.get(triangles.get(i)[0])[1], vectors.get(triangles.get(i)[0])[2]);
            }
            glEnd();

        } else {
            glBegin(GL_QUADS);
            for (int i = 0; i < faces.size(); i++) {
                glNormal3f(normals.get(faces.get(i)[2])[0], normals.get(faces.get(i)[2])[1], normals.get(faces.get(i)[2])[2]);
                glVertex3f(vectors.get(faces.get(i)[0])[0], vectors.get(faces.get(i)[0])[1], vectors.get(faces.get(i)[0])[2]);
            }
            glEnd();
            glBegin(GL_TRIANGLES);
            for (int i = 0; i < triangles.size(); i++) {
                glNormal3f(normals.get(triangles.get(i)[2])[0], normals.get(triangles.get(i)[2])[1], normals.get(triangles.get(i)[2])[2]);
                glVertex3f(vectors.get(triangles.get(i)[0])[0], vectors.get(triangles.get(i)[0])[1], vectors.get(triangles.get(i)[0])[2]);
            }
            glEnd();
        }
    }

    public void release() {
        if (texture != null) {
            texture.release();
        }
    }
}
