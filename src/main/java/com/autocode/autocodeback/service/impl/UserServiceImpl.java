package com.autocode.autocodeback.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.autocode.autocodeback.common.BaseResponse;
import com.autocode.autocodeback.common.ResultUtils;
import com.autocode.autocodeback.common.StatusCode;
import com.autocode.autocodeback.exception.BusinessException;
import com.autocode.autocodeback.mapper.UserMapper;
import com.autocode.autocodeback.model.dto.UserLoginDTO;
import com.autocode.autocodeback.model.dto.UserQueryDTO;
import com.autocode.autocodeback.model.dto.UserRegisterDTO;
import com.autocode.autocodeback.model.entity.User;
import com.autocode.autocodeback.model.enums.UserRoleEnum;
import com.autocode.autocodeback.model.vo.UserGetVO;
import com.autocode.autocodeback.model.vo.UserVO;
import com.autocode.autocodeback.service.UserService;
import com.autocode.autocodeback.utils.BCryptPasswordEncoder;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.autocode.autocodeback.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务层实现
 * <p>
 * 提供用户注册、登录、注销、查询等核心业务逻辑的实现。
 * 密码使用 BCrypt 加密存储，登录态通过 HttpSession 管理。
 * </p>
 *
 * @author Jack
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * {@inheritDoc}
     * <p>
     * 注册流程：参数校验 → 长度校验 → 密码一致性校验 → 账号唯一性校验 → 密码加密 → 入库。
     * </p>
     */
    @Override
    public BaseResponse<Boolean> register(UserRegisterDTO userRegisterDTO) {
        // 1. 空值校验
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        String confirmPassword = userRegisterDTO.getConfirmPassword();
        if (StrUtil.hasBlank(username, password, confirmPassword)) {
            return ResultUtils.error(StatusCode.PARAMS_REGISTER_NULL);
        }
        
        // 2. 账号长度校验（至少 4 位）
        if (username.length() < 4) {
            return ResultUtils.error(StatusCode.BAD_USERNAME_LENGTH);
        }
        
        // 3. 密码长度校验（至少 8 位）
        if (password.length() < 8 || confirmPassword.length() < 8) {
            return ResultUtils.error(StatusCode.BAD_PASSWORD_LENGTH);
        }
        
        // 4. 两次输入的密码是否一致
        if (!password.equals(confirmPassword)) {
            return ResultUtils.error(StatusCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        
        // 5. 账号唯一性校验
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        long count = userMapper.selectCountByQuery(queryWrapper);
        if (count > 0) {
            return ResultUtils.error(StatusCode.DATA_USERNAME_DUPLICATE);
        }
        
        // 6. 密码加密
        String encodePassword = BCryptPasswordEncoder.encode(password);
        
        // 7. 构建用户对象并入库
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodePassword);
        user.setNickName(generateFancyNickname());
        user.setUserRole(UserRoleEnum.USER.getValue());
        
        boolean save = this.save(user);
        if (save) {
            return ResultUtils.success(StatusCode.SUCCESS_REGISTER, save);
        }
        return ResultUtils.error(StatusCode.REGISTER_ERROR);
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * 登录流程：参数校验 → 查询用户 → 密码比对 → 写入 Session。
     * </p>
     */
    @Override
    public BaseResponse<Long> login(UserLoginDTO userLoginDTO, HttpServletRequest request) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        
        // 1. 参数校验
        if (StrUtil.hasBlank(username, password)) {
            return ResultUtils.error(StatusCode.PARAMS_REGISTER_NULL);
        }
        
        // 2. 根据账号查询用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOneByQuery(queryWrapper);
        if (user == null) {
            return ResultUtils.error(StatusCode.NOT_FOUND_USER);
        }
        
        // 3. 密码比对，成功则写入 Session
        boolean matches = BCryptPasswordEncoder.matches(password, user.getPassword());
        if (matches) {
            request.getSession().setAttribute(USER_LOGIN_STATE, user.getId());
            return ResultUtils.success(StatusCode.SUCCESS_LOGIN);
        }
        return ResultUtils.error(StatusCode.PARAMS_LOGIN_ERROR);
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * 从 Session 中获取用户 ID，查询数据库并返回脱敏后的用户信息。
     * </p>
     */
    @Override
    public BaseResponse<UserGetVO> selectUser(HttpServletRequest request) {
        Long attribute = (Long) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (attribute == null) {
            return ResultUtils.error(StatusCode.NOT_LOGIN);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", attribute);
        User user = userMapper.selectOneByQuery(queryWrapper);
        if (user == null) {
            return ResultUtils.error(StatusCode.NOT_FOUND_USER);
        }
        UserGetVO userGetVO = new UserGetVO();
        userGetVO.setUsername(user.getUsername());
        userGetVO.setNickName(user.getNickName());
        userGetVO.setUserAvatar(user.getUserAvatar());
        userGetVO.setUserProfile(user.getUserProfile());
        userGetVO.setUserRole(user.getUserRole());
        return ResultUtils.success(StatusCode.SUCCESS_GET_USER, userGetVO);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (attribute == null) {
            return ResultUtils.error(StatusCode.NOT_LOGIN);
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return ResultUtils.success(StatusCode.SUCCESS_LOGOUT);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        Long attribute = (Long) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (attribute == null) {
            return null;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", attribute);
        return userMapper.selectOneByQuery(queryWrapper);
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * 根据查询条件动态构建 MyBatis-Flex 查询包装器，
     * 支持按 ID、角色精确查询，按账号、昵称、简介模糊查询，以及排序。
     * </p>
     */
    @Override
    public QueryWrapper getQueryWrapper(UserQueryDTO userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        return QueryWrapper.create()
                .eq("id", id)
                .eq("user_role", userRole)
                .like("username", userAccount)
                .like("nick_name", userName)
                .like("user_profile", userProfile)
                .orderBy(sortField, "ascend".equals(sortOrder));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }
    
    /**
     * 生成随机昵称
     * <p>
     * 使用 UUID 前 8 位拼接固定前缀 "AutoCode" 作为新用户的默认昵称。
     * </p>
     *
     * @return 随机生成的昵称，格式如 "AutoCode3a7b2c1d"
     */
    public String generateFancyNickname() {
        String shortUuid = UUID.randomUUID().toString().substring(0, 8);
        return "AutoCode" + shortUuid;
    }
}
