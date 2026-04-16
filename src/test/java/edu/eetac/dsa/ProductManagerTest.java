package edu.eetac.dsa;

import edu.eetac.dsa.models.Order;
import edu.eetac.dsa.models.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProductManagerTest {

    ProductManager pm;

    @Before
    public void setUp() {
        pm = ProductManagerImpl.getInstance();
        pm.clear();

        pm.addUser("U1", "Roger");

        pm.addProduct("P1", "Cafe", 1.2);
        pm.addProduct("P2", "Bocadillo", 3.5);
        pm.addProduct("P3", "Agua", 1.0);
    }

    @After
    public void tearDown() {
        pm.clear();
    }

    @Test
    public void testAddOrder() {
        Order o = new Order("U1");

        o.addItem(pm.getProduct("P1"), 2);
        o.addItem(pm.getProduct("P2"), 1);

        pm.addOrder(o);

        Assert.assertEquals(1, pm.numOrders());
        Assert.assertEquals("U1", o.getUserId());
        Assert.assertEquals(2, o.getItems().size());
    }

    @Test
    public void testDeliverOrder() {
        Order o = new Order("U1");

        o.addItem(pm.getProduct("P1"), 2);
        o.addItem(pm.getProduct("P2"), 1);

        pm.addOrder(o);

        Order delivered = pm.deliverOrder();

        Assert.assertNotNull(delivered);
        Assert.assertTrue(delivered.isServed());
        Assert.assertEquals(0, pm.numOrders());

        Product p1 = pm.getProduct("P1");
        Product p2 = pm.getProduct("P2");

        Assert.assertEquals(2, p1.getSales());
        Assert.assertEquals(1, p2.getSales());

        List<Order> userOrders = pm.getOrdersByUser("U1");
        Assert.assertEquals(1, userOrders.size());
    }
}