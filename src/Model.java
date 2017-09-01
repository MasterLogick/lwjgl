import org.newdawn.slick.opengl.CompositeImageData;
import org.newdawn.slick.opengl.Texture;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import static org.lwjgl.opengl.GL11.*;

public class Model {
    private Texture texture;
    private Vector<Float[]> vectors = new Vector<>();
    private Vector<Float[]> texCords = new Vector<>();
    private HashMap<Integer, Integer[]> faces = new HashMap<>();
    private HashMap<Integer, Integer[]> triangles = null;


    public Model(InputStream file, Texture texture) {
        this.texture = texture;
        boolean ret, aa;
        // int a;
        BufferedReader bf = new BufferedReader(new InputStreamReader(file));
        String s = null;
        String face = null;
        do {
            ret = false;
            aa = true;
            //a = vectors.size();
            try {
                s = bf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (s == null) break;
            //System.out.println(s.equals(""));
            //------before vect
            while (aa) {
                try {
                    s = bf.readLine();
                } catch (IOException e) {
                    e.printStackTrace();

                }
                if (s == null) {
                    ret = true;
                    break;
                }
                if (s.startsWith("v ")) {
                    aa = false;
                }
            }
            if (ret) break;
            //------ vect read
            Scanner scan = null;
            while (s.startsWith("v ")) {
                s = new StringBuilder(s).deleteCharAt(0).deleteCharAt(0).toString();
                scan = new Scanner(s);
                scan.useDelimiter(" ");
                vectors.add(new Float[]{Float.parseFloat(scan.next()), Float.parseFloat(scan.next()), Float.parseFloat(scan.next())});
                try {
                    s = bf.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //------ before tex cord
            while (!s.startsWith("vt ")) {
                try {
                    s = bf.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //------ tex cord read
            while (s.startsWith("vt ")) {
                s = new StringBuilder(s).deleteCharAt(0).deleteCharAt(0).deleteCharAt(0).toString();
                scan = new Scanner(s);
                scan.useDelimiter(" ");
                texCords.add(new Float[]{Float.parseFloat(scan.next()), Float.parseFloat(scan.next()), Float.parseFloat(scan.next())});
                try {
                    s = bf.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //----- before face
            while (!s.startsWith("f ")) {
                try {
                    s = bf.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //----- face read
            while (s.startsWith("f ")) {
                s = new StringBuilder(s).deleteCharAt(0).deleteCharAt(0).toString();
                s.trim();
                //System.out.println(s);
                scan = new Scanner(s);
                scan.useDelimiter(" ");
                //System.out.println((s.split(" ").length-1));
                if ((s.split(" ").length - 1) == 2) {
                    if (triangles == null) {
                        triangles = new HashMap<>();
                    }
                    while (scan.hasNext()) {
                        face = scan.next();
                        triangles.put(triangles.size(), new Integer[]{
                                Integer.parseInt(face.substring(0, face.indexOf("/"))) - 1,
                                Integer.parseInt(face.substring(face.indexOf("/") + 1, face.length())) - 1});
                    }
                    try {
                        s = bf.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                while (scan.hasNext()) {
                    face = scan.next();
                    faces.put(faces.size(), new Integer[]{
                            Integer.parseInt(face.substring(0, face.indexOf("/"))) - 1,
                            Integer.parseInt(face.substring(face.indexOf("/") + 1, face.length())) - 1});
                }
                try {
                    s = bf.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } while (true);

        /*System.out.println(Arrays.toString(vectors.toArray()));
        System.out.println(Arrays.toString(texCords.toArray()));
        System.out.println(Arrays.toString(faces.values().toArray()));*/
    }

    public void draw() {
        texture.bind();
        glBegin(GL_QUADS);
        for (int i = 0; i < faces.size()/*&&(i<InputThread.c)*/; i++) {
            //System.out.println(i);
            /*System.out.println(texCords.get(faces.get(i)[1])[0]+" "+ texCords.get(faces.get(i)[1])[1]+" "+ texCords.get(faces.get(i)[1])[2]);
            System.out.println(vectors.get(faces.get(i)[0])[0]+" "+ vectors.get(faces.get(i)[0])[1]+" "+ vectors.get(faces.get(i)[0])[2]);*/
            glTexCoord3f(texCords.get(faces.get(i)[1])[0], texCords.get(faces.get(i)[1])[1], texCords.get(faces.get(i)[1])[2]);
            glVertex3f(vectors.get(faces.get(i)[0])[0], vectors.get(faces.get(i)[0])[1], vectors.get(faces.get(i)[0])[2]);
        }
        glEnd();
        if (triangles != null) {
            //System.out.println(triangles.size());
            glBegin(GL_TRIANGLES);
            for (int i = 0; i < triangles.size()/*&&(i<InputThread.c)*/; i++) {
                glTexCoord3f(texCords.get(triangles.get(i)[1])[0], texCords.get(triangles.get(i)[1])[1], texCords.get(triangles.get(i)[1])[2]);
                glVertex3f(vectors.get(triangles.get(i)[0])[0], vectors.get(triangles.get(i)[0])[1], vectors.get(triangles.get(i)[0])[2]);

            }
            glEnd();
        }
//        System.out.println("texture: "+texCords.get(faces.get(InputThread.c)[1])[0]+" "+ texCords.get(faces.get(InputThread.c)[1])[1]+" "+ texCords.get(faces.get(InputThread.c)[1])[2]);
//        System.out.println("vector: "+vectors.get(faces.get(InputThread.c)[0])[0]+" "+ vectors.get(faces.get(InputThread.c)[0])[1]+" "+ vectors.get(faces.get(InputThread.c)[0])[2]);
    }

    public void release() {
        texture.release();
    }
}
