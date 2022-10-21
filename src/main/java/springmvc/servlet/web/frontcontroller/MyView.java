package springmvc.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MyView {

    private String viewPath;    // /WEB_INF/views/new-form.jsp

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    /**
     * 모델 정보를 함께 받는다.
     * Controller V3 부터 render
     * @param model
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void render(Map<String, Object> model,
                       HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    /**
     * ControllerV2 에서의 render
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }


    /**
     * 모델의 데이터를 꺼내서 request 에 담기.
     *
     * @param model
     * @param request
     */
    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
//        model.forEach((key, value) -> request.setAttribute(key, value));
        model.forEach(request::setAttribute); // 위와 같은 것임.
    }

}