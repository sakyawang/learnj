package lean.data.structure.tree.extend;

/**
 * 扩展树节点，存放节点坐标信息之外的业务信息。
 */
public class ZoneNode extends TreeNode {

    private long pid = -1;

    private long id;

    private int level = 0;

    private String type;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
