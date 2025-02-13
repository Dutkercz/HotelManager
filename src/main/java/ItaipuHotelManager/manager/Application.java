package ItaipuHotelManager.manager;

import ItaipuHotelManager.manager.frontend.HotelManagementUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(Application.class, args);
        Thread.sleep(21000);
        SwingUtilities.invokeLater(HotelManagementUI::createAndShowGUI);
    }
}
