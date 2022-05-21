package org.example.listener;

import org.example.config.BinLogItem;

/**
 * BinLogListener监听器
 *
 * @author yuancetian
 * @since 2021/7/26
 **/
@FunctionalInterface
public interface BinLogListener {

    void onEvent(BinLogItem item);
}

