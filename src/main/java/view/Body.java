package view;

import javax.swing.JPanel;

import view.pages.Customer_Management;
import view.pages.Employee_Management;
import view.pages.Food_Management;
import view.pages.Revenue_Management;
import view.pages.Sell_Management;
import view.pages.Statistical_Management;
import view.pages.Store_Management;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JLayeredPane;

public class Body extends JPanel{
	private Customer_Management customer;
	private Store_Management store;
	private Sell_Management sell;
	private Employee_Management employee;
	private Food_Management food;
	private Revenue_Management revenue;
	private Component statistical;
	private CardLayout cardlayout;

	public Body() {
		setSize(1250, 823);
		
		// Khởi tạo CardLayout
        cardlayout = new CardLayout();
        setLayout(cardlayout);
		
		store = new Store_Management();
		add(store, "store");
		
		sell = new Sell_Management();
		add(sell, "sell");
		
		employee = new Employee_Management();
		add(employee, "employee");
		
		food = new Food_Management();
		add(food, "food");
		
		customer = new Customer_Management();
		add(customer, "customer");
		
		revenue = new Revenue_Management();
		add(revenue, "revenue");
		
		statistical = new Statistical_Management();
		add(statistical, "statistical");
		
		cardlayout.show(this, "store");
	}
	
	public CardLayout getCardLayout() {
		return cardlayout;
	}
	
	public void showPanel(String name) {
        cardlayout.show(this, name);
//        System.out.println(name);
    }
}
