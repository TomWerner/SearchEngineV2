package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.utilities.ByteConverter;

public class FieldFileNodeTest
{
    @Test
    public void testToAndFromByteArray()
    {
        FieldFileNode node = new FieldFileNode(new Field("value", "name"), 5);
        node.setLeftPosition(2);
        node.setRightPosition(3);
        node.setParentPosition(4);
        byte[] byteRepr = node.convert();
        FieldFileNode revertedNode = (FieldFileNode)ByteConverter.revert(byteRepr);
        assertEquals(node, revertedNode);
        assertEquals(node.getHeadOfLinkedListPosition(), revertedNode.getHeadOfLinkedListPosition());
        assertEquals(node.getLeftPosition(), revertedNode.getLeftPosition());
        assertEquals(node.getRightPosition(), revertedNode.getRightPosition());
        assertEquals(node.getParentPosition(), revertedNode.getParentPosition());
        assertEquals(node.getAddress(), revertedNode.getAddress());
        
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLongByteArrayError()
    {
        byte[] byteArray = new byte[FieldFileNode.MAX_SIZE + 1];
        FieldFileNode.revert(byteArray);
    }
    
    @Test
    public void testEquals()
    {
        FieldFileNode node1 = new FieldFileNode(new Field("value", "name"), 5);
        FieldFileNode node2 = new FieldFileNode(new Field("value", "name"), 5);
        FieldFileNode node3 = new FieldFileNode(new Field("value", "name2"), 5);

        assertTrue(node1.equals(node1));
        assertTrue(node1.equals(node2));
        assertFalse(node1.equals(node3));
        assertFalse(node1.equals(new Object()));
    }
}
