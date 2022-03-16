package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxService {

    @Autowired A1Dao a1dao;
    @Autowired B1Dao b1dao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertA1WithTx()throws Exception {
        a1dao.insert(1,100);
        insertB1WithTx();
        a1dao.insert(1,200);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertB1WithTx()throws Exception {
        b1dao.insert(1,100);
        b1dao.insert(2,200);
    }

    public void insertA1WithoutTx() throws Exception {
        a1dao.insert(1, 100);
        a1dao.insert(1, 200);
    }
//  @Transactional은 RunTimeException, Error만 Rollback
    @Transactional(rollbackFor = Exception.class)
    public void insertA1WithtTxFail() throws Exception {
        a1dao.insert(1, 100);
        a1dao.insert(1, 200);
    }

    @Transactional
    public void insertA1WithtTxSucsess() throws Exception {
        a1dao.insert(1, 100);
        a1dao.insert(2, 200);
    }

}
