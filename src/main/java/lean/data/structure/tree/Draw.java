package lean.data.structure.tree;

import java.util.Arrays;
import java.util.Optional;

public class Draw {

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
            System.out.println("finished drawTree = " + drawTree.getTree().getNode() + " children");
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
        System.out.println(drawTreeLeft.getTree().getNode() + "is conflicted with "
                + drawTreeRight.getTree().getNode() + " moving " + subtrees + " shift " + shift);
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
                .filter(child -> child.getTree().getNode().equals(ancestor.getTree().getNode())).findAny();
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
            System.out.println("shift: " + child.getTree().getNode() + "-" + shift + "-" + child.getChange());
            child.setX(child.getX() + shift);
            child.setMod(child.getMod() + shift);
            change += child.getChange();
            shift += child.getShift() + change;
        }
    }

    private static float secondWalk(DrawTree dt, float m, int y, float min) {
        dt.setX(dt.getX() + m);
        dt.setY(y);
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
        dt.setX(dt.getX() + i);
        DrawTree[] children = dt.getChildren();
        for(DrawTree child : children) {
            thirdWalk(child, i);
        }
    }

    public static void print(DrawTree root, int depth) {
        System.out.println("node " + root.getTree().getNode() + " x:" + root.getX() + " y:" + root.getY());
        for(DrawTree child : root.getChildren()) {
            print(child, depth + 1);
        }
    }
    public static void main(String[] args) {
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
        Tree tree = new Tree("root", new Tree[]{
                new Tree("bigleft", new Tree[]{
                        new Tree("l1"),
                        new Tree("l2"),
                        new Tree("l3"),
                        new Tree("l4"),
                        new Tree("l5"),
                        new Tree("l6"),
                        new Tree("l7", new Tree[]{
                                new Tree("ll1")
                        }),
                }),
                new Tree("m1"),
                new Tree("m2"),
                new Tree("m3", new Tree[]{
                        new Tree("m31"),
                        new Tree("m32"),
                        new Tree("m33"),
                        new Tree("m34"),
                        new Tree("m35"),
                        new Tree("m36")
                }),
                new Tree("m4"),
                new Tree("bigright", new Tree[]{
                        new Tree("brr", new Tree[]{
                                new Tree("br1"),
                                new Tree("br2"),
                                new Tree("br3"),
                                new Tree("br4"),
                                new Tree("br5"),
                                new Tree("br6"),
                                new Tree("br7")
                        })
                })
        });
        DrawTree drawTree = Draw.buchheim(tree);
        print(drawTree, drawTree.getY());
    }
}
