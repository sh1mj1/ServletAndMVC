package springmvc.servlet.web.frontcontroller.v5;

import springmvc.servlet.web.frontcontroller.ModelView;
import springmvc.servlet.web.frontcontroller.MyView;
import springmvc.servlet.web.frontcontroller.v3.MemberFormControllerV3;
import springmvc.servlet.web.frontcontroller.v3.MemberListControllerV3;
import springmvc.servlet.web.frontcontroller.v3.MemberSaveControllerV3;
import springmvc.servlet.web.frontcontroller.v4.MemberFormControllerV4;
import springmvc.servlet.web.frontcontroller.v4.MemberListControllerV4;
import springmvc.servlet.web.frontcontroller.v4.MemberSaveControllerV4;
import springmvc.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import springmvc.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // ControllerV3, ControllerV4 같은 인터페이스 대신 아무 값이나 받을 수 있는 Object 로 변경.
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();


    // 생성자는 핸들러 매핑과 어댑터를 초기화한다.
    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v4/v3/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v4/v3/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v4/v3/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handler = getHandler(req);
        if (handler == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handle(req, resp, handler);

        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), req, resp);


    }

    /**
     * handlerMappingMap 에서 URL 에 매핑된 핸들러(컨트롤러) 객체를 찾아서 반환.
     *
     * @param request
     * @return
     */
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    /**
     * handler 을 처리할 수 있는 어댑터를 adapter.supports(handler) 을 통해 찾는다.
     *
     * @param handler
     * @return
     */
    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter 을 찾을 수 없습니다. handler=" + handler);
    }


    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }


}
