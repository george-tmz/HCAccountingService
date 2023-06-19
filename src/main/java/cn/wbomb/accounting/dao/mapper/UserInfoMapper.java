package cn.wbomb.accounting.dao.mapper;

import cn.wbomb.accounting.model.persistence.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author George
 */
@Mapper
public interface UserInfoMapper {
    /**
     * Get user info by user id
     * @param id user id
     * @return UserInfo 对象
     */
    @Select("SELECT id,username,password,create_time,update_time FROM hcas_user WHERE id = #{id}")
    UserInfo getUserInfoByUserId(@Param("id") Long id);
}
