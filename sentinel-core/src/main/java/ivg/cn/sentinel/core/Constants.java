package ivg.cn.sentinel.core;

import ivg.cn.sentinel.core.node.ClusterNode;
import ivg.cn.sentinel.core.node.DefaultNode;
import ivg.cn.sentinel.core.node.EntranceNode;
import ivg.cn.sentinel.core.slotchain.StringResourceWrapper;

public class Constants {

    public final static String ROOT_ID = "machine-root";

    public final static DefaultNode ROOT = new EntranceNode(new StringResourceWrapper(ROOT_ID, EntryType.IN), new ClusterNode());	
    
}
