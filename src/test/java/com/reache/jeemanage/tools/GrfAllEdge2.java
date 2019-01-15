package com.reache.jeemanage.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * 无向无权无环图<br/>
 * 寻找起点到终点的所有路径 定义 根据节点间的关系及脱管节点，给出所有受影响的节点数
 */
public class GrfAllEdge2 {
	// 图的顶点总数
	private int total;
	// 各顶点基本信息
	private String[] nodes;
	// 脱管的节点
	private List<String> nodesUn;
	// asg1节点位置
	private int asg1Index;
	// asg2节点位置
	private int asg2Index;
	// 图的邻接矩阵
	private int[][] matirx;

	/**
	 * 
	 * @param nodesA   A端节点数组
	 * @param nodesZ   A端对应的Z端节点数组
	 * @param asg1Name asg1节点名称
	 * @param asg2Name asg2节点名称
	 * @param nodesUn  脱管的节点列表
	 */
	public GrfAllEdge2(String[] nodesA, String[] nodesZ, String asg1Name, String asg2Name, List<String> nodesUn) {
		Map<String, Integer> nodeIndexTmp = new HashMap<String, Integer>();
		Map<String, Integer> nodeIndex = new HashMap<String, Integer>();
		// 给数组编号
		for (int i = 0; i < nodesA.length; i++) {
			nodeIndexTmp.put(nodesA[i], i);
		}
		for (int i = 0; i < nodesZ.length; i++) {
			nodeIndexTmp.put(nodesZ[i], i);
		}
		int count = 0;
		for (String key : nodeIndexTmp.keySet()) {
			nodeIndex.put(key, count);
			count++;
		}
		// 生成节点个数、节点数组
		this.total = count;
		this.nodes = new String[total];
		Set<String> set = nodeIndex.keySet();
		set.toArray(this.nodes);
		this.matirx = new int[total][total];
		this.nodesUn = nodesUn;
		this.asg1Index = nodeIndex.get(asg1Name);
		this.asg1Index = nodeIndex.get(asg2Name);
		// 根据A-Z关联关系生成矩阵
		for (int i = 0; i < nodesA.length; i++) {
			String nodeA = nodesA[i];
			String nodeZ = nodesZ[i];
			int nodeAIndex = nodeIndex.get(nodeA);
			int nodeZIndex = nodeIndex.get(nodeZ);
			this.matirx[nodeAIndex][nodeZIndex] = 1;
			this.matirx[nodeZIndex][nodeAIndex] = 1;
		}
		// 脱管节点与其他节点设置无关系
		for (int i = 0; i < total; i++) {
			if (this.nodesUn.contains(nodes[i])) {
				for (int j = 0; j < total; j++) {
					this.matirx[j][i] = 0;
					this.matirx[i][j] = 0;
				}
			}
		}
		// 节点自身不会相连
		for (int i = 0; i < this.total; i++) {
			this.matirx[i][i] = 0;
		}
		// 设置两个asg节点间无关联
		this.matirx[asg1Index][asg2Index] = 0;
		this.matirx[asg2Index][asg1Index] = 0;
	}

	/**
	 * 获取每条路径的经过节点
	 * 
	 * @param stack
	 * @param k
	 * @return
	 */
	private String getStackLink(Stack<Integer> stack, int k) {
		StringBuilder sb = new StringBuilder();
		for (Integer i : stack) {
			sb.append(this.nodes[i] + ",");
		}
		sb.append(this.nodes[k] + ",");
		return sb.toString();
	}

	/**
	 * 寻找起点到终点的所有路径
	 * 
	 * @param underTop 紧挨着栈顶的下边的元素
	 * @param goal     目标
	 * @param stack
	 */
	private void dfsStack(int underTop, int goal, Stack<Integer> stack, List<String> lines) {
		if (stack.isEmpty()) {
			return;
		}

		// 访问栈顶元素，但不弹出
		int k = stack.peek().intValue();
		// 紧挨着栈顶的下边的元素
		// int uk = underTop;

		if (k == goal) {
			// System.out.print("\n起点与终点不能相同");
			return;
		}

		// 对栈顶的邻接点依次递归调用，进行深度遍历
		for (int i = 0; i < this.total; i++) {
			// 有边，并且不在左上到右下的中心线上
			if (this.matirx[k][i] == 1 && k != i) {
				// 排除环路
				if (stack.contains(i)) {
					// 由某顶点A，深度访问其邻接点B时，由于是无向图，所以存在B到A的路径，在环路中，我们要排除这种情况
					// 严格的请，这种情况也是一个环
					// if (i != uk) {
					// System.out.print("\n有环:");
					// this.printStack(stack, i);
					// }
					continue;
				}

				// 打印路径
				if (i == goal) {
//					System.out.print("\n路径:");
					lines.add(getStackLink(stack, i));
					continue;
				}
				// 深度遍历
				stack.push(i);
				dfsStack(k, goal, stack, lines);
			}
		}
		stack.pop();
	}

	/**
	 * 打印节点关系矩阵图
	 */
	public void printMatrix() {

		System.out.println("----------------- matrix -----------------");
		for (int i = 0; i < this.total; i++) {
			System.out.print(this.nodes[i] + "|");
		}
		System.out.println();
		for (int i = 0; i < this.total; i++) {
			System.out.print(" " + this.nodes[i] + "|");
			for (int j = 0; j < this.total; j++) {
				System.out.print(this.matirx[i][j] + "-");
			}
			System.out.print("\n");
		}
		System.out.println("----------------- matrix -----------------");
	}

	/**
	 * 
	 * @return 返回受影响的节点，不包括asg节点，如果ASG节点不正常，则此方法不适用
	 */
	public List<String> unreachableNodeAll() {
		List<String> unreachableNodeList = new ArrayList<String>();
		// 查询节点关联到主ASG节点
		for (int i = 0; i < this.total; i++) {
			int origin = i;
			int goal1 = this.asg1Index;
			Stack<Integer> stack1 = new Stack<Integer>();
			List<String> lines1 = new ArrayList<String>();
			stack1.push(origin);
			this.dfsStack(-1, goal1, stack1, lines1);

			int goal2 = this.asg2Index;
			Stack<Integer> stack2 = new Stack<Integer>();
			List<String> lines2 = new ArrayList<String>();
			stack2.push(origin);
			this.dfsStack(-1, goal2, stack2, lines2);
			if (lines1.size() < 1 && lines2.size() < 1 && i != goal1 && i != goal2) {
				unreachableNodeList.add(this.nodes[i]);
			}
		}
		return unreachableNodeList;
	}

	public static void main(String[] args) {
		String[] nodesA = new String[] { "冀州码头李模块局_CSG", "冀州周村2_CSG", "冀州新寨模块_CSG", "冀州新庄模块局", "冀州王海模块_CSG",
				"冀州王海模块_CSG", "冀州五分村基站_CSG", "冀州魏屯模_CSG", "冀州西沙基站_CSG", "冀州北榆林基站_CSG", "冀州彭村基站910I_CSG", "冀州杨孔武基站_CSG",
				"冀州王海模块_CSG", "冀州徐庄西基站_CSG", "冀州门庄基站_CSG", "冀州县局3_CSG", "冀州柳家寨_CSG", "冀州烟家雾基站_CSG", "冀州徐家庄2_CSG",
				"冀州管道李基站（电信）_CSG", "冀州中心局基站_CSG", "冀州徐家庄_CSG", "冀州傅水店基站_CSG", "冀州管道李模块局_CSG", "冀州周村2_CSG",
				"冀州中心局1_ATN950B_CSG", "冀州枣园模块局_CSG", "冀州王海模块_CSG", "冀州五分村基站_CSG", "冀州金鼎佳园室分_CSG", "冀州东西堤北基站_CSG",
				"冀州前冢基站_CSG", "冀州码头李模块_CSG", "冀州南岳村基站_CSG", "冀州大寨模块_CSG", "冀州管道李模块_CSG", "冀州东王_CSG",
				"冀州县局_CX600-X8_ASG_1", "冀州垒头_CSG", "冀州二铺基站_CSG", "冀州程家庄基站_CSG", "冀州东午村基站_CSG", "冀州付官_CSG",
				"冀州炉具市场基站_CSG", "冀州黄村_CSG", "冀州中心局基站_CSG", "冀州北榆林基站_CSG", "冀州日上小区_ATN910I_CSG", "冀州衡尚营基站_CSG",
				"冀州码头李模块_CSG", "冀州县局3_CSG", "冀州彭村基站_CSG", "冀州付官_CSG", "冀州徐家庄基站_CSG", "冀州殷庄模_CSG", "冀州徐家庄_X3_CSG",
				"冀州冀新路基站_CSG", "冀州王家庄基站_CSG", "冀州徐家庄2_CSG", "冀州漫水洼_CSG", "冀州北张基站_CSG", "冀州管道李模块_CSG", "冀州徐家庄_X3_CSG",
				"冀州徐家庄_X3_CSG", "冀州周村模块局_CSG", "冀州漳淮模块局_CSG", "冀州薛家曹基站_CSG1", "冀州魏屯模_CSG", "冀州管道李模块_CSG", "冀州殷庄模_CSG",
				"冀州南午村模块局2_ATN950B_CSG", "冀州齐官屯基站_CSG", "冀州南午村模块局_CSG", "冀州管道李模块_CSG", "冀州县局_CX600-X8_ASG_1",
				"冀州王海模块_CSG", "冀州西城墙基站_CSG", "冀州魏屯模_CSG", "冀州南刘庄基站_CSG", "冀州殷庄模_CSG", "冀州野庄头_CSG", "冀州殷庄模_CSG",
				"冀州周村_CSG", "冀州后恩关_CSG", "冀州县局_CX600-X8_ASG_2", "冀州殷庄模_CSG", "冀州南午照基站（在周村模）_CSG", "冀州西王模块_CSG",
				"冀州东楼瞳村基站_CSG", "冀州北储宜基站_CSG", "冀州周村_CSG", "冀州大寨模块_CSG", "冀州北漳淮基站（电信）_CSG", "冀州码头李模块_CSG",
				"冀州金鼎佳园室分_CSG", "冀州大寨模块_CSG", "冀州西南王基站_CSG", "冀州南小寨基站_CSG", "冀州码头李模块_CSG", "冀州大寨模块_CSG", "冀州码头李二基站_CSG",
				"冀州南午村模块局_CSG", "冀州管道李模块_CSG", "冀州柏芽基站_CSG", "冀州北漳淮_CSG", "冀州管道李模块局_CSG", "冀州县局_CX600-X8_ASG_1",
				"冀州南午村模块局_CSG", "冀州西古头基站_CSG", "冀州新庄模块局", "冀州开发区基站_CSG", "冀州工行基站_CSG", "冀州县局_CX600-X8_ASG_1",
				"冀州李瓦窑基站_CSG", "冀州营业厅基站_CSG", "冀州县局_CX600-X8_ASG_1", "冀州旧城基站_CSG", "冀州大瓦窑基站_CSG", "冀州殷庄模_CSG",
				"冀州县局_CX600-X8_ASG_1", "冀州韩村基站_CSG", "冀州邢家宜子基站_CSG", "冀州碧水康庭基站_CSG", "冀州南午村模块局2_ATN950B_CSG",
				"冀州县局_CX600-X8_ASG_2", "冀州门庄基站_CSG", "冀州老城基站_CSG", "冀州东午村基站_CSG", "冀州南贾村基站_CSG", "冀州魏屯基站_CSG",
				"冀州县局_CX600-X8_ASG_2", "冀州新庄模块局", "冀州漳下村东基站_CSG", "冀州碧水湾基站_CSG", "冀州魏屯模_CSG", "冀州邮政基站_CSG",
				"冀州北张基站_CSG", "冀州前丰备基站_CSG", "冀州冀新路基站_CSG", "冀州吴吕基站_CSG", "冀州南午村模块局_CSG", "冀州刘家埝基站_CSG", "冀州南岳村基站_CSG",
				"冀州县局_CX600-X8_ASG_1", "冀州县局_CX600-X8_ASG_1", "冀州开发区基站_CSG", "冀州南午村模块局2_ATN950B_CSG", "冀州西古头基站_CSG",
				"冀州前冢基站_CSG", "冀州北内漳基站_CSG", "冀州县局_CX600-X8_ASG_2", "冀州县局_CX600-X8_ASG_2", "冀州孙杜基站_CSG", "冀州炉具市场基站_CSG",
				"冀州前韩庄基站_CSG", "冀州邢家宜子基站_CSG", "冀州县局_CX600-X8_ASG_1", "冀州县局_CX600-X8_ASG_1", "冀州程家周村_CSG",
				"冀州吉爽暖气（艺科）基站_CSG", "冀州程家周村_CSG", "冀州高田基站_CSG", "冀州工行基站_CSG", "冀州西王模块局_CSG", "冀州烟家雾基站_CSG",
				"冀州石家庄（电信 ）_CSG", "冀州寺上基站_CSG" };
		String[] nodesZ = new String[] { "冀州西王模块局_CSG", "冀州周村_CSG", "冀州北漳淮基站（电信）_CSG", "冀州彭村基站910I_CSG", "冀州西徐庄基站_CSG",
				"冀州西王模块_CSG", "冀州码头李3_ATN950B_CSG", "冀州魏屯模块局_CSG", "冀州漳下村东基站_CSG", "冀州徐家庄_X3_CSG", "冀州杨孔武基站_CSG",
				"冀州李桃园基站_CSG", "冀州王海模块局_CSG", "冀州枣园模块局_CSG", "冀州管道李模块_CSG", "冀州县局_CX600-X8_ASG_2", "冀州炉具市场基站_CSG",
				"冀州码头李模块_CSG", "冀州徐庄西基站_CSG", "冀州管道李模块_CSG", "冀州中心局1_ATN950B_CSG", "冀州枣园模块局_CSG", "冀州徐家庄_X3_CSG",
				"冀州王海模块局_CSG", "冀州南午村模块局2_ATN950B_CSG", "冀州彭村基站_CSG", "冀州北漳淮_CSG", "冀州西王庄基站_CSG", "冀州高田基站_CSG",
				"冀州广播局I_CSG", "冀州王海模块_CSG", "冀州旧城基站_CSG", "冀州前薛庄基站_CSG", "冀州孙杜基站_CSG", "冀州大寨模块局_CSG", "冀州范家庄基站_CSG",
				"冀州大寨模块_CSG", "冀州县局2_CSG", "冀州西宋村基站_CSG", "冀州西城墙基站_CSG", "冀州范家庄基站_CSG", "冀州垒头_CSG", "冀州前韩庄基站_CSG",
				"冀州李桃园基站_CSG", "冀州炉具市场基站_CSG", "冀州温州大厦_CSG", "冀州傅水店基站_CSG", "冀州冀新路基站_CSG", "冀州南刘庄基站_CSG",
				"冀州码头李模块局_CSG", "冀州县局2_CSG", "冀州吉爽暖气（艺科）基站_CSG", "冀州徐家庄_X3_CSG", "冀州徐家庄_X3_CSG", "冀州魏屯模_CSG",
				"冀州县局_CX600-X8_ASG_1", "冀州广播局I_CSG", "冀州徐家庄_X3_CSG", "冀州徐家庄_X3_CSG", "冀州垒头_CSG", "冀州西王模块_CSG",
				"冀州王海模块_CSG", "冀州枣园模块局_CSG", "冀州徐家庄_CSG", "冀州周村_CSG", "冀州北漳淮_CSG", "冀州周村_CSG", "冀州邢家村基站_CSG",
				"冀州大寨模块_CSG", "冀州隆盛环保基站_CSG", "冀州前辛店基站_CSG", "冀州魏屯模_CSG", "冀州北漳淮_CSG", "冀州路家庄_CSG", "冀州大寨模块_CSG",
				"冀州王海基站（电信）_CSG", "冀州老城基站_CSG", "冀州旧城基站_CSG", "冀州管道李基站（电信）_CSG", "冀州殷庄模块局_CSG", "冀州东午村基站_CSG",
				"冀州温泉基站_CSG", "冀州周村基站_CSG", "冀州周村_CSG", "冀州周村_CSG", "冀州八里庄基站_CSG", "冀州周村_CSG", "冀州码头李模块_CSG",
				"冀州魏屯模_CSG", "冀州付官_CSG", "冀州新寨模块_CSG", "冀州南大方基站_CSG", "冀州北漳淮_CSG", "冀州码头李3_ATN950B_CSG", "冀州二铺基站_CSG",
				"冀州南良村基站_CSG", "冀州北漳淮_CSG", "冀州北漳淮_CSG", "冀州县局_CX600-X8_ASG_2", "冀州大寨基站（电信）_CSG", "冀州码头李模块_CSG",
				"冀州周村_CSG", "冀州北大方_CSG", "冀州西王模块_CSG", "冀州北内漳基站_CSG", "冀州管道李模块_CSG", "冀州宾馆基站_CSG", "冀州田庄基站_CSG",
				"冀州李瓦窑基站_CSG", "冀州马厂庄基站_CSG", "冀州县局_CX600-X8_ASG_2", "冀州县局_CX600-X8_ASG_2", "冀州三基站_CSG", "冀州南午村基站_CSG",
				"冀州县局_CX600-X8_ASG_2", "冀州魏屯基站_CSG", "冀州冀新路基站_CSG", "冀州南午村模块局2_ATN950B_CSG", "冀州前店基站_CSG",
				"冀州炉具市场基站_CSG", "冀州枣园模块局_CSG", "冀州王家宜子基站_CSG", "冀州张家宜子基站_CSG", "冀州南午村模块局_CSG", "冀州三里庄基站_CSG",
				"冀州王海基站（电信）_CSG", "冀州旧城基站_CSG", "冀州枣园模块局_CSG", "冀州西王庄基站_CSG", "冀州王家宜子基站_CSG", "冀州中心局基站_CSG",
				"冀州西沙基站_CSG", "冀州温泉基站_CSG", "冀州县局_CX600-X8_ASG_1", "冀州东明师庄村基站（电信）_CSG", "冀州县局_CX600-X8_ASG_2",
				"冀州南贾村基站_CSG", "冀州南午村模块局2_ATN950B_CSG", "冀州县局_CX600-X8_ASG_2", "冀州南午村基站_CSG", "冀州孟岭基站_CSG",
				"冀州旧城基站_CSG", "冀州南良村基站_CSG", "冀州大常庄基站_CSG", "冀州法院家属院基站_CSG", "冀州小罗村基站_CSG", "冀州南齐庄基站_CSG",
				"冀州南午村模块局_CSG", "冀州刘家埝基站_CSG", "冀州吴吕基站_CSG", "冀州碧水康庭基站_CSG", "冀州岳家庄基站_CSG", "冀州北大方_CSG", "冀州温泉基站_CSG",
				"冀州西宋村基站_CSG", "冀州张家宜子基站_CSG", "冀州华阳基站_CSG", "冀州县局_CX600-X8_ASG_2", "冀州周村_CSG", "冀州县局_CX600-X8_ASG_1",
				"冀州枣园模块局_CSG", "冀州柏芽基站_CSG", "冀州县局_CX600-X8_ASG_1", "冀州西王模块_CSG", "冀州伍佰口_CSG", "冀州县局_CX600-X8_ASG_1",
				"冀州南午村模块局2_ATN950B_CSG" };
		// List<String> nodesUn = Arrays.asList("冀州大常庄基站_CSG",
		// "冀州周村_CSG","冀州吉爽暖气（艺科）基站_CSG", "冀州中心局基站_CSG");
		List<String> nodesUn = Arrays.asList("冀州周村_CSG");
		// 节点目标关系数组、asg1名称、asg2名称、脱管的节点名称列表构造图关系实例
		GrfAllEdge2 grf = new GrfAllEdge2(nodesA, nodesZ, "冀州县局_CX600-X8_ASG_1", "冀州县局_CX600-X8_ASG_2", nodesUn);
		// 打印节点关系矩阵图
		grf.printMatrix();
		// 获取受影响的节点列表
		List<String> list = grf.unreachableNodeAll();
		System.out.println("脱管节点列表:" + nodesUn);
		System.out.println("受影响的节点列表:" + list);

	}

}