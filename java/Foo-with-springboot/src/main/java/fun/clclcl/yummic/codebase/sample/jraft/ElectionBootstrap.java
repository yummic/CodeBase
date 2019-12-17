/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fun.clclcl.yummic.codebase.sample.jraft;

import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author jiachun.fjc
 */
public class ElectionBootstrap {


    // 启动 3 个实例选举, 注意如果实在同一台机器启动多个实例, 那么第一个参数 dataPath 不能相同
    public static void main(final String[] args) {
        if (args.length < 4) {
            System.out
                .println("Useage : java com.alipay.sofa.jraft.example.election.ElectionBootstrap {dataPath} {groupId} {serverId} {initConf}");
            System.out
                .println("Example: java com.alipay.sofa.jraft.example.election.ElectionBootstrap /tmp/server1 election_test 127.0.0.1:8081 127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083");
            System.exit(1);
        }
        final String dataPath = args[0];
        final String groupId = args[1];
        final String serverIdStr = args[2];
        final String initialConfStr = args[2];//args[3];

        final ElectionNodeOptions electionOpts = new ElectionNodeOptions();
        electionOpts.setDataPath(dataPath);
        electionOpts.setGroupId(groupId);
        electionOpts.setServerAddress(serverIdStr);
        electionOpts.setInitialServerAddressList(initialConfStr);
        electionOpts.setNodeOptions(new NodeOptions());
        electionOpts.getNodeOptions().setElectionTimeoutMs(10000);

        final ElectionNode node = new ElectionNode();

        node.addLeaderStateListener(new LeaderStateListener() {

            @Override
            public void onLeaderStart(long leaderTerm) {
                System.out.println("[ElectionBootstrap] Leader start on term: " + leaderTerm);
            }

            @Override
            public void onLeaderStop(long leaderTerm) {
                System.out.println("[ElectionBootstrap] Leader stop on term: " + leaderTerm);
            }
        });

        node.init(electionOpts);

        addNodeAndRetry(node,"127.0.0.1:8081");
        addNodeAndRetry(node,"127.0.0.1:8082");
        addNodeAndRetry(node,"127.0.0.1:8083");
    }

    private static void addNodeAndRetry(ElectionNode node, String peerStr) {
        AtomicBoolean flag = new AtomicBoolean(true);
        PeerId peer = PeerId.parsePeer(peerStr);
        while (flag.get()) {
            try {
                if (!node.getNode().isLeader()) {
                    System.err.println("OOOOOOOOOOOOO I am not Leader...");
                    Thread.sleep(3000);
                    continue;
                }
                if (node.getNode().listPeers().contains(peer)) {
                    System.err.println("MMMMMMMMMMMMMM exists peer : " +  peerStr);
                    return;
                }
                node.getNode().addPeer(peer, status -> {
                    if (status.isOk()) {
                        System.err.println("SSSSSSS success to add node." + peerStr);
                        flag.set(false);
                    } else {
                        System.err.println("XXXXXXXXXXXX : "  + peerStr + status.getErrorMsg());
                    }
                });
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                System.err.println("YYYYYYY: "  + peerStr + ex);
                break;
            }
        }
    }
}
