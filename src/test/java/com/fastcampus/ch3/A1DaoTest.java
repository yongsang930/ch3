package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class) // ac를 자동으로 만들어줌
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class A1DaoTest {
    @Autowired
    A1Dao a1Dao;

    @Autowired
    DataSource ds;

    @Autowired
    DataSourceTransactionManager tm;

    @Test
    public void Testinsert() throws Exception {
        // TxManager를 생성
        // PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        // ㄴ root-context에서 정의해주고 @Autowired로 연결하기 때문에 적지 않아도 된다.
        // <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        //        <property name="dataSource" ref="dataSource"/>
        //    </bean>
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());

        try {
            a1Dao.deleteAll();
            // Tx 시작
            a1Dao.insert(1, 100);
            a1Dao.insert(2, 200);
            tm.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            tm.rollback(status);
        } finally {
        }
    }
}