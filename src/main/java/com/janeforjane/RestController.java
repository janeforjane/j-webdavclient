package com.janeforjane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/weddavclient")
public class RestController {

    @Autowired
    FilesLogic logic;


    @GetMapping("/files")
    List<String> getFiles(){
        return logic.getListOfFiles();
    }


}
