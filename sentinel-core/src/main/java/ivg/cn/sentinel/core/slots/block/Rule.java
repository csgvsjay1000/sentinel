package ivg.cn.sentinel.core.slots.block;

import ivg.cn.sentinel.core.context.Context;
import ivg.cn.sentinel.core.node.DefaultNode;

public interface Rule {

	/**  检查是否通过 */
	boolean passCheck(Context context, DefaultNode node, int count, Object... args);
	
}
