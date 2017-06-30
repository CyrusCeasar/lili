package cn.sz.lili;

import org.alicebot.ab.ChatRobot;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenlei2 on 2017/6/23 0023.
 */
@Controller
public class TestService {


    @RequestMapping(value = "test")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String reqMsg = req.getParameter("req_msg");
        System.out.println(reqMsg);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("result_content",ChatRobot.getInstance().chat(reqMsg));
            jsonObject.put("result_msg","success");
            jsonObject.put("result_code","0");
            resp.getWriter().write(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
