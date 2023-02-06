package com.janeforjane;

import com.github.sardine.DavResource;
import com.janeforjane.repository.WebDavRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilesLogic {

    @Value("${application.wedbdavserver.login}")
    private String username;
    @Value("${application.wedbdavserver.password}")
    private String password;
    @Value("${application.wedbdavserver.serverdirectory}")
    private String dirName;

    @Autowired
    private WebDavRepository repository;

    public List<String> getListOfFiles() {

        List<String> listOfFiles = new ArrayList<>();

        for (DavResource davResource : repository.getFilesName(username, password, dirName)) {
            listOfFiles.add(davResource.getName());
        }
        return listOfFiles;
    }

    public void putFile() {
        repository.putFile(username, password, dirName);
    }

    public void getFile() {
        repository.getFile(username, password, dirName);
    }

    public void getAndPrint() {
        repository.getAndPrintFile(username, password, dirName);
    }


}
