package lean.data.structure.tree.extend;

import com.alibaba.fastjson.JSONArray;
import org.codehaus.jettison.json.JSONException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 生成带坐标信息的树。
 */
public class Draw {

    /**
     * 生成带坐标信息的树。
     * @param tree
     * @return
     */
    public static DrawTree buchheim(Tree tree) {
        DrawTree dt = firstWalk(new DrawTree(tree, null, 0, 1), 1.0f);
        float min = secondWalk(dt, 0.0f, 0, 0.0f);
        if(min < 0) {
            thirdWalk(dt, -min);
        }
        return dt;
    }

    private static DrawTree firstWalk(DrawTree drawTree, float distance) {
        if(drawTree.getChildren().length == 0) {
            DrawTree lmost_sibling = drawTree.get_lmost_sibling();
            if(lmost_sibling != null) {
                float x = drawTree.lbrother().getX() + distance;
                drawTree.setX(x);
            } else {
                drawTree.setX(0.0f);
            }
        } else {
            DrawTree defaultAncestor = drawTree.getChildren()[0];
            for(DrawTree child : drawTree.getChildren()) {
                firstWalk(child, 1.0f);
                defaultAncestor = apportion(child, defaultAncestor, distance);
            }
            System.out.println("finished drawTree = " + drawTree.getTree().getT().getName() + " children");
            executeShifts(drawTree);
            DrawTree[] children = drawTree.getChildren();
            float midpoint = (children[0].getX() + children[children.length -1].getX()) / 2;
            DrawTree lbrother = drawTree.lbrother();
            if(lbrother != null) {
                drawTree.setX(lbrother.getX() + distance);
                drawTree.setMod(drawTree.getX() - midpoint);
            } else {
                drawTree.setX(midpoint);
            }
        }
        return drawTree;
    }

    private static DrawTree apportion(DrawTree drawTree, DrawTree defaultAncestor, float distance) {
        DrawTree lbrother = drawTree.lbrother();
        if(lbrother != null) {
            // i == inner; o == outer; r == right; l == left; r = +; l = -
            DrawTree vir = drawTree;
            DrawTree vor = drawTree;
            DrawTree vil = lbrother;
            DrawTree vol = drawTree.get_lmost_sibling();
            float sir = drawTree.getMod();
            float sor = drawTree.getMod();
            float sil = vil.getMod();
            float sol = vol.getMod();
            while (vil.right() != null && vir.left() != null) {
                vil = vil.right();
                vir = vir.left();
                if(vol != null) {
                    vol = vol.lbrother();
                }
                vor = vor.right();
                vor.setAncestor(drawTree);
                float shift = (vil.getX() + sil) - (vir.getX() + sir) + distance;
                if(shift > 0) {
                    moveSubtree(ancestor(vil, drawTree, defaultAncestor), drawTree, shift);
                }
                if(vil != null) {
                    sil += vil.getMod();
                }
                if(vir != null) {
                    sir += vir.getMod();
                }
                if(vol != null) {
                    sol += vol.getMod();
                }
                if(vor != null) {
                    sor += vor.getMod();
                }
            }
            if(vil.right() != null && vor.right() == null) {
                vor.setThread(vil.right());
                vor.setMod(vor.getMod() + sil - sor);
            } else {
                if(vir.left() != null && vol.left() == null) {
                    vol.setThread(vir.left());
                    vol.setMod(vol.getMod() + sir - sol);
                }
                defaultAncestor = drawTree;
            }
        }
        return defaultAncestor;
    }

    private static void moveSubtree(DrawTree drawTreeLeft, DrawTree drawTreeRight, float shift) {
        int subtrees = drawTreeRight.getNumber() - drawTreeLeft.getNumber();
        System.out.println(drawTreeLeft.getTree().getT().getName() + "is conflicted with "
                + drawTreeRight.getTree().getT().getName() + " moving " + subtrees + " shift " + shift);
        drawTreeRight.setChange(drawTreeRight.getChange() - shift / subtrees);
        drawTreeRight.setShift(drawTreeRight.getShift() + shift);
        drawTreeLeft.setChange(drawTreeLeft.getChange() + shift / subtrees);
        drawTreeRight.setX(drawTreeRight.getX() + shift);
        drawTreeRight.setMod(drawTreeRight.getMod() + shift);
    }

    private static DrawTree ancestor(DrawTree vil, DrawTree drawTree, DrawTree defaultAncestor) {
        /**
         * the relevant text is at the bottom of page 7 of
         * "Improving Walker's Algorithm to Run in Linear Time" by Buchheim et al, (2002)
         * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.16.8757&rep=rep1&type=pdf
         */
        DrawTree ancestor = vil.getAncestor();
        if(ancestor == null) {
            return defaultAncestor;
        }
        DrawTree parent = drawTree.getParent();
        if(parent == null) {
            return defaultAncestor;
        }
        DrawTree[] children = parent.getChildren();
        if(children.length <= 0) {
            return defaultAncestor;
        }
        Optional<DrawTree> optional = Arrays.asList(children).stream()
                .filter(child -> child.getTree().getT().getName().equals(ancestor.getTree().getT().getName())).findAny();
        if(!optional.isPresent()) {
            return defaultAncestor;
        }
        return ancestor;
    }

    private static void executeShifts(DrawTree drawTree) {
        float shift = 0.0f;
        float change = 0.0f;
        DrawTree[] children = drawTree.getChildren();
        for(int i = children.length - 1; i > 0; i--) {
            DrawTree child = children[i];
            System.out.println("shift: " + child.getTree().getT().getName() + "-" + shift + "-" + child.getChange());
            child.setX(child.getX() + shift);
            child.setMod(child.getMod() + shift);
            change += child.getChange();
            shift += child.getShift() + change;
        }
    }

    private static float secondWalk(DrawTree dt, float m, int y, float min) {
        float x = dt.getX() + m;
        dt.setX(x);
        dt.setY(y);
        dt.getTree().getT().setX(x);
        dt.getTree().getT().setY(y);
        if(min == 0.0 || dt.getX() < min) {
            min = dt.getX();
        }
        DrawTree[] children = dt.getChildren();
        for(DrawTree child : children) {
            min = secondWalk(child, m + dt.getMod(), y + 1, min);
        }
        return min;
    }

    private static void thirdWalk(DrawTree dt, float i) {
        float x = dt.getX() + i;
        dt.setX(x);
        dt.getTree().getT().setX(x);
        dt.getTree().getT().setY(dt.getY());
        DrawTree[] children = dt.getChildren();
        for(DrawTree child : children) {
            thirdWalk(child, i);
        }
    }

    /**
     * 生成带坐标的树节点集合，避免后续递归遍历。
     * @param tree
     * @return
     */
    public static List<TreeNode> toNodeList(Tree tree) {
        return toList(buchheim(tree));
    }

    private static List<TreeNode> toList(DrawTree root) {
        List<TreeNode> list = new ArrayList<>();
        list.add(root.getTree().getT());
        DrawTree[] children = root.getChildren();
        if(children.length > 0) {
            loopChildren(list, children);
        }
        return list;
    }

    private static void loopChildren(List<TreeNode> list, DrawTree[] children) {
        for (DrawTree child : children) {
            list.add(child.getTree().getT());
            if(child.getChildren().length > 0) {
                loopChildren(list, child.getChildren());
            }
        }
    }

    public static void main(String[] args) throws JSONException {
        /**
         * Tree("root",
         Tree("bigleft",
         Tree("l1"),
         Tree("l2"),
         Tree("l3"),
         Tree("l4"),
         Tree("l5"),
         Tree("l6"),
         Tree("l7", Tree("ll1"))),
         Tree("m1"),
         Tree("m2"),
         Tree("m3", Tree("m31")),
         Tree("m4"),
         Tree("bigright",
         Tree("brr",
         Tree("br1"),
         Tree("br2"),
         Tree("br3"),
         Tree("br4"),
         Tree("br5"),
         Tree("br6"),
         Tree("br7")))),
         */
        Tree tree = new Tree(new TreeNode("root") ,new Tree[]{
                new Tree(new TreeNode("bigleft"), new Tree[]{
                        new Tree(new TreeNode("l1")),
                        new Tree(new TreeNode("l2")),
                        new Tree(new TreeNode("l3")),
                        new Tree(new TreeNode("l4")),
                        new Tree(new TreeNode("l5")),
                        new Tree(new TreeNode("l6")),
                        new Tree(new TreeNode("l7"), new Tree[]{
                                new Tree(new TreeNode("ll1"))
                        }),
                }),
                new Tree(new TreeNode("m1")),
                new Tree(new TreeNode("m2")),
                new Tree(new TreeNode("m3"), new Tree[]{
                        new Tree(new TreeNode("m31")),
                        new Tree(new TreeNode("m32")),
                        new Tree(new TreeNode("m33")),
                        new Tree(new TreeNode("m34")),
                        new Tree(new TreeNode("m35")),
                        new Tree(new TreeNode("m36"))
                }),
                new Tree(new TreeNode("m4")),
                new Tree(new TreeNode("bigright"), new Tree[]{
                        new Tree(new TreeNode("brr"), new Tree[]{
                                new Tree(new TreeNode("br1")),
                                new Tree(new TreeNode("br2")),
                                new Tree(new TreeNode("br3")),
                                new Tree(new TreeNode("br4")),
                                new Tree(new TreeNode("br5")),
                                new Tree(new TreeNode("br6")),
                                new Tree(new TreeNode("br7"))
                        })
                })
        });
        DrawTree drawTree = Draw.buchheim(tree);
        List<TreeNode> treeList = toList(drawTree);
        System.out.println(JSONArray.toJSONString(treeList));
        int width = 1000;
        int height = 1000;
        int radius = 10;
        JFrame jFrame = new JFrame("树图");
        jFrame.setSize(width, height);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setContentPane(new Point(drawTree, width, height, radius));
        jFrame.setVisible(true);
    }
}
