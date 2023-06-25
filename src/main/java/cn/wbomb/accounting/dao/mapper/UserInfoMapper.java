package cn.wbomb.accounting.dao.mapper;

import cn.wbomb.accounting.model.persistence.UserInfo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * User information Mapper.
 *
 * @author George
 */
@Mapper
public interface UserInfoMapper {
    /**
     * Get user info by user id.
     *
     * @param id user id
     * @return UserInfo 对象
     */
    @Select("SELECT id,username,password,create_time,update_time FROM hcas_user WHERE id = #{id}")
    UserInfo getUserInfoByUserId(@Param("id") Long id);

    @Select("SELECT id,username,password,salt,create_time,update_time FROM hcas_user WHERE username = #{username}")
    UserInfo getUserInfoByUsername(@Param("username") String username);

    @Insert("INSERT hcas_user (username, password, salt, create_time) "
        + "VALUES (#{username}, #{password}, #{salt}, #{createTime})")
    void createNewUser(UserInfo userInfo);
}
