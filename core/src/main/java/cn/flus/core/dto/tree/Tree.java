package cn.flus.core.dto.tree;

/**
 * 树
 * 
 * @author zhoux 2013-6-17
 */
public class Tree<T> {

    private TreeNode<T> root;

    public Tree() {
    }

    public Tree(TreeNode<T> root) {
        this.root = root;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

}
