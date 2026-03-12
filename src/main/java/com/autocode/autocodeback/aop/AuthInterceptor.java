package com.autocode.autocodeback.aop;

import com.autocode.autocodeback.annotation.AuthCheck;
import com.autocode.autocodeback.common.StatusCode;
import com.autocode.autocodeback.exception.BusinessException;
import com.autocode.autocodeback.model.entity.User;
import com.autocode.autocodeback.model.enums.UserRoleEnum;
import com.autocode.autocodeback.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 权限校验 AOP 拦截器
 * <p>
 * 基于 {@link AuthCheck} 注解实现方法级别的权限校验。
 * 在目标方法执行前，从 Session 获取当前登录用户并验证其角色是否满足要求。
 * </p>
 */
@Aspect
@Component
public class AuthInterceptor {
    
    @Resource
    private UserService userService;
    
    /**
     * 执行拦截
     *
     * @param joinPoint 切入点
     * @param authCheck 权限校验注解
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前登录用户
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        // 不需要权限，放行
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }
        // 以下为：必须有该权限才通过
        // 获取当前用户具有的权限
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        // 没有权限，拒绝
        if (userRoleEnum == null) {
            throw new BusinessException(StatusCode.NO_AUTH);
        }
        // 要求必须有管理员权限，但用户没有管理员权限，拒绝
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum) && !UserRoleEnum.ADMIN.equals(userRoleEnum)) {
            throw new BusinessException(StatusCode.NO_AUTH);
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

