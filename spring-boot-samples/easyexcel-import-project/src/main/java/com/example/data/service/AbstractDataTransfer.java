package com.example.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractDataTransfer implements DataTransfer {
    private JdbcTemplate jdbcTemplate;

    private DataSourceTransactionManager transactionManager;

    @Autowired
    private ExecutorService executorService;

    protected long start;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public AbstractDataTransfer(JdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionManager = transactionManager;
    }

    protected int[] batchUpdate(String sql, List<Object[]> values) {
        return jdbcTemplate.batchUpdate(sql, values);
    }

    protected int[] batchUpdate(final String... sql) {
        return jdbcTemplate.batchUpdate(sql);
    }

    protected int[] batchUpdate(final List<String> sqls) {
        return jdbcTemplate.batchUpdate(sqls.toArray(new String[0]));
    }

    protected int update(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    protected void execute(String sql) {
        jdbcTemplate.execute(sql);
    }


    protected int batchSizeUpdate(String sqlTemplate, List<Object[]> datas) {
        if (datas.size() <= 0) {
            return 0;
        }
        int batchSize = 1000;
        int start = 0;
        int size = batchSize;
        int num = 0;
        do {
            if (datas.size() - start < size) {
                //不足1000数量，重新计算size
                size = datas.size() - start;
            }
            int[] ints = batchUpdate(datas, sqlTemplate, start, size);
            for (int anInt : ints) {
                num = num + anInt;
            }
            start += size;
        } while (start < datas.size());
        return num;
    }

    protected int[] batchUpdate(final List<Object[]> datas, String sql, final int start, final int size) {
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Object[] objects = datas.get(start + i);
                //设置具体的参数
                for (int j = 1; j <= objects.length; j++) {
                    ps.setObject(j, objects[j - 1]);
                }
            }

            @Override
            public int getBatchSize() {
                return size;
            }
        });
    }

    protected void threadBatchSizeUpdate(String sqlTemplate, List<Object[]> datas) {
        if (datas.size() <= 0) {
            return;
        }
        start = System.currentTimeMillis();
        // 每5000条数据开启一条线程
        int threadSize = 5000;
        // 总数据条数
        int dataSize = datas.size();
        // 线程数
        int threadNum = dataSize / threadSize + 1;
        // 定义标记,过滤threadNum为整数
        boolean special = dataSize % threadSize == 0;
        // 创建一个线程池
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        // 定义一个任务集合
        List<Callable<Integer>> tasks = new ArrayList<>();
        Callable<Integer> task = null;
        List<Object[]> cutList = null;
        // 确定每条线程的数据
        for (int i = 0; i < threadNum; i++) {
            if (i == threadNum - 1) {
                if (special) {
                    break;
                }
                cutList = datas.subList(threadSize * i, dataSize);
            } else {
                cutList = datas.subList(threadSize * i, threadSize * (i + 1));
            }
            final List<Object[]> listStr = cutList;
            task = new BatchUpdateCallable(listStr, sqlTemplate);
            // 这里提交的任务容器列表和返回的Future列表存在顺序对应的关系
            tasks.add(task);
        }
        try {
            log.info("共开启{}个线程", threadNum);
            List<Future<Integer>> results = exec.invokeAll(tasks);
            AtomicInteger atomicInteger = new AtomicInteger(0);
            for (Future<Integer> future : results) {
                Integer integer = future.get();
                atomicInteger.getAndAdd(integer);
            }
            log.info("{}个线程任务执行插入{}数据", threadNum, atomicInteger.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            exec.shutdown();
            log.info("线程任务执行结束");
            log.info("{}个线程执行任务消耗了{}", threadNum, (System.currentTimeMillis() - start) + "毫秒");
        }
    }

    /**
     * 开启事务
     */

    public TransactionStatus begin() {
        return beginTransaction(transactionManager);
    }

    /**
     * 提交事务1
     */
    public void commit(TransactionStatus status) {
        commitTransaction(transactionManager, status);
    }

    /**
     * 回滚事务1
     */
    public void rollback(TransactionStatus status) {
        rollbackTransaction(transactionManager, status);
    }

    /**
     * 开启事务
     */
    public TransactionStatus beginTransaction(DataSourceTransactionManager transactionManager) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();//事务定义类
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);// 返回事务对象
        return status;
    }

    /**
     * 提交事务
     */
    public void commitTransaction(DataSourceTransactionManager transactionManager, TransactionStatus status) {
        transactionManager.commit(status);
    }

    /**
     * 事务回滚
     */
    public void rollbackTransaction(DataSourceTransactionManager transactionManager, TransactionStatus status) {
        transactionManager.rollback(status);
    }


    private class BatchUpdateCallable implements Callable<Integer> {
        private final List<Object[]> values;
        private final String sql;

        private BatchUpdateCallable(List<Object[]> values, String sql) {
            this.values = values;
            this.sql = sql;
        }

        @Override
        public Integer call() {
            TransactionStatus transactionStatus = null;
            int num;
            try {
                log.debug("begin transaction.");
                transactionStatus = begin();
                num = batchSizeUpdate(sql, values);
                log.debug("execute sql {}", sql);
                commit(transactionStatus);
                log.debug("commit transaction.");
            } catch (Exception e) {
                if (transactionStatus != null) {
                    rollback(transactionStatus);
                }
                throw new RuntimeException(e.getMessage(), e);
            }
            return num;
        }

    }
}


