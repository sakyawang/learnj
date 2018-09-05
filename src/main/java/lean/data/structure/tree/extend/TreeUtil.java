package lean.data.structure.tree.extend;

import com.alibaba.fastjson.JSONArray;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TreeUtil {

    /**
     * 有序集合转为树。
     * @param originList Breadth-first search tree 序列的有序集合。
     * @return
     */
    public static Tree generalTree(List<ZoneNode> originList) {
        ZoneNode root = originList.get(0);
        long nodeId = root.getId();
        Tree rootTree = new Tree(root);
        rootTree.setChildren(generalChildren(originList, nodeId));
        return rootTree;
    }

    /**
     * 生成子节点集合。
     * @param originList
     * @param pNodeId
     * @return
     */
    private static Tree[] generalChildren(List<ZoneNode> originList, long pNodeId) {
        List<ZoneNode> children = originList.stream().filter(zone -> pNodeId == zone.getPid())
                .collect(Collectors.toList());
        Collections.sort(children, Comparator.comparing(ZoneNode::getId));
        if(children.isEmpty()) {
            return new Tree[0];
        }
        Tree[] trees = new Tree[children.size()];
        for(int i = 0; i < children.size(); i++) {
            ZoneNode child = children.get(i);
            long nodeId = child.getId();
            Tree proxy = new Tree(child);
            Tree[] childTreeArray = generalChildren(originList, nodeId);
            if(childTreeArray.length > 0) {
                proxy.setChildren(childTreeArray);
            } else {
                proxy.setChildren(new Tree[0]);
            }
            trees[i] = proxy;
        }
        return trees;
    }

    public static void main(String[] args) {
        String jsonstr = "[{\"pid\":-1,\"name\":\"数据中心\",\"id\":5},{\"pid\":5,\"name\":\"工作区\",\"id\":10005},{\"pid\":5,\"name\":\"服务区\",\"id\":10006},{\"pid\":5,\"name\":\"测试区\",\"id\":10007},{\"pid\":10005,\"name\":\"研发\",\"id\":10015},{\"pid\":10005,\"name\":\"测试\",\"id\":10014},{\"pid\":10006,\"name\":\"演示服务\",\"id\":10011},{\"pid\":10006,\"name\":\"对外服务\",\"id\":10012},{\"pid\":10006,\"name\":\"测试服务\",\"id\":10010},{\"pid\":10006,\"name\":\"开发服务\",\"id\":10009},{\"pid\":10007,\"name\":\"wh\",\"id\":10023},{\"pid\":10007,\"name\":\"lq\",\"id\":10024},{\"pid\":10007,\"name\":\"sj\",\"id\":10025},{\"pid\":10007,\"name\":\"lgf\",\"id\":10026},{\"pid\":10007,\"name\":\"zx\",\"id\":10027},{\"pid\":10007,\"name\":\"sjx\",\"id\":10022},{\"pid\":10007,\"name\":\"syr\",\"id\":10021},{\"pid\":10007,\"name\":\"lfq\",\"id\":10020},{\"pid\":10007,\"name\":\"xw\",\"id\":10019},{\"pid\":10007,\"name\":\"ry\",\"id\":10018},{\"pid\":10007,\"name\":\"gp\",\"id\":10017},{\"pid\":10007,\"name\":\"djp\",\"id\":10016},{\"pid\":10007,\"name\":\"硬件主机\",\"id\":10028}]";
        List<ZoneNode> zoneList = JSONArray.parseArray(jsonstr, ZoneNode.class);
        Collections.sort(zoneList, Comparator.comparing(ZoneNode::getId));
        AtomicInteger atomicInteger = new AtomicInteger(0);
        int maxLevel = 0;
        for (ZoneNode zone : zoneList) {
            int index = atomicInteger.getAndIncrement();
            if(index == 0) {
                zone.setType("global");
                zone.setLevel(maxLevel);
            } else {
                long id = zone.getId();
                List<ZoneNode> children = zoneList.stream()
                        .filter(child -> child.getPid() == id).collect(Collectors.toList());
                zone.setType(children.isEmpty() ? "child" : "zone");
                long pid = zone.getPid();
                Optional<ZoneNode> pZone = zoneList.stream()
                        .filter(filter -> filter.getId() == pid).findFirst();
                maxLevel = pZone.get().getLevel() + 1;
                zone.setLevel(maxLevel);
            }
        }
        zoneList.forEach(System.out::println);
        Tree tree = TreeUtil.generalTree(zoneList);
        List<TreeNode> trees = Draw.toNodeList(tree);
        String jsonString = JSONArray.toJSONString(trees);
        System.out.println(jsonString);
    }
}
