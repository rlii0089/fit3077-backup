package Utilities;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class responsible for saving and loading instances of the game.
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */
public class ResourceManager {

    public static void save(Serializable data, String fileName) throws Exception{
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
            oos.writeObject(data);
        }
    }

    public static Object load(String fileName) throws Exception{
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
            return ois.readObject();
        }
    }
}
