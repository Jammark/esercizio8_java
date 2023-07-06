package esercizio;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Customer;
import model.Order;
import model.Product;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		Product product = new Product(0l, "Libro di scienza", "BOOKS", 40);
		Product product2 = new Product(1l, "Libro di cucina", "BOOKS", 20);
		Product product3 = new Product(2l, "Libro di cultura", "BOOKS", 110);

		Product product4 = new Product(3l, "Giocattolo", "Baby", 4);
		Product product5 = new Product(4l, "Pesi", "Boy", 18);

		List<Product> products = List.of(product, product2, product3, product4, product5);
		Predicate<Product> condition = p -> p.getCategory().equals("BOOKS");
		condition = condition.and(p -> p.getPrice() > 100f);
		List<Product> books = products.stream().filter(condition).collect(Collectors.toList());
		log.info("Esercizio 1");
		log.info(books.toString());

		Customer customer1 = new Customer(0l, "Pasquale", 8);
		Customer customer2 = new Customer(1l, "Marco", 2);

		Order o1 = new Order(0l, "pagato", LocalDate.now().minusDays(4), LocalDate.now().plusDays(3),
				Arrays.asList(product, product2), customer1);
		Order o2 = new Order(1l, "pending", LocalDate.now().minusDays(2), LocalDate.now().plusDays(5),
				Arrays.asList(product3, product4), customer1);

		Order o3 = new Order(3l, "annullato", LocalDate.of(2021, 3, 4), LocalDate.of(2021, 3, 10),
				Arrays.asList(product4, product5), customer2);

		List<Order> babyesOrders = Arrays.asList(o1, o2).stream().filter(o -> {
			return o.getProducts().stream().anyMatch((Product p) -> p.getCategory().equals("Baby"));
		}).collect(Collectors.toList());
		log.info("Esercizio 2");
		log.info(babyesOrders.toString());

		List<Product> boyProducts = products.stream().filter(p -> p.getCategory().equals("Boy"))
				.collect(Collectors.toList());
		boyProducts.forEach(p -> p.setPrice(p.getPrice() * 9 / 10));

		log.info("Esercizio 3");
		log.info(boyProducts.toString());

		Predicate<Order> dateP1 = o -> LocalDate.of(2021, 2, 1).isBefore(o.getOrderDate());
		dateP1 = dateP1.and(o -> LocalDate.of(2021, 4, 1).isAfter(o.getOrderDate()));
		List<Product> filtrati = Arrays.asList(o1, o2, o3).stream()
				.filter(o -> o.getCustomer() != null && o.getCustomer().getTier() == 2).filter(dateP1)
				.flatMap(o -> o.getProducts().stream()).collect(Collectors.toList());

		log.info("Esercizio 4");
		log.info(filtrati.toString());
	}

}
