package lean.data.structure.tree.extend;

/**
 * 待计算坐标的树，业务信息存在TreeNode中，不干扰算法运算。
 * 运算时与TreeNode的name属性耦合，要保证name的唯一性。
 * 后续考虑解耦。可在DrawTree生成时创建唯一ID替换。
 * 需要一个安全的自增ID生成器。
 */
public class Tree {

    private Tree[] children;

    private TreeNode t;

    public Tree(TreeNode t) {
        this.t = t;
        this.children = new Tree[0];
    }

    public Tree(TreeNode t, Tree[] children) {
        this.t = t;
        this.children = children;
    }

    public Tree[] getChildren() {
        return children;
    }

    public TreeNode getT() {
        return t;
    }

    public void setChildren(Tree[] children) {
        this.children = children;
    }
}
