package com.biwork.mapper;

import com.biwork.entity.EthTransfer;

public interface EthTransferMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EthTransfer record);

    int insertSelective(EthTransfer record);

    EthTransfer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EthTransfer record);

    int updateByPrimaryKeyWithBLOBs(EthTransfer record);

    int updateByPrimaryKey(EthTransfer record);
}