package com.biwork.mapper;

import com.biwork.entity.Configuration;
import com.biwork.entity.ConfigurationExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ConfigurationMapper {
    int countByExample(ConfigurationExample example);

    int deleteByExample(ConfigurationExample example);

    @Delete({
        "delete from configuration",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into configuration (id, name, ",
        "value, state, inserttime)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{value,jdbcType=VARCHAR}, #{state,jdbcType=INTEGER}, #{inserttime,jdbcType=TIMESTAMP})"
    })
    int insert(Configuration record);

    int insertSelective(Configuration record);

    List<Configuration> selectByExample(ConfigurationExample example);

    @Select({
        "select",
        "id, name, value, state, inserttime",
        "from configuration",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    Configuration selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Configuration record, @Param("example") ConfigurationExample example);

    int updateByExample(@Param("record") Configuration record, @Param("example") ConfigurationExample example);

    int updateByPrimaryKeySelective(Configuration record);

    @Update({
        "update configuration",
        "set name = #{name,jdbcType=VARCHAR},",
          "value = #{value,jdbcType=VARCHAR},",
          "state = #{state,jdbcType=INTEGER},",
          "inserttime = #{inserttime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Configuration record);
}