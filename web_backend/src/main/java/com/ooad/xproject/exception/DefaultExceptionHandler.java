//package com.ooad.xproject.exception;
//
//
//import com.ooad.xproject.vo.Result;
//import org.apache.ibatis.jdbc.Null;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import static com.ooad.xproject.constant.RespStatus.UNAUTHORIZED;
//
//@ControllerAdvice
//public class DefaultExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Result<Null> handleAuthorizationException(Exception e) {
//        String message = "Permission authentication failed";
//        return new Result<>(UNAUTHORIZED, message);
//    }
//}