package cn.wbomb.accounting.dao.mapper;

import cn.wbomb.accounting.model.persistence.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT id,username,password,create_time,update_time FROM hcas_user WHERE id = #{id}")
    public UserInfo getUserInfoByUserId(@Param("id") Long id);
}
