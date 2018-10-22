package AnalizadorLexico;//asd

import java.io.*;

public class FileManager {
    private File fileFuente = null;
    private FileReader in = null;

    public FileManager(File fuente) throws FileNotFoundException {
        in = new FileReader(fuente);
        this.fileFuente = fuente;
    }

    public Character readChar() throws IOException {
        int ret = in.read();
        if (ret != -1)
            return (char) ret;
        else
            return null;
    }

    public static void write(String entry, File f) throws IOException {
        f.delete();
        BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
        writer.write(entry);
        writer.close();
    }
}