package org.isthere.controller;

import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sock.InfoGetter;

import java.io.IOException;
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
public class DetailController {

    @RequestMapping(value = "socket", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> socket(@RequestParam String reqAppNo) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            InfoGetter infoGetter = new InfoGetter();
            infoGetter.requestDetail(reqAppNo);
            JSONObject obj = infoGetter.parseDataFromHtmlString();
            params.put("result", obj);
            return new ResponseEntity(params, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            params.put("error", ex.getMessage());
            return new ResponseEntity(params, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
