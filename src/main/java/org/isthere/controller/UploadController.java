package org.isthere.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Description.
 *
 * @author Edward KIM
 * @since 1.0
 */
@Controller
@RequestMapping("rest")
public class UploadController {

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> upload(HttpServletRequest req) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        if (!(req instanceof DefaultMultipartHttpServletRequest)) {
            params.put("success", Boolean.FALSE);
            return new ResponseEntity(params, HttpStatus.BAD_REQUEST);
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            DefaultMultipartHttpServletRequest request = (DefaultMultipartHttpServletRequest) req;
            MultipartFile uploadedFile = request.getFile("uploadPath");
            String originalFilename = uploadedFile.getOriginalFilename();

            // TODO pass bytes array to image handler


            System.out.println(originalFilename);
            params.put("success", Boolean.TRUE);
            return new ResponseEntity(params, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println("\t>> " + ex.getMessage());
            params.put("success", Boolean.FALSE);
            params.put("error", ex.getMessage());
            return new ResponseEntity(params, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}
