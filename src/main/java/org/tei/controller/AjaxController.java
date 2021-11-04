package org.tei.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AjaxController {
    @RequestMapping(value = "/ajaxToGetUser", method = RequestMethod.POST)
    public Map<String, String> getUser() {
        Map<String, String> result = new HashMap<>();
        return result;
    }

    public void test(MultipartFile photo) throws IOException {
        FileCopyUtils.copyToByteArray(photo.getInputStream());
    }
}
