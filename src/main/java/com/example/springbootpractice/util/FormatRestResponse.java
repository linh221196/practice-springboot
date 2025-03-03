package com.example.springbootpractice.util;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.springbootpractice.domain.RestResponse;
import com.example.springbootpractice.util.annotation.ApiMessage;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        
                HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
                int status = servletResponse.getStatus();

                RestResponse<Object> res = new RestResponse<Object>();
                res.setStatusCode(status);

                if(body instanceof String){
                    return body;
                }
                if (body instanceof Resource) {
                    return body; // Skip wrapping for Resource
                }

                String path = request.getURI().getPath();
                if(path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")){
                    return body;
                }

                if(status >=400){
                return body;

                }else{
                    res.setData(body);
                    ApiMessage apiMessage = returnType.getMethodAnnotation(ApiMessage.class);
                    res.setMessage(apiMessage != null? apiMessage : "CALL API SUCCEDED");

                }
        return res;
    }

}
