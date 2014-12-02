package org.uiowa.cs2820.engine.databases;

import java.util.Iterator;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;

public class AVLFieldDatabase extends BinaryTreeFieldDatabase
{
    /*
     * ONE RULE. The ROOT MUST ALWAYS BE AT 0
     */
    public AVLFieldDatabase(ChunkedAccess file)
    {
        super(file);
    }

    public void add(FieldFileNode data)
    {
        int root = insert(0, FieldFileNode.NULL_ADDRESS, data);
        switch (balanceNumber(root))
        {
        case 1:
            rotateLeft(root);
            break;
        case -1:
            rotateRight(root);
            break;
        default:
            break;
        }
    }

    private int balanceNumber(int index)
    {
        FieldFileNode node = (FieldFileNode) getFileHandle().get(index);
        if (node == null)
            return 0;
        int leftDepth = depth(node.getLeftPosition());
        int rightDepth = depth(node.getRightPosition());

        if (leftDepth - rightDepth >= 2)
            return -1;
        else if (leftDepth - rightDepth <= -2)
            return 1;
        return 0;
    }

    protected int insert(int rootIndex, int parent, FieldFileNode node)
    {
        // Get the node at the position given. It is the root of a subtree.
        FieldFileNode root = (FieldFileNode) getFileHandle().get(rootIndex);

        // If there is no node at this position we add our node here.
        if (root == null)
        {
            rootIndex = getFileHandle().nextAvailableChunk();
            node.setAddress(rootIndex);
            node.setParentPosition(parent);
            getFileHandle().set(node.convert(), rootIndex);

            // We return the address we put the new node at so that its
            // parent can update it's child pointer correctly
            return rootIndex;
        }
        else if (node.getField().compareTo(root.getField()) < 0)
        {
            // If our current node doesn't have a left child, add the new
            // node in that position
            if (root.getLeftPosition() == FieldFileNode.NULL_ADDRESS)
            {
                rootIndex = insert(root.getLeftPosition(), root.getAddress(), node);
                root.setLeftPosition(rootIndex);
                getFileHandle().set(root.convert(), root.getAddress());
            }
            else
                // Continue recursively
                insert(root.getLeftPosition(), root.getAddress(), node);
        }
        else if (node.getField().compareTo(root.getField()) > 0)
        {
            // Our current node doesn't have a right child, add a new node
            // in that position
            if (root.getRightPosition() == FieldFileNode.NULL_ADDRESS)
            {
                rootIndex = insert(root.getRightPosition(), root.getAddress(), node);
                root.setRightPosition(rootIndex);
                getFileHandle().set(root.convert(), root.getAddress());
            }
            else
                // Continue recursively
                insert(root.getRightPosition(), root.getAddress(), node);
        }

        // Check the balance of our sub tree
        switch (balanceNumber(rootIndex))
        {
        case 1:
            rotateLeft(rootIndex);
            break;
        case -1:
            rotateRight(rootIndex);
            break;
        default:
            break;
        }

        return rootIndex;
    }

    /**
     * Check the depth of a subtree with the root at a specific index
     * 
     * @param index
     *            The address of the subtree root
     * @return The depth of the subtree
     */
    public int depth(int index)
    {
        FieldFileNode node = (FieldFileNode) getFileHandle().get(index);
        if (node == null)
            return 0;
        int left = node.getLeftPosition();
        int right = node.getRightPosition();
        return 1 + Math.max(depth(left), depth(right));
    }

    @Override
    public void removeElement(int index)
    {
        super.removeElement(index);

        // Check the balance of our sub tree
        switch (balanceNumber(index))
        {
        case 1:
            rotateLeft(index);
            break;
        case -1:
            rotateRight(index);
            break;
        default:
            break;
        }
    }

    /*
     * ------------------------------------------------------------------------
     * Rotate Methods
     * ------------------------------------------------------------------------
     * 
     * These methods are the standard rotation methods for an AVL tree. They are
     * based off of the methods found here:
     * https://gist.github.com/antonio081014/5939018 and from wikipedia.
     * 
     * They were changed to allow for pointer based node relations and needing
     * to store the nodes in an array.
     */
    private void rotateLeft(int index)
    {
        FieldFileNode q = (FieldFileNode) getFileHandle().get(index);
        FieldFileNode p = (FieldFileNode) getFileHandle().get(q.getRightPosition());
        FieldFileNode c = (FieldFileNode) getFileHandle().get(q.getLeftPosition());
        FieldFileNode a = (FieldFileNode) getFileHandle().get(p.getLeftPosition());
        FieldFileNode b = (FieldFileNode) getFileHandle().get(p.getRightPosition());

        q.setAddress(p.getAddress());
        int oldParent = q.getParentPosition();
        q.setParentPosition(p.getParentPosition());
        p.setAddress(index);
        p.setParentPosition(oldParent);

        p.setLeftPosition(getAddressOfNodeWithDefault(q));
        if (q != null)
            q.setParentPosition(p.getAddress());

        p.setRightPosition(getAddressOfNodeWithDefault(b));
        if (b != null)
            b.setParentPosition(p.getAddress());

        q.setLeftPosition(getAddressOfNodeWithDefault(c));
        if (c != null)
            c.setParentPosition(q.getAddress());

        q.setRightPosition(getAddressOfNodeWithDefault(a));
        if (a != null)
            a.setParentPosition(q.getAddress());

        getFileHandle().set(q.convert(), q.getAddress());
        getFileHandle().set(p.convert(), p.getAddress());
        if (c != null)
            getFileHandle().set(c.convert(), c.getAddress());
        if (a != null)
            getFileHandle().set(a.convert(), a.getAddress());
        if (b != null)
            getFileHandle().set(b.convert(), b.getAddress());
    }

    private void rotateRight(int index)
    {
        FieldFileNode q = (FieldFileNode) getFileHandle().get(index);
        FieldFileNode p = (FieldFileNode) getFileHandle().get(q.getLeftPosition());
        FieldFileNode c = (FieldFileNode) getFileHandle().get(q.getRightPosition());
        FieldFileNode a = (FieldFileNode) getFileHandle().get(p.getLeftPosition());
        FieldFileNode b = (FieldFileNode) getFileHandle().get(p.getRightPosition());

        q.setAddress(p.getAddress());
        int oldParent = q.getParentPosition();
        q.setParentPosition(p.getParentPosition());
        p.setAddress(index);
        p.setParentPosition(oldParent);

        p.setLeftPosition(getAddressOfNodeWithDefault(a));
        if (a != null)
            a.setParentPosition(p.getAddress());

        p.setRightPosition(getAddressOfNodeWithDefault(q));
        if (q != null)
            q.setParentPosition(p.getAddress());

        q.setLeftPosition(getAddressOfNodeWithDefault(b));
        if (b != null)
            b.setParentPosition(q.getAddress());

        q.setRightPosition(getAddressOfNodeWithDefault(c));
        if (c != null)
            c.setParentPosition(q.getAddress());

        getFileHandle().set(q.convert(), q.getAddress());
        getFileHandle().set(p.convert(), p.getAddress());
        if (c != null)
            getFileHandle().set(c.convert(), c.getAddress());
        if (a != null)
            getFileHandle().set(a.convert(), a.getAddress());
        if (b != null)
            getFileHandle().set(b.convert(), b.getAddress());
    }

    private int getAddressOfNodeWithDefault(FieldFileNode node)
    {
        if (node == null)
            return -1;
        return node.getAddress();
    }

    @Override
    public Iterator<Field> iterator()
    {
        return new BinaryTreeIterator(this, 0);
    }
}
