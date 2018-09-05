package lean.data.structure.tree;

public class Tree {

    private String node;

    private int width;

    private Tree[] children;

    public Tree(String node) {
        this.node = node;
        this.width = node.length();
        this.children = new Tree[0];
    }

    public Tree(String node, Tree[] children) {
        this.node = node;
        this.width = node.length();
        this.children = children;
    }

    public Tree(String node, int width, Tree[] children) {
        this.node = node;
        this.width = width;
        this.children = children;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Tree[] getChildren() {
        return children;
    }

    public void setChildren(Tree[] children) {
        this.children = children;
    }
}
