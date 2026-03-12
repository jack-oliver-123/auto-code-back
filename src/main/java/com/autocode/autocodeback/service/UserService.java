package com.autocode.autocodeback.service;

import com.autocode.autocodeback.common.BaseResponse;
import com.autocode.autocodeback.model.dto.UserLoginDTO;
import com.autocode.autocodeback.model.dto.UserQueryDTO;
import com.autocode.autocodeback.model.dto.UserRegisterDTO;
import com.autocode.autocodeback.model.entity.User;
import com.autocode.autocodeback.model.vo.UserGetVO;
import com.autocode.autocodeback.model.vo.UserVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户服务层接口
 * <p>
 * 定义用户相关的核心业务方法，包括注册、登录、注销、
 * 查询当前登录用户以及用户数据的转换与查询构建等。
 * </p>
 *
 * @author Jack
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户注册
     *
     * @param userRegisterDTO 注册请求参数（账号、密码、确认密码）
     * @return 注册是否成功
     */
    BaseResponse<Boolean> register(UserRegisterDTO userRegisterDTO);
    
    /**
     * 用户登录
     *
     * @param userLoginDTO 登录请求参数（账号、密码）
     * @param request      HTTP 请求，用于创建登录 Session
     * @return 登录成功的状态信息
     */
    BaseResponse<Long> login(UserLoginDTO userLoginDTO, HttpServletRequest request);
    
    /**
     * 获取当前登录用户的脱敏信息
     *
     * @param request HTTP 请求，用于读取 Session
     * @return 当前登录用户的脱敏 VO
     */
    BaseResponse<UserGetVO> selectUser(HttpServletRequest request);
    
    /**
     * 用户注销（退出登录）
     *
     * @param request HTTP 请求，用于清除 Session
     * @return 注销是否成功
     */
    BaseResponse<Boolean> logout(HttpServletRequest request);
    
    /**
     * 获取当前登录用户实体（内部使用）
     *
     * @param request HTTP 请求
     * @return 登录用户实体，未登录则返回 null
     */
    User getLoginUser(HttpServletRequest request);
    
    /**
     * 将用户实体转换为脱敏 VO
     *
     * @param user 用户实体
     * @return 脱敏后的用户 VO
     */
    UserVO getUserVO(User user);
    
    /**
     * 根据查询条件构建 MyBatis-Flex 查询包装器
     *
     * @param userQueryRequest 查询请求参数
     * @return 查询包装器
     */
    QueryWrapper getQueryWrapper(UserQueryDTO userQueryRequest);
    
    /**
     * 批量将用户实体列表转换为脱敏 VO 列表
     *
     * @param records 用户实体列表
     * @return 脱敏后的用户 VO 列表
     */
    List<UserVO> getUserVOList(List<User> records);
}

