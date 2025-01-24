package me.sharp.webcamcontrols;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WebcamControls implements ModInitializer {
	public static final String MOD_ID = "webcamcontrols";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final int PORT = 50000;

	@Override
	public void onInitialize() {
		startListening();
	}

	public static void startListening() {
		new Thread(() -> {
			try (ServerSocket serverSocket = new ServerSocket(PORT)) {
				System.out.println("Listening on port " + PORT + "...");
				while (true) {
					try (Socket clientSocket = serverSocket.accept(); BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
						String message;
						while ((message = reader.readLine()) != null) {

							MinecraftClient.getInstance().player.sendMessage(Text.of(message), false);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}