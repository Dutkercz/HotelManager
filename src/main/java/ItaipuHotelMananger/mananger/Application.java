package ItaipuHotelMananger.mananger;

import ItaipuHotelMananger.mananger.ui.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Autowired
	Menu menu;

	@Override
	public void run(String... args) throws Exception {
		menu.showMenu();
	}
}
