package ItaipuHotelManager.manager;

import ItaipuHotelManager.manager.frontend.HotelManagementUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		try {
			System.setProperty("java.awt.headless", "false");
			Thread.sleep(20000);
			SwingUtilities.invokeLater(HotelManagementUI::createAndShowGUI);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}
}
