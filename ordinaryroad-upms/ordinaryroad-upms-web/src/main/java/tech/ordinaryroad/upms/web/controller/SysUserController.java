/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package tech.ordinaryroad.upms.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.entity.SysUserDO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;
import tech.ordinaryroad.upms.mapstruct.SysUserMapStruct;
import tech.ordinaryroad.upms.request.*;
import tech.ordinaryroad.upms.service.SysUserService;
import tech.ordinaryroad.upms.service.SysUsersRolesService;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/10/27
 */
@RequiredArgsConstructor
@RestController
public class SysUserController implements ISysUserApi {

    private final SysUserService sysUserService;
    private final SysUsersRolesService sysUsersRolesService;
    private final SysUserMapStruct objMapStruct;
    private final PasswordEncoder passwordEncoder;
    private final Pattern passwordPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$");

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<SysUserDTO> create(@RequestBody @Validated SysUserSaveRequest request) {

        Result<SysUserDTO> validResult = checkValid(request);
        if (validResult != null) {
            return validResult;
        }

        SysUserDO sysUserDO = objMapStruct.transfer(request);

        Result<SysUserDTO> fail = validatePassword(sysUserDO);
        if (fail != null) {
            return fail;
        }

        return Result.success(objMapStruct.transfer(sysUserService.createSelective(sysUserDO)));
    }

    @Override
    public Result<SysUserDTO> findById(@RequestBody SysUserQueryRequest request) {
        SysUserDO sysUserDO = objMapStruct.transfer(request);
        SysUserDO byId = sysUserService.findById(sysUserDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<List<SysUserDTO>> findAll(@RequestBody SysUserQueryRequest request) {
        SysUserDO sysUserDO = objMapStruct.transfer(request);

        List<SysUserDO> all = sysUserService.findAll(sysUserDO, request);
        List<SysUserDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<SysUserDTO>> list(@RequestBody SysUserQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysUserDO sysUserDO = objMapStruct.transfer(request);
        Page<SysUserDO> all = (Page<SysUserDO>) sysUserService.findAll(sysUserDO, request);

        PageInfo<SysUserDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

    @Override
    public Result<SysUserDTO> update(@RequestBody @Validated SysUserSaveRequest request) {
        SysUserDO sysUserDO = objMapStruct.transfer(request);
        // 不允许更新密码
        sysUserDO.setPassword(null);
        // 不允许更新邮箱
        sysUserDO.setEmail(null);
        return Result.success(objMapStruct.transfer(sysUserService.updateSelective(sysUserDO)));
    }

    @Override
    public Result<Boolean> delete(@RequestBody @Validated BaseDeleteRequest request) {
        return Result.success(sysUserService.delete(request.getUuid()));
    }

    @Override
    public Result<SysUserDTO> findByUniqueColumn(@RequestBody SysUserQueryRequest request) {
        Optional<SysUserDO> optional = Optional.empty();
        String orNumber = request.getOrNumber();
        String email = request.getEmail();
        String username = request.getUsername();
        if (StrUtil.isNotBlank(orNumber)) {
            optional = sysUserService.findByOrNumber(orNumber);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(email)) {
            optional = sysUserService.findByEmail(email);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(username)) {
            optional = sysUserService.findByUsername(username);
        }

        return optional.map(data -> Result.success(objMapStruct.transfer(data))).orElseGet(Result::fail);
    }

    @Override
    public Mono<Result<SysUserDTO>> findByUniqueColumnAsync(@RequestBody SysUserQueryRequest request) {
        return Mono.just(this.findByUniqueColumn(request));
    }

    @Override
    public Result<Boolean> updateAvatar(@RequestBody @Validated SysUserUpdateAvatarRequest request) {
        // 获取当前登录用户
        String orNumber = StpUtil.getLoginIdAsString();
        Optional<SysUserDO> byOrNumber = sysUserService.findByOrNumber(orNumber);
        if (!byOrNumber.isPresent()) {
            return Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        }
        String newAvatar = request.getAvatar();
        SysUserDO sysUserDO = byOrNumber.get();
        String avatar = sysUserDO.getAvatar();
        if (newAvatar.equals(avatar)) {
            return Result.success(false);
        }

        SysUserDO newSysUserDO = new SysUserDO();
        newSysUserDO.setUuid(sysUserDO.getUuid());
        newSysUserDO.setAvatar(StrUtil.blankToDefault(newAvatar, ""));
        if (sysUserService.doUpdateSelective(newSysUserDO)) {
            return Result.success(true);
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result<Boolean> updateUsername(@RequestBody @Validated SysUserUpdateUsernameRequest request) {
        // 获取当前登录用户
        String orNumber = StpUtil.getLoginIdAsString();
        Optional<SysUserDO> byOrNumber = sysUserService.findByOrNumber(orNumber);
        if (!byOrNumber.isPresent()) {
            return Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        }
        String newUsername = request.getUsername();
        SysUserDO sysUserDO = byOrNumber.get();
        String username = sysUserDO.getUsername();
        if (newUsername.equals(username)) {
            return Result.success(false);
        }

        Optional<SysUserDO> byUsername = sysUserService.findByUsername(newUsername);
        if (byUsername.isPresent()) {
            return Result.fail(StatusCode.USERNAME_ALREADY_EXIST);
        }

        SysUserDO newSysUserDO = new SysUserDO();
        newSysUserDO.setUuid(sysUserDO.getUuid());
        newSysUserDO.setUsername(newUsername);
        if (sysUserService.doUpdateSelective(newSysUserDO)) {
            return Result.success(true);
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result<Boolean> updateEmail(@RequestBody @Validated SysUserUpdateEmailRequest request) {
        // 获取当前登录用户
        String orNumber = StpUtil.getLoginIdAsString();
        Optional<SysUserDO> byOrNumber = sysUserService.findByOrNumber(orNumber);
        if (!byOrNumber.isPresent()) {
            return Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        }
        String newEmail = request.getEmail();
        SysUserDO sysUserDO = byOrNumber.get();
        String email = sysUserDO.getEmail();
        if (newEmail.equals(email)) {
            return Result.success(false);
        }

        Optional<SysUserDO> byEmail = sysUserService.findByEmail(newEmail);
        if (byEmail.isPresent()) {
            return Result.fail(StatusCode.EMAIL_ALREADY_EXIST);
        }

        SysUserDO newSysUserDO = new SysUserDO();
        newSysUserDO.setUuid(sysUserDO.getUuid());
        newSysUserDO.setEmail(newEmail);
        if (sysUserService.doUpdateSelective(newSysUserDO)) {
            return Result.success(true);
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result<Boolean> updatePassword(@RequestBody @Validated SysUserUpdatePasswordRequest request) {
        // 获取当前登录用户
        String orNumber = StpUtil.getLoginIdAsString();
        Optional<SysUserDO> byOrNumber = sysUserService.findByOrNumber(orNumber);
        if (!byOrNumber.isPresent()) {
            return Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        }
        // 新旧密码是否相同
        String newPassword = request.getNewPassword();
        SysUserDO sysUserDO = byOrNumber.get();
        String password = sysUserDO.getPassword();
        if (password.equals(newPassword)) {
            Result.success(false);
        }

        // 旧密码是否匹配
        if (!passwordEncoder.matches(request.getPassword(), password)) {
            return Result.fail(StatusCode.USER_CREDENTIALS_ERROR);
        }

        SysUserDO newSysUserDO = new SysUserDO();
        newSysUserDO.setUuid(sysUserDO.getUuid());
        // 密码加密
        newSysUserDO.setPassword(passwordEncoder.encode(newPassword));
        return Result.success(sysUserService.doUpdateSelective(newSysUserDO));
    }

    @Override
    public Result<SysUserDTO> register(@Validated @RequestBody SysUserRegisterRequest request) {
        // 校验邮箱
        String email = request.getEmail();
        Optional<SysUserDO> byEmail = sysUserService.findByEmail(email);
        if (byEmail.isPresent()) {
            return Result.fail(StatusCode.EMAIL_ALREADY_EXIST);
        }
        SysUserDO sysUserDO = objMapStruct.transfer(request);
        // 密码加密
        sysUserDO.setPassword(passwordEncoder.encode(request.getPassword()));
        return Result.success(objMapStruct.transfer(sysUserService.createSelective(sysUserDO)));
    }

    @Override
    public Result<?> resetPassword(@Validated @RequestBody SysUserResetPasswordRequest request) {
        SysUserDO byUuid = sysUserService.findById(request.getUuid());
        if (Objects.isNull(byUuid)) {
            return Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        }

        SysUserDO sysUserDO = objMapStruct.transfer(request);
        // 密码加密
        sysUserDO.setPassword(passwordEncoder.encode(sysUserDO.getPassword()));
        sysUserService.updateSelective(sysUserDO);
        // 重置密码后强制下线
        StpUtil.kickout(byUuid.getOrNumber());
        return Result.success();
    }

    @Override
    public Result<?> updateEnabled(@Validated @RequestBody SysUserUpdateEnabledRequest request) {
        SysUserDO byUuid = sysUserService.findById(request.getUuid());
        if (Objects.isNull(byUuid)) {
            return Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        }

        SysUserDO sysUserDO = objMapStruct.transfer(request);
        sysUserService.updateSelective(sysUserDO);

        String orNumber = byUuid.getOrNumber();
        if (request.getEnabled()) {
            StpUtil.untieDisable(orNumber);
        } else {
            StpUtil.disable(orNumber, -1L);
        }

        return Result.success();
    }

    @Override
    public Result<List<SysUserDTO>> findAllByForeignColumn(@RequestBody SysUserQueryRequest request) {
        List<SysUserDO> all = Collections.emptyList();
        String roleUuid = request.getRoleUuid();
        if (StrUtil.isNotBlank(roleUuid)) {
            all = sysUserService.findAllByRoleUuid(roleUuid);
        }
        List<SysUserDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());
        return Result.success(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> updateUserRoles(@RequestBody @Validated SysUserRolesSaveRequest request) {
        String uuid = request.getUuid();

        // 最新的角色uuids
        List<String> roleUuids = request.getRoleUuids();

        // 本地的角色uuids
        List<SysUsersRolesDO> allByUserUuid = sysUsersRolesService.findAllByUserUuid(uuid);

        // 需要新增的用户角色关联关系实体类
        List<SysUsersRolesDO> needInsertList = new ArrayList<>();
        // 需要删除的用户角色关联关系uuids
        List<String> needDeleteList = new ArrayList<>();

        roleUuids.forEach(roleUuid -> {
            // 判断是否需要新增
            boolean find = false;
            for (SysUsersRolesDO sysUsersRolesDO : allByUserUuid) {
                if (sysUsersRolesDO.getRoleUuid().equals(roleUuid)) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                SysUsersRolesDO sysUsersRolesDO = new SysUsersRolesDO();
                sysUsersRolesDO.setUserUuid(uuid);
                sysUsersRolesDO.setRoleUuid(roleUuid);
                needInsertList.add(sysUsersRolesDO);
            }
        });
        allByUserUuid.forEach(sysUsersRolesDO -> {
            // 最新的不存在，需要删除的
            if (!roleUuids.contains(sysUsersRolesDO.getRoleUuid())) {
                needDeleteList.add(sysUsersRolesDO.getUuid());
            }
        });

        if (CollUtil.isEmpty(needDeleteList) && CollUtil.isEmpty(needInsertList)) {
            return Result.success(Boolean.FALSE);
        } else {
            if (CollUtil.isNotEmpty(needDeleteList)) {
                sysUsersRolesService.deleteByIdList(needDeleteList);
            }
            if (CollUtil.isNotEmpty(needInsertList)) {
                sysUsersRolesService.insertList(needInsertList);
            }
        }

        return Result.success(true);
    }

    @Nullable
    private Result<SysUserDTO> checkValid(SysUserSaveRequest request) {
        // 校验邮箱
        String email = request.getEmail();
        Optional<SysUserDO> byEmail = sysUserService.findByEmail(email);
        if (byEmail.isPresent()) {
            return Result.fail(StatusCode.EMAIL_ALREADY_EXIST);
        }
        // 校验用户名
        String username = request.getUsername();
        if (StrUtil.isNotBlank(username)) {
            Optional<SysUserDO> byName = sysUserService.findByUsername(username);
            if (byName.isPresent()) {
                return Result.fail(StatusCode.USERNAME_ALREADY_EXIST);
            }
        }
        return null;
    }

    /**
     * 校验密码是否合法
     *
     * @param sysUserDO SysUserDO
     * @return 密码合法返回null
     */
    @Nullable
    private Result<SysUserDTO> validatePassword(@NotNull SysUserDO sysUserDO) {
        String password = sysUserDO.getPassword();
        if (StrUtil.isBlank(password)) {
            // 默认密码Abc123
            sysUserDO.setPassword(passwordEncoder.encode("Abc123"));
        } else {
            if (StrUtil.length(password) < 6 || StrUtil.length(password) > 16) {
                return Result.fail("密码长度 6-16");
            } else {
                if (!passwordPattern.matcher(password).matches()) {
                    return Result.fail("必须包含大小写字母和数字的组合，可以使用特殊字符");
                } else {
                    sysUserDO.setPassword(passwordEncoder.encode(password));
                }
            }
        }
        return null;
    }
}
