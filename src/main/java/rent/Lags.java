package rent;

import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;

public class Lags {
    private final List<Order> orders;

    public Lags(List<Order> orders) {
        this.orders = orders;
    }

    public int revenue_orders(List<Order> orders) {
        if(orders.size() == 0)
            return 0;
        Order order = orders.get(0);
        List<Order>select = new ArrayList<Order>();
        int start = order.getStart();
        int end   = start + order.getDuration();
        int final_end;
        int year  = start / 1000;
        int year_end = year * 1000 + 365;
        if(end > year_end) {
            int days = end - year_end;
            end = (year + 1) * 1000 + days;
        }
        for (Order o : orders) {
            if(o.getStart() >= end) {
                select.add(o);
            }
        }
        List<Order> comp = select;
        int revenueA = order.getPrice() + revenue_orders(comp);
        List <Order>rem = new ArrayList(orders);
        rem.remove(0);
        int rev2 = revenue_orders(rem);
        return Math.max(rev2, revenueA);
    }

    public void revenue() {
        int tot = 0;
        Comparator<Order> cm = new Comparator<Order>() {
            @Override
            public int compare(Order a, Order b) {
                return a.getStart() - b.getStart();
            }
        };
        Collections.sort(this.orders, cm);
        for(int i=0; i<this.orders.size()-1; i++) {
            tot++;
            assert this.orders.get(i).getStart() <= this.orders.get(i+1).getStart();
        }

        System.out.format("%d %d\n", tot+1, revenue_orders(this.orders));
    }
};

