package res;

import java.io.InputStream;

public class ResourseManager {
    public static InputStream getResourse(String relativePath) {
        return ResourseManager.class.getResourceAsStream(relativePath);
    }
}
