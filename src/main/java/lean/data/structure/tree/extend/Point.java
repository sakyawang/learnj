package lean.data.structure.tree.extend;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

public class Point extends JPanel {

    private int width;

    private int height;

    private int radius;

    private DrawTree tree;

    public Point(DrawTree tree, int width, int height, int radius) {
        this.tree = tree;
        this.width = width;
        this.height = height;
        this.radius = radius;
    }

    @Override
    public void paint(Graphics g) {
        if(Objects.isNull(this.tree)) {
            return;
        }
        float x = tree.getX();
        if (x > 0) {
            int coefficient = this.width / (int)(2 * x);
            draw(g, tree, coefficient);
        }
    }

    private void draw(Graphics g, DrawTree tree, int coefficient) {
        Graphics2D g2d = (Graphics2D) g;
        float x = tree.getX() * coefficient;
        int y = tree.getY() * 100;
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 2 * this.radius, 2 * this.radius);
        g2d.setColor(Color.BLUE);
        g2d.fill(circle);
        DrawTree parent = tree.getParent();
        if (Objects.nonNull(parent)) {
           g.drawLine((int)(parent.getX() * coefficient) + radius, parent.getY() * 100 + radius,
                   (int) (x + radius), y + radius);
        }
        DrawTree[] children = tree.getChildren();
        if (Objects.isNull(children) || children.length == 0) {
            return;
        }
        for (DrawTree node : children) {
            draw(g, node, coefficient);
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public DrawTree getTree() {
        return tree;
    }

    public void setTree(DrawTree tree) {
        this.tree = tree;
    }
}
