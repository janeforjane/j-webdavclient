package com.janeforjane.repository;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.StringJoiner;

@Component
public class WebDavRepositoryImpl implements WebDavRepository {

    @Value("${application.wedbdavserver.address}")//address: http://localhost:8080/remote.php/dav/files/
    private static String serverAddress;


    @Override
    public List<DavResource> getFilesName(String username, String password, String directoryName) {

        Sardine sardine = SardineFactory.begin(username, password);
        try {
            return sardine.list(generatePath(username, directoryName).toString(), 1, true);//"http://localhost:8080/remote.php/dav/files/username/Documents/"
        } catch (IOException e) {
            System.out.println("Error message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putFile(String username, String password, String directoryName) {

        Sardine sardine = SardineFactory.begin(username, password);
        InputStream fis = null;
        try {
            fis = new FileInputStream(new File("filename.txt"));
            sardine.put(generatePath(username,directoryName).add("filename.txt").toString(), fis);

        } catch (FileNotFoundException e) {
            System.out.println("Error message: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getFile(String username, String password, String directoryName) {

        Sardine sardine = SardineFactory.begin(username, password);

        try {
            InputStream inputStream = sardine.get(generatePath(username,directoryName).add("filename.txt").toString());
            FileOutputStream fout = new FileOutputStream("filename.txt");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fout,1048576);

            for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
                bufferedOutputStream.write(c);

            }
            bufferedOutputStream.flush();
            inputStream.close();
            bufferedOutputStream.close();

        }catch (IOException e){
            System.out.println("Error message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAndPrintFile(String username, String password, String directoryName) {

        Sardine sardine = SardineFactory.begin(username, password);

        try {
            InputStream in = sardine.get(generatePath(username,directoryName).add("filename.txt").toString());

            try {
                InputStreamReader inR = new InputStreamReader( in );
                BufferedReader buf = new BufferedReader( inR );
                String line;
                while ( ( line = buf.readLine() ) != null ) {
                    System.out.println( line );
                }
            } finally {
                in.close();
            }
        }catch (IOException e){
            System.out.println("Error message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static StringJoiner generatePath(String username,String directoryName){
        return new StringJoiner(serverAddress).add(username).add(directoryName);
    }
}
