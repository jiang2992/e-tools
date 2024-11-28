import java.util.Arrays;
import java.util.List;
import org.jiang.tools.arithmetic.hash.ConsistentHash;
import org.jiang.tools.arithmetic.hash.ConsistentHashLoop;
import org.jiang.tools.arithmetic.hash.ConsistentHashNode;
import org.jiang.tools.arithmetic.hash.PointScope;
import org.junit.Test;

/**
 * 算法测试类
 *
 * @author Bin
 * @since 1.1.5
 */
public class ArithmeticTests {

    @Test
    public void consistentHash() {
        ConsistentHashLoop loop = new ConsistentHashLoop(1000);
        ConsistentHash consistentHash = ConsistentHash.of(loop);
        ConsistentHashNode nodeA = loop.createNode("NODE_A", 2);
        ConsistentHashNode nodeB = loop.createNode("NODE_B", 2);
        ConsistentHashNode nodeC = loop.createNode("NODE_C", 1);

        System.out.println("添加节点：" + nodeA.getTarget());
        System.out.println("影响范围：" + Arrays.toString(consistentHash.putNode(nodeA)));
        System.out.println(consistentHash);

        System.out.println("\n添加节点：" + nodeB.getTarget());
        System.out.println("影响范围：" + Arrays.toString(consistentHash.putNode(nodeB)));
        System.out.println(consistentHash);

        System.out.println("\n添加节点：" + nodeC.getTarget());
        System.out.println("影响范围：" + Arrays.toString(consistentHash.putNode(nodeC)));
        System.out.println(consistentHash);
        System.out.println();

        List<String> ipList = Arrays.asList("192.168.1.1", "192.168.1.48", "192.168.1.95", "192.168.1.158", "192.168.1.216");
        for (String ip : ipList) {
            ConsistentHashNode node = consistentHash.getNode(ip);
            System.out.printf("IP [%s] 分配到节点：%s%n", ip, node.getTarget());
        }

        PointScope[] pointScopes = consistentHash.removeNode(nodeB);
        System.out.println("\n删除节点：" + nodeB.getTarget());
        System.out.println("影响范围：" + Arrays.toString(pointScopes));
        System.out.println();

        for (PointScope pointScope : pointScopes) {
            for (Object ip : pointScope.filtration(ipList.toArray())) {
                System.out.printf("IP [%s] 需要重新分配到节点：%s%n", ip, pointScope.getNextNode().getTarget());
            }
        }

    }

}
