package lean.data.structure.tree.extend;

/**
 * 树节点附带信息，节点坐标 x，y。
 * 节点名称不能重复。
 */
public class TreeNode {

    private String name = "";

    private float x = 0.0F;

    private float y = 0.0F;

    public TreeNode() {}

    public TreeNode(String name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
