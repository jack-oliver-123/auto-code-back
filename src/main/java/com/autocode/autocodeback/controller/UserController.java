package com.autocode.autocodeback.controller;

import cn.hutool.core.bean.BeanUtil;
import com.autocode.autocodeback.annotation.AuthCheck;
import com.autocode.autocodeback.common.BaseResponse;
import com.autocode.autocodeback.common.DeleteRequest;
import com.autocode.autocodeback.common.ResultUtils;
import com.autocode.autocodeback.common.StatusCode;
import com.autocode.autocodeback.constant.UserConstant;
import com.autocode.autocodeback.exception.BusinessException;
import com.autocode.autocodeback.exception.ThrowUtils;
import com.autocode.autocodeback.model.dto.*;
import com.autocode.autocodeback.model.entity.User;
import com.autocode.autocodeback.model.vo.UserGetVO;
import com.autocode.autocodeback.model.vo.UserVO;
import com.autocode.autocodeback.service.UserService;
import com.autocode.autocodeback.utils.BCryptPasswordEncoder;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理 控制层
 * <p>
 * 提供用户注册、登录、注销、查询、新增、修改、删除等 RESTful 接口。
 * 部分接口使用 {@link AuthCheck} 注解进行权限校验，仅管理员可访问。
 * </p>
 *
 * @author Jack
 */
@Tag(name = "用户管理", description = "用户注册/登录/增删改查等接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterDTO 注册请求参数（账号、密码、确认密码）
     * @return 注册是否成功
     */
    @Operation(summary = "用户注册", description = "通过账号和密码注册新用户")
    @PostMapping("/register")
    public BaseResponse<Boolean> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.register(userRegisterDTO);
    }

    /**
     * 用户登录
     * <p>
     * 验证账号密码，成功后将用户 ID 写入 Session。
     * </p>
     *
     * @param userLoginDTO 登录请求参数（账号、密码）
     * @param request      HTTP 请求（用于操作 Session）
     * @return 登录成功的用户 ID
     */
    @Operation(summary = "用户登录", description = "验证账号密码并创建登录会话")
    @PostMapping("/login")
    public BaseResponse<Long> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        return userService.login(userLoginDTO, request);
    }

    /**
     * 获取当前登录用户信息
     *
     * @param request HTTP 请求（用于读取 Session）
     * @return 当前登录用户的脱敏信息
     */
    @Operation(summary = "获取当前登录用户", description = "根据 Session 获取当前登录用户的脱敏信息")
    @GetMapping("/select")
    public BaseResponse<UserGetVO> selectUser(HttpServletRequest request) {
        return userService.selectUser(request);
    }

    /**
     * 用户注销（退出登录）
     *
     * @param request HTTP 请求（用于清除 Session）
     * @return 注销是否成功
     */
    @Operation(summary = "用户注销", description = "清除登录会话")
    @DeleteMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    /**
     * 创建用户（仅管理员）
     * <p>
     * 管理员手动添加用户，新用户密码默认为 12345678。
     * </p>
     *
     * @param userAddRequest 添加用户请求参数
     * @return 新用户的 ID
     */
    @Operation(summary = "添加用户", description = "管理员手动创建用户，默认密码 12345678")
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody UserAddDTO userAddRequest) {
        ThrowUtils.throwIf(userAddRequest == null, StatusCode.PARAMS_ERROR);
        User user = new User();
        BeanUtil.copyProperties(userAddRequest, user);
        // 默认密码 12345678
        final String DEFAULT_PASSWORD = "12345678";
        String encryptPassword = BCryptPasswordEncoder.encode(DEFAULT_PASSWORD);
        user.setPassword(encryptPassword);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, StatusCode.BAD_ADD_USER);
        return ResultUtils.success(user.getId());
    }

    /**
     * 根据 ID 获取用户完整信息（仅管理员）
     *
     * @param id 用户 ID
     * @return 用户完整信息（含敏感字段）
     */
    @Operation(summary = "根据 ID 获取用户", description = "仅管理员可用，返回完整用户信息")
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(@Parameter(description = "用户 ID", required = true) long id) {
        ThrowUtils.throwIf(id <= 0, StatusCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, StatusCode.NOT_FOUND_USER);
        return ResultUtils.success(user);
    }

    /**
     * 根据 ID 获取用户脱敏信息（VO）
     *
     * @param id 用户 ID
     * @return 脱敏后的用户信息
     */
    @Operation(summary = "根据 ID 获取用户 VO", description = "返回脱敏后的用户信息")
    @GetMapping("/get/vo")
    public BaseResponse<UserVO> getUserVOById(@Parameter(description = "用户 ID", required = true) long id) {
        BaseResponse<User> response = getUserById(id);
        User user = response.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }

    /**
     * 删除用户（仅管理员）
     *
     * @param deleteRequest 删除请求（包含用户 ID）
     * @return 删除是否成功
     */
    @Operation(summary = "删除用户", description = "仅管理员可用，逻辑删除指定用户")
    @DeleteMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(StatusCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新用户信息（仅管理员）
     *
     * @param userUpdateRequest 更新请求参数
     * @return 更新是否成功
     */
    @Operation(summary = "更新用户", description = "仅管理员可用，更新指定用户信息")
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateDTO userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtil.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, StatusCode.BAD_UPDATE_USER);
        return ResultUtils.success(true);
    }

    /**
     * 分页获取用户封装列表（仅管理员）
     * <p>
     * 支持按用户名、角色等条件筛选，返回脱敏后的用户信息分页列表。
     * </p>
     *
     * @param userQueryRequest 分页查询请求参数
     * @return 用户 VO 分页列表
     */
    @Operation(summary = "分页获取用户列表", description = "仅管理员可用，返回脱敏后的用户分页列表")
    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryDTO userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, StatusCode.PARAMS_ERROR);
        long pageNum = userQueryRequest.getPageNum();
        long pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(Page.of(pageNum, pageSize),
                userService.getQueryWrapper(userQueryRequest));
        // 数据脱敏
        Page<UserVO> userVOPage = new Page<>(pageNum, pageSize, userPage.getTotalRow());
        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }
}
