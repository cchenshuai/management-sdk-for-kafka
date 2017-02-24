/**
 * Copyright (c) 2017 McAfee Inc. - All Rights Reserved
 */

package com.mcafee.dxl.streaming.operations.client.examples;

import com.mcafee.dxl.streaming.operations.client.ZookeeperMonitor;
import com.mcafee.dxl.streaming.operations.client.ZookeeperMonitorBuilder;
import com.mcafee.dxl.streaming.operations.client.zookeeper.ZKClusterHealthName;
import com.mcafee.dxl.streaming.operations.client.zookeeper.entities.ZKCluster;

/**
 * This example get zookeeper cluster status and print nodes' statistics
 * This is  an exampl of the information that zookeeper node offer:
 *
 * <p>
 * Zookeeper version: 3.4.8--1, built on 02/06/2016 03:18 GMT
 * <p>
 * Clients:
 * /127.0.0.1:53517[1](queued=0,recved=1,sent=1)
 * /127.0.0.1:53270[1](queued=0,recved=52,sent=52)
 * /127.0.0.1:53267[1](queued=0,recved=53,sent=53)
 * /127.0.0.1:53527[0](queued=0,recved=1,sent=0)
 * <p>
 * Latency min/avg/max: 0/0/5
 * Received: 196
 * Sent: 195
 * Connections: 4
 * Outstanding: 0
 * Zxid: 0x200000004
 * Mode: leader
 * Node count: 21

 <pre>
 {@code
public class ZKMonitorNodeStatisticsExample {

    private static final String ZOOKEEPER_SERVER_HOST_NAMES = "zookeeper-1:2181,zookeeper-2:2181,zookeeper-3:2181";
    private static final int ZOOKEEPER_SESSION_TIME_OUT_MS = 8000;
    private static final int ZOOKEEPER_POLL_INITIAL_DELAY_TIME_MS = 500;
    private static final int ZOOKEEPER_POLL_DELAY_TIME_MS = 1000;
    private static final long TWO_SECONDS = 2000;

    // Example entry point
    public static void main(final String[] args) {
        new ZKMonitorNodeStatisticsExample().startExample();
    }

    public void startExample() {
        try {
            ZookeeperMonitor zkMonitor = new ZookeeperMonitorBuilder(ZOOKEEPER_SERVER_HOST_NAMES)
                    .withZookeeperSessionTimeout(ZOOKEEPER_SESSION_TIME_OUT_MS)
                    .withZKPollingInitialDelayTime(ZOOKEEPER_POLL_INITIAL_DELAY_TIME_MS)
                    .withZKPollingDelayTime(ZOOKEEPER_POLL_DELAY_TIME_MS)
                    .build();

            zkMonitor.start(); // Start Zookeeper Monitoring
            System.out.println("Example started. Waiting for zookeeper cluster being running...");

            while (zkMonitor.getHealth() == ZKClusterHealthName.NO_QUORUM) {
                Thread.sleep(TWO_SECONDS);
            }

            final ZKCluster zookeeperCluster = zkMonitor.getCluster();

            zookeeperCluster.getZKNodes().forEach(zkNode -> {
                System.out.println("############ " + zkNode.getZKNodeId() + " " + zkNode.getZkNodeStatus() + " ############");
                System.out.println(zkNode.getZkNodeStatistics());
            });
            zkMonitor.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}
</pre>
 */

public class ZKMonitorNodeStatisticsExample {

    private static final String ZOOKEEPER_SERVER_HOST_NAMES = "zookeeper-1:2181,zookeeper-2:2181,zookeeper-3:2181";
    private static final int ZOOKEEPER_SESSION_TIME_OUT_MS = 8000;
    private static final int ZOOKEEPER_POLL_INITIAL_DELAY_TIME_MS = 500;
    private static final int ZOOKEEPER_POLL_DELAY_TIME_MS = 1000;
    private static final long TWO_SECONDS = 2000;

    // Example entry point
    public static void main(final String[] args) {
        new ZKMonitorNodeStatisticsExample().startExample();
    }

    public void startExample() {
        try {
            ZookeeperMonitor zkMonitor = new ZookeeperMonitorBuilder(ZOOKEEPER_SERVER_HOST_NAMES)
                    .withZKSessionTimeout(ZOOKEEPER_SESSION_TIME_OUT_MS)
                    .withZKPollingInitialDelayTime(ZOOKEEPER_POLL_INITIAL_DELAY_TIME_MS)
                    .withZKPollingDelayTime(ZOOKEEPER_POLL_DELAY_TIME_MS)
                    .build();

            zkMonitor.start(); // Start Zookeeper Monitoring
            System.out.println("Example started. Waiting for zookeeper cluster being running...");

            while (zkMonitor.getHealth() == ZKClusterHealthName.NO_QUORUM) {
                Thread.sleep(TWO_SECONDS);
            }

            final ZKCluster zookeeperCluster = zkMonitor.getCluster();

            zookeeperCluster.getZKNodes().forEach(zkNode -> {
                System.out.println("############ " + zkNode.getZKNodeId() + " " + zkNode.getZkNodeStatus() + " ############");
                System.out.println(zkNode.getZkNodeStatistics());
            });
            zkMonitor.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
