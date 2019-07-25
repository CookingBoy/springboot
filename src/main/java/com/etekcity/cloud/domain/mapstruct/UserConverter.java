package com.etekcity.cloud.domain.mapstruct;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.etekcity.cloud.domain.User;
import com.etekcity.cloud.domain.response.GetUserInfoResponseData;
import com.etekcity.cloud.domain.response.LoginResponseData;
import com.etekcity.cloud.domain.response.RegisterResponseData;

/**
 * DT 转 DTO ，使用 mapstruct
 *
 * @author vik
 */
@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * User实体对象 转 GetUserInfoResponseData 实体对象
     *
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(target = "createAt", expression = "java(com.etekcity.cloud.util.GetFormatTime.toUtcTime(user.getCreateAt()))"),
            @Mapping(target = "updateAt", expression = "java(com.etekcity.cloud.util.GetFormatTime.toUtcTime(user.getUpdateAt()))")
    })
    GetUserInfoResponseData getUserInfoDTO(User user);

    /**
     * User实体对象 转 LoginResponseData实体对象
     *
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(target = "createAt", expression = "java(com.etekcity.cloud.util.GetFormatTime.toUtcTime(user.getCreateAt()))"),
            @Mapping(target = "updateAt", expression = "java(com.etekcity.cloud.util.GetFormatTime.toUtcTime(user.getUpdateAt()))"),
            @Mapping(target = "expiresIn", expression = "java(com.etekcity.cloud.common.Constant.REDIS_EXPIRES_IN)"),
            @Mapping(target = "token", ignore = true)
    })
    LoginResponseData loginDTO(User user);

    /**
     * User实体对象 转 RegisterResponseData实体对象
     *
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(target = "createAt", expression = "java(com.etekcity.cloud.util.GetFormatTime.toUtcTime(user.getUpdateAt()))")
    })
    RegisterResponseData registerDTO(User user);
}
