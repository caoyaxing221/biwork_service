package com.biwork.service.Impl;

import com.biwork.entity.TxLog;
import com.biwork.service.TxLogService;
import com.biwork.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("TxLogService")
public class TxLogServiceImpl implements TxLogService {
    static Logger log = LoggerFactory.getLogger(TxLogService.class);
    
	@Override
	public TxLog getEthTxLog(String query) throws Exception {
		TxLog txl = new TxLog();
       
        String rsp = HttpUtil.testGet(query);
        txl.setTxLog(rsp);
  	
		return txl;
    }
    
    @Override
	public TxLog getErc20TxLog(String query) throws Exception {
		TxLog txl = new TxLog();
       
        String rsp = HttpUtil.testGet(query);
        txl.setTxLog(rsp);
  	
		return txl;
	}

	@Override
	public TxLog getBtcTxLog(String query) throws Exception {
		TxLog txl = new TxLog();
       
        String rsp = HttpUtil.testGet(query);
        txl.setTxLog(rsp);
  	
		return txl;
	}
}
