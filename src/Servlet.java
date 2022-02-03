import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

@WebServlet(name = "Servlet", urlPatterns = "/Servlet")
public class Servlet extends HttpServlet {
    HashMap<String, location> mp = new HashMap<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 星号表示所有的异域请求都可以接受，
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        //获取微信小程序传递的参数值并打印
        String transInfo = request.getParameter("transInfo");

        String pre = transInfo.substring(0, 3), val = transInfo.substring(3);
        //System.out.println(pre+' '+val);
        if (pre.equals("chk")) {
            if (mp.get(val) == null) {
                Writer out = response.getWriter();
                out.write("nope");
                out.flush();
            } else {
                Writer out = response.getWriter();
                StringBuilder sb = new StringBuilder();
                location loo = mp.get(val);
                sb.append(loo.strLa).append(',').append(loo.strLo).append(',').append(loo.nowLa).append(',').append(loo.nowLo);
                sb.append(',').append(loo.sum);
                out.write(sb.toString());
                out.flush();
            }
        } else if (pre.equals("crt")) {
            if (mp.get(val) == null) {
                Writer out = response.getWriter();
                out.write("good");
                out.flush();
            } else {
                Writer out = response.getWriter();
                out.write("change");
                out.flush();
            }
        } else if (pre.equals("sur")) {
            if (mp.get(val) != null) {
                Writer out = response.getWriter();
                out.write("nope");
                out.flush();
            } else {
                String[] s = {};
                s = val.split(",");
                location loc = new location();
                loc.nowLa = loc.strLa = s[1];
                loc.nowLo = loc.strLo = s[2];
                loc.sum = 0;
                mp.put(s[0], loc);
                Writer out = response.getWriter();
                out.write("success");
                out.flush();
            }
        }
        System.out.println("In:" + transInfo);
        //向小程序端传递数据
//        Writer out = response.getWriter();
//        out.write("后台给小程序端的数据");
//        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

