package com.kevin.mapper;

import com.kevin.domain.SysDict;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;


//@org.apache.ibatis.annotations.Mapper
public interface SysDictMapper extends Mapper<SysDict> {

    @Select("select * from sys_dict where id = #{id}")
    SysDict myfindById(@Param("id") int state);

}