package com.biwork.mapper;

import com.biwork.entity.PaymentOrder;
import com.biwork.entity.PaymentOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PaymentOrderMapper {
    int countByExample(PaymentOrderExample example);

    @Delete({
        "delete from payment_order",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into payment_order (id, user_id, ",
        "payment_id, payment_name, ",
        "payment_money, payment_unit, ",
        "payment_discount, actual_money, ",
        "user_coin_address, receive_coin_address, ",
        "payment_time, insert_time, ",
        "state, order_no)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{paymentId,jdbcType=INTEGER}, #{paymentName,jdbcType=VARCHAR}, ",
        "#{paymentMoney,jdbcType=DECIMAL}, #{paymentUnit,jdbcType=VARCHAR}, ",
        "#{paymentDiscount,jdbcType=DECIMAL}, #{actualMoney,jdbcType=DECIMAL}, ",
        "#{userCoinAddress,jdbcType=VARCHAR}, #{receiveCoinAddress,jdbcType=VARCHAR}, ",
        "#{paymentTime,jdbcType=TIMESTAMP}, #{insertTime,jdbcType=TIMESTAMP}, ",
        "#{state,jdbcType=INTEGER}, #{orderNo,jdbcType=VARCHAR})"
    })
    int insert(PaymentOrder record);

    int insertSelective(PaymentOrder record);

    List<PaymentOrder> selectByExample(PaymentOrderExample example);

    @Select({
        "select",
        "id, user_id, payment_id, payment_name, payment_money, payment_unit, payment_discount, ",
        "actual_money, user_coin_address, receive_coin_address, payment_time, insert_time, ",
        "state, order_no",
        "from payment_order",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    PaymentOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PaymentOrder record, @Param("example") PaymentOrderExample example);

    int updateByExample(@Param("record") PaymentOrder record, @Param("example") PaymentOrderExample example);

    int updateByPrimaryKeySelective(PaymentOrder record);

    @Update({
        "update payment_order",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "payment_id = #{paymentId,jdbcType=INTEGER},",
          "payment_name = #{paymentName,jdbcType=VARCHAR},",
          "payment_money = #{paymentMoney,jdbcType=DECIMAL},",
          "payment_unit = #{paymentUnit,jdbcType=VARCHAR},",
          "payment_discount = #{paymentDiscount,jdbcType=DECIMAL},",
          "actual_money = #{actualMoney,jdbcType=DECIMAL},",
          "user_coin_address = #{userCoinAddress,jdbcType=VARCHAR},",
          "receive_coin_address = #{receiveCoinAddress,jdbcType=VARCHAR},",
          "payment_time = #{paymentTime,jdbcType=TIMESTAMP},",
          "insert_time = #{insertTime,jdbcType=TIMESTAMP},",
          "state = #{state,jdbcType=INTEGER},",
          "order_no = #{orderNo,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PaymentOrder record);
}