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

    // Redirect相当于重新发送了一次请求，浏览器的地址栏也会发生变化
    @RequestMapping("/testRedirect")
    public String testRedirect() {
        System.out.println("testRedirect");
        // 在url前面http://，即被当作绝对路径
        return "redirect:http://www.baidu.com";
    }

    // @RequestBody:只有post，put,delete才会有
    @RequestMapping(value = "/testRequestBody", method = {RequestMethod.POST})
    public String testRequestBody(@RequestBody String requestBody) {
        System.out.println("requestBody:" + requestBody);
        return "success";
    }

    // @RequestBody:只有post，put,delete才会有
    @RequestMapping(value = "/testRequestEntity", method = {RequestMethod.POST})
    public String testRequestEntity(RequestEntity<String> requestEntity) {
        System.out.println("requestEntity:" + requestEntity);
        return "success";
    }

    // @RequestBody:只有post，put,delete才会有RequestBody
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
        // 因为有@ResponseBody,这里的"success"，并不会按照视图解析。而是按照普通的字符解析，即在网页输出success。
        return "success";
    }

    @RequestMapping("/testResponseBodyObj")
    @ResponseBody
    public Map<String, String> testResponseBodyObj() {
        UpdateForm user = new UpdateForm();
        user.setAge(20);
        user.setName("jack");
        // 将user对象以json的格式的字符串返回，pom.xml必须要有com.fasterxml.jackson.core依赖
        Map<String, String> mapObj = new HashMap<>();
        mapObj.put("name", "jack_map");
        mapObj.put("age", "20");
        // 可以返回Map类型，也可以是pojo类型，都可以被转换为json格式
        return mapObj;
    }

    @RequestMapping("/testAjax")
    @ResponseBody
    public String testAjax() {
        return "hello,ajax";
    }

    // 将返回类型设置为ResponseEntity，实现下载文件的功能
    @RequestMapping("/testDownload")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        //获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        //获取服务器中文件的真实路径
        String realPath = servletContext.getRealPath("/static/img/1.jpg");
        //创建输入流
        InputStream is = new FileInputStream(realPath);
        //创建字节数组
        // is.available()，获取inputStream所对应的文件的字节数
        byte[] bytes = new byte[is.available()];
        //将流读到字节数组中
        int result = is.read(bytes);
        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        //设置要下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename=1.jpg");
        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //关闭输入流
        is.close();
        return responseEntity;
    }

    @RequestMapping("/testUpload")
    public String testUp(MultipartFile photo, HttpSession session) throws IOException {
        //获取上传的文件的文件名
        String fileName = photo.getOriginalFilename();
        //处理文件重名问题
        assert fileName != null;
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString() + hzName;
        //获取服务器中photo目录的路径
        ServletContext servletContext = session.getServletContext();
        String photoPath = servletContext.getRealPath("photo");
        File file = new File(photoPath);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
        }
        String finalPath = photoPath + File.separator + fileName;
        //实现上传功能
        photo.transferTo(new File(finalPath));
        return "success";
    }

    @RequestMapping("/testExceptionAdviser")
    public String testExceptionAdviser() {
        throw new ArithmeticException("Test ArithmeticException");
    }
}
