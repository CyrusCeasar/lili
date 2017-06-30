package cn.sz.lili;

import cn.sz.lili.module.PromptMsg;
import cn.sz.lili.module.PromptMsgMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenlei2 on 2017/6/29 0029.
 */
@Controller
public class PromptMsgService  {

    @Autowired
    @Qualifier("promptMsgMapper")
    PromptMsgMapper promptMsgMapper;

    @ResponseBody
    @RequestMapping(value = "/promptMsg.json", method = RequestMethod.GET)
    protected String doGet() {
       /* String reqMsg = req.getParameter("req_msg");
        System.out.println(reqMsg);*/
        JSONObject jsonObject = new JSONObject();
        try {
            PromptMsg promptMsg = promptMsgMapper.getRandomPromptMsg();
            if(promptMsg != null) {
                jsonObject.put("result_content",promptMsg.getContent());
            }
            jsonObject.put("result_msg","success");
            jsonObject.put("result_code", "0");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
