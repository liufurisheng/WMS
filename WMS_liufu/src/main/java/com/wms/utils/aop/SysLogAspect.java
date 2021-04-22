package com.wms.utils.aop;

import com.alibaba.fastjson.JSON;
import com.wms.annotation.MyLog;
import com.wms.entity.SysLog;
import com.wms.entity.User;
import com.wms.repository.SysLogRepository;
import com.wms.utils.HttpContextUtils;
import com.wms.utils.IPUtils;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


@Aspect
@Component
public class SysLogAspect {
	@Autowired
    private SysLogRepository sysLogService;
	
	//定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.wms.annotation.MyLog)")
    public void logPoinCut() {
    	
    }
    
    @Around("logPoinCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    	Object result =  proceedingJoinPoint.proceed();
    	
    	SysLog sysLog = new SysLog();
    	if (myLog != null) {
            String value = myLog.value();
            sysLog.setOperation(value);//保存获取的操作
        }
    	sysLog.setMethod(className + "." + methodName);
    	sysLog.setParams(params);
        sysLog.setCreateDate(new Date());
        User user = (User) SecurityUtils.getSubject().getPrincipal();
       
        sysLog.setUsername(user.getUsername());
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        sysLog.setIp(IPUtils.getIpAddr(request));
        String params = JSON.toJSONString(result);
        sysLog.setResultType(params);
        
    	System.out.println("结果数据是"+params);
    	
    	 sysLogService.save(sysLog);
    	return result;
   }
    MyLog myLog=null;
    String className=null;
    String methodName=null;
    String params=null;
   
  //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        System.out.println("切面。。。。。");
        //保存日志
        SysLog sysLog = new SysLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

      //获取操作
        myLog = method.getAnnotation(MyLog.class);
      //获取请求的类名
         className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
         methodName = method.getName();
       // sysLog.setMethod(className + "." + methodName);

      //请求的参数
        Object[] args = joinPoint.getArgs();
       //将参数所在的数组转换成json,参数太多选有用的
       // String params = JSON.toJSONString(args);
        params = JSON.toJSONString(args[0]);
      //获取用户名
        User user = (User) SecurityUtils.getSubject().getPrincipal();
       //获取用户ip地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
       
     }
    
}
