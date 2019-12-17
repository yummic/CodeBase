package fun.clclcl.yummic.codebase.sample.jraft;

import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;
import fun.clclcl.yummic.codebase.sample.springboot.controller.Message;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/election")
public class ElectionController {

    boolean started = false;

    final ElectionNode node = new ElectionNode();

    @RequestMapping(method = RequestMethod.POST, path = "/start")
    @ResponseBody
    public Message startElection(@RequestBody String addresses, @RequestParam("local") String localAddress, @RequestParam("index") Integer index) {
        if (started) {
            return new Message(0L, "Node is started");
        }
        final String dataPath = "/tmp/raftserver" + index;
        final String groupId = "raft-uniio";
        final String serverIdStr = localAddress;
        final String initialConfStr = addresses;//args[3];

        final ElectionNodeOptions electionOpts = new ElectionNodeOptions();
        electionOpts.setDataPath(dataPath);
        electionOpts.setGroupId(groupId);
        electionOpts.setServerAddress(serverIdStr);
        electionOpts.setInitialServerAddressList(initialConfStr);
        electionOpts.setNodeOptions(new NodeOptions());
        electionOpts.getNodeOptions().setElectionTimeoutMs(10000);

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
        started = true;
        Message msg = new Message(1L, "start ok.");
        return msg;
    }

    // 启动 3 个实例选举, 注意如果实在同一台机器启动多个实例, 那么第一个参数 dataPath 不能相同
    public static void main(final String[] args) {


        //addNodeAndRetry(node,"127.0.0.1:8081");
        //addNodeAndRetry(node,"127.0.0.1:8082");
        //addNodeAndRetry(node,"127.0.0.1:8083");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addNode")
    public Message addNodeAndRetry(@RequestParam("peerStr") String peerStr) {
        if (!started) {
            return new Message(0L, "Node is Not started");
        }
        PeerId peer = PeerId.parsePeer(peerStr);
        if (!node.getNode().isLeader()) {
            System.err.println("OOOOOOOOOOOOO I am not Leader...");
            return new Message(2L, "Failed cause of Not leader.");
        }

        if (node.getNode().listPeers().contains(peer)) {
            System.err.println("MMMMMMMMMMMMMM exists peer : " + peerStr);
            return new Message(2L, "MMMMMMMMMMMMMM exists peer : " + peerStr);
        }
        node.getNode().addPeer(peer, status -> {
            if (status.isOk()) {
                System.err.println("SSSSSSS success to add node." + peerStr);
            } else {
                System.err.println("XXXXXXXXXXXX : " + peerStr + status.getErrorMsg());
            }
        });
        return new Message(2L, "Add Node Task for : " + peerStr);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/checkNode")
    public Message queryPeerNow(@RequestParam("peerStr") String peerStr) {
        if (!started) {
            return new Message(0L, "Node is Not started");
        }
        PeerId peer = PeerId.parsePeer(peerStr);
        if (!node.getNode().isLeader()) {
            System.err.println("OOOOOOOOOOOOO I am not Leader...");
            return new Message(2L, "Failed cause of Not leader.");
        }
        if (node.getNode().listPeers().contains(peer)) {
            System.err.println("MMMMMMMMMMMMMM exists peer : " + peerStr);
            return new Message(2L, "MMMMMMMMMMMMMM exists peer : " + peerStr);
        }
        return new Message(2L, "Not exists." + peerStr);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/change")
    public Message changeLeader() {
        if (!started) {
            return new Message(0L, "Node is Not started");
        }
        if (!node.getNode().isLeader()) {
            System.err.println("OOOOOOOOOOOOO I am not Leader...");
            return new Message(2L, "Failed cause of Not leader.");
        }
        Configuration conf = new Configuration();
        node.getNode().transferLeadershipTo(PeerId.ANY_PEER);
        return new Message(2L, "change leader task ok.");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/leader")
    public Message getLeader() {
        if (!started) {
            return new Message(0L, "Node is Not started");
        }
        Configuration conf = new Configuration();
        PeerId leader = node.getNode().getLeaderId();
        if (leader == null) {
            return new Message(2L, "No leader");
        } else {
            return new Message(2L, "Leader is : " + leader.toString());
        }
    }
}
