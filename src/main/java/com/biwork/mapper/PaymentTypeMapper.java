package com.biwork.mapper;

import com.biwork.entity.PaymentType;
import com.biwork.entity.PaymentTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PaymentTypeMapper {
    @Delete({
        "delete from payment_type",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into payment_type (id, name, ",
        "money, unit, discount, ",
        "state, insert_time)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{money,jdbcType=DECIMAL}, #{unit,jdbcType=VARCHAR}, #{discount,jdbcType=DECIMAL}, ",
        "#{state,jdbcType=INTEGER}, #{insertTime,jdbcType=TIMESTAMP})"
    })
    int insert(PaymentType record);

    int insertSelective(PaymentType record);

    List<PaymentType> selectByExample(PaymentTypeExample example);

    @Select({
        "select",
        "id, name, money, unit, discount, state, insert_time",
        "from payment_type",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    PaymentType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PaymentType record);

    @Update({
        "update payment_type",
        "set name = #{name,jdbcType=VARCHAR},",
          "money = #{money,jdbcType=DECIMAL},",
          "unit = #{unit,jdbcType=VARCHAR},",
          "discount = #{discount,jdbcType=DECIMAL},",
          "state = #{state,jdbcType=INTEGER},",
          "insert_time = #{insertTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PaymentType record);
}