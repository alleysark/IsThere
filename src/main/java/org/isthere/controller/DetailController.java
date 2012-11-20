package org.isthere.controller;

import org.isthere.connector.kipris.DetailPatentInfoGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("rest")
public class DetailController {

    @Autowired
    private DetailPatentInfoGetter detailPatentInfoGetter;

    @RequestMapping(value = "detailPatentGetter", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> detailPatentGetter(@RequestParam String reqAppNo) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();

        try {
            Map<String,String > detailData =
                detailPatentInfoGetter.requestDetail(reqAppNo);

            if( null == detailData){
                params.put("success", false);
            }else{
                params.put("success", true);
                params.put("total", 1);
                params.put("detInfo", detailData);
            }
            return new ResponseEntity(params, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            params.put("error", ex.getMessage());
            return new ResponseEntity(params, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
