package top.ordinaryroad.commons.core.utils;

import org.springframework.http.MediaType;
import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.base.result.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HttpServlet工具类
 *
 * @author mjz
 * @date 2021/9/4
 * @see org.springframework.web.bind.ServletRequestUtils
 * @see org.springframework.web.util.ServletRequestPathUtils
 * @see org.springframework.web.util.ServletContextPropertyUtils
 */
public class HttpServletUtils {

    /**
     * 写入JSON数据
     *
     * @param response HttpServletResponse
     * @param s        String
     * @throws IOException IOException
     */
    public static void write(HttpServletResponse response, String s) throws IOException {
        if (response == null || s == null) {
            return;
        }
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(s);
    }

    public static void write(HttpServletResponse response, Result<?> result) throws IOException {
        if (result == null) {
            return;
        }
        write(response, result.toString());
    }

    public static void write(HttpServletResponse response, StatusCode statusCode) throws IOException {
        if (statusCode == null) {
            return;
        }
        write(response, Result.fail(statusCode));
    }

    public static void writeSuccess(HttpServletResponse response) throws IOException {
        write(response, Result.success());
    }

    public static void writeFail(HttpServletResponse response) throws IOException {
        write(response, Result.fail());
    }

}
