package com.etekcity.cloud.dao.mapper;


import org.apache.ibatis.annotations.*;

import com.etekcity.cloud.domain.User;

/**
 * 用户 DAO 接口类
 *
 * @author vik
 */
@Mapper
public interface UserMapper {

    /**
     * 新建账号，写入数据库
     *
     * @param user
     * @return id
     */
    @Insert(" insert into vik_user (email,password,create_at,update_at,salt) values(#{email},#{password},#{createAt}" +
            ",#{updateAt},#{salt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long addAcount(User user);

    /**
     * 根据email查询密码和盐值
     *
     * @param email
     * @return user.password
     */
    @Results({
            @Result(property = "createAt", column = "create_at"),
            @Result(property = "updateAt", column = "update_at")
    })
    @Select("select * from vik_user where email = #{email}")
    User selectByEmail(String email);

    /**
     * 根据id查用户信息
     *
     * @param id
     * @return user
     */
    @Results({
            @Result(property = "createAt", column = "create_at"),
            @Result(property = "updateAt", column = "update_at")
    })
    @Select("select * from vik_user where id = #{id}")
    User selectById(long id);

    /**
     * 更新昵称
     *
     * @param user
     * @return int
     */
    @Update("update vik_user set nickname = #{nickname} where id = #{id}")
    int updateNicknameById(String nickname, long id);

    /**
     * 更新地址
     *
     * @param user
     * @return int
     */
    @Update("update vik_user set address = #{address} where id = #{id}")
    int updateAddressById(String address, long id);

    /**
     * 根据id修改密码
     *
     * @param user
     * @return int
     */
    @Update("update vik_user set password = #{password}, salt = #{salt} where id = #{id}")
    int updatePwdById(String password, String salt, long id);
}
