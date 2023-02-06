package com.janeforjane.repository;

import com.github.sardine.DavResource;

import java.util.List;

public interface WebDavRepository {

    List<DavResource> getFilesName(String username, String password, String directoryName);
    void putFile(String username, String password, String directoryName);
    void getFile(String username, String password, String directoryName);
    void getAndPrintFile(String username, String password, String directoryName);



}
