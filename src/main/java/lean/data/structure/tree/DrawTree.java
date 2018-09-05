package lean.data.structure.tree;

public class DrawTree {

    private float x = -1;

    private int y = 0;

    private float mod = 0.0f;

    private Tree tree;

    private DrawTree[] children;

    private DrawTree parent;

    private DrawTree thread;

    private DrawTree ancestor;

    private float change = 0.0f;

    private float shift = 0.0f;

    private int number = 1;

    private DrawTree _lmost_sibling;

    public DrawTree(Tree tree, DrawTree parent, int y, int number) {
        this.y = y;
        this.tree = tree;
        this.parent = parent;
        this.number = number;
        this.ancestor = this;
        Tree[] children = tree.getChildren();
        if(null == children) {
            this.children = new DrawTree[0];
        } else {
            DrawTree[] dtchildren = new DrawTree[children.length];
            for(int i = 0; i < children.length; i++) {
                dtchildren[i] = new DrawTree(children[i], this, y+1, i+1);
            }
            this.children = dtchildren;
        }
    }

    public DrawTree left() {
        if(this.thread != null) {
            return this.thread;
        } else {
            if(this.children.length > 0) {
                return this.children[0];
            } else {
                return null;
            }
        }
    }

    public DrawTree right() {
        if(this.thread != null) {
            return this.thread;
        } else {
            if(this.children.length > 0) {
                return this.children[children.length - 1];
            } else {
                return null;
            }
        }
    }

    public DrawTree lbrother() {
        if(this.parent == null) {
            return null;
        }
        DrawTree lbrother = null;
        for(DrawTree child : this.parent.children) {
            if(child == this) {
                return lbrother;
            } else {
                lbrother = child;
            }
        }
        return lbrother;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getMod() {
        return mod;
    }

    public void setMod(float mod) {
        this.mod = mod;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public DrawTree[] getChildren() {
        return children;
    }

    public void setChildren(DrawTree[] children) {
        this.children = children;
    }

    public DrawTree getParent() {
        return parent;
    }

    public void setParent(DrawTree parent) {
        this.parent = parent;
    }

    public DrawTree getThread() {
        return thread;
    }

    public void setThread(DrawTree thread) {
        this.thread = thread;
    }

    public DrawTree getAncestor() {
        return ancestor;
    }

    public void setAncestor(DrawTree ancestor) {
        this.ancestor = ancestor;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public float getShift() {
        return shift;
    }

    public void setShift(float shift) {
        this.shift = shift;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public DrawTree get_lmost_sibling() {
        if(this._lmost_sibling == null && this.parent != null && this != this.parent.children[0]) {
            this._lmost_sibling = this.parent.children[0];
        }
        return _lmost_sibling;
    }

    public void set_lmost_sibling(DrawTree _lmost_sibling) {
        this._lmost_sibling = _lmost_sibling;
    }
}
