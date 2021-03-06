package org.tei.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.tei.form.UpdateForm;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class MyController {
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "index";
    }

    @RequestMapping(value = "/ajaxToGetUser", method = RequestMethod.POST)
    public String getUser(WebRequest request, String flag) {
        Map<String, String> result = new HashMap<>();
        String referer = request.getHeader("referer");
        System.out.println("referer => " + referer);
        return "redirect:success?flag=" + flag;
    }

    @RequestMapping("/testRequestByServletAPI")
    public String testRequestByServletAPI(HttpServletRequest request) {
        request.setAttribute("testRequestScope", "hello,servletAPI");
        return "success";
    }

    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("testRequestScope", "hello,ModelAndView");
        mav.setViewName("success");
        return mav;
    }

    @RequestMapping("/success")
    public String success(Model model, String flag) {
        model.addAttribute("flag", flag);
        return "success";
    }

    @RequestMapping("/testModel")
    public String testModel(Model model) {
        model.addAttribute("testRequestScope", "hello,Model");
        return "success";
    }

    @RequestMapping("/testMap")
    public String tesMap(Map<String, Object> map) {
        map.put("testRequestScope", "hello,Map");
        return "success";
    }

    @RequestMapping("/testModelMap")
    public String tesModelMap(ModelMap modelMap) {
        modelMap.addAttribute("testRequestScope", "hello,modelMap");
        System.out.println("modelMap.getClass() => " + modelMap.getClass().getName());
        return "success";
    }

    @RequestMapping("/testThymeleafView")
    public String testThymeleafView() {
        System.out.println("testThymeleafView");
        return "success";
    }

    @RequestMapping("/testForward")
    public String testForward() {
        System.out.println("testForward");
        return "forward:/testThymeleafView";
    }

    // Redirect??????????????????????????????????????????????????????????????????????????????
    @RequestMapping("/testRedirect")
    public String testRedirect() {
        System.out.println("testRedirect");
        // ???url??????http://???????????????????????????
        return "redirect:http://www.baidu.com";
    }

    // @RequestBody:??????post???put,delete?????????
    @RequestMapping(value = "/testRequestBody", method = {RequestMethod.POST})
    public String testRequestBody(@RequestBody String requestBody) {
        System.out.println("requestBody:" + requestBody);
        return "success";
    }

    // @RequestBody:??????post???put,delete?????????
    @RequestMapping(value = "/testRequestEntity", method = {RequestMethod.POST})
    public String testRequestEntity(RequestEntity<String> requestEntity) {
        System.out.println("requestEntity:" + requestEntity);
        return "success";
    }

    // @RequestBody:??????post???put,delete?????????RequestBody
    @RequestMapping(value = "/testJsonBody", method = {RequestMethod.POST})
    public String testJsonBody(@RequestBody String jsonBody) {
        System.out.println("jsonBody:" + jsonBody);
        return "success";
    }

    @RequestMapping(value = "/testResponse", method = {RequestMethod.GET})
    public void testResponse(HttpServletResponse response) throws IOException {
        System.out.println("response.status:" + response.getStatus());
        response.getWriter().print("hello,response");
    }

    @RequestMapping("/testResponseBody")
    @ResponseBody
    public String testResponseBody() {
        // ?????????@ResponseBody,?????????"success"???????????????????????????????????????????????????????????????????????????????????????success???
        return "success";
    }

    @RequestMapping("/testResponseBodyObj")
    @ResponseBody
    public Map<String, String> testResponseBodyObj() {
        UpdateForm user = new UpdateForm();
        user.setAge(20);
        user.setName("jack");
        // ???user?????????json??????????????????????????????pom.xml????????????com.fasterxml.jackson.core??????
        Map<String, String> mapObj = new HashMap<>();
        mapObj.put("name", "jack_map");
        mapObj.put("age", "20");
        // ????????????Map?????????????????????pojo??????????????????????????????json??????
        return mapObj;
    }

    @RequestMapping("/testAjax")
    @ResponseBody
    public String testAjax() {
        return "hello,ajax";
    }

    // ????????????????????????ResponseEntity??????????????????????????????
    @RequestMapping("/testDownload")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        //??????ServletContext??????
        ServletContext servletContext = session.getServletContext();
        //???????????????????????????????????????
        String realPath = servletContext.getRealPath("/static/img/1.jpg");
        //???????????????
        InputStream is = new FileInputStream(realPath);
        //??????????????????
        // is.available()?????????inputStream??????????????????????????????
        byte[] bytes = new byte[is.available()];
        //???????????????????????????
        int result = is.read(bytes);
        //??????HttpHeaders???????????????????????????
        MultiValueMap<String, String> headers = new HttpHeaders();
        //????????????????????????????????????????????????
        headers.add("Content-Disposition", "attachment;filename=1.jpg");
        //?????????????????????
        HttpStatus statusCode = HttpStatus.OK;
        //??????ResponseEntity??????
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //???????????????
        is.close();
        return responseEntity;
    }

    @RequestMapping("/testUpload")
    public String testUp(MultipartFile photo, HttpSession session) throws IOException {
        //?????????????????????????????????
        String fileName = photo.getOriginalFilename();
        //????????????????????????
        assert fileName != null;
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString() + hzName;
        //??????????????????photo???????????????
        ServletContext servletContext = session.getServletContext();
        String photoPath = servletContext.getRealPath("photo");
        File file = new File(photoPath);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
        }
        String finalPath = photoPath + File.separator + fileName;
        //??????????????????
        photo.transferTo(new File(finalPath));
        return "success";
    }

    @RequestMapping("/testExceptionAdviser")
    public String testExceptionAdviser() {
        throw new ArithmeticException("Test ArithmeticException");
    }
}
