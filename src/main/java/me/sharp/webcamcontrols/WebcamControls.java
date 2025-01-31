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
import java.util.HashMap;

public class WebcamControls implements ModInitializer {
	public static final String MOD_ID = "webcamcontrols";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final int PORT = 50000;
	private static final HashMap<String, String> gestures = new HashMap<>();

	@Override
	public void onInitialize() {
		gestures.put("turnLeft", "tp @s ~ ~ ~ ~-5 ~");
		gestures.put("turnRight", "tp @s ~ ~ ~ ~5 ~");
		gestures.put("turnUp", "tp @s ~ ~ ~ ~ ~-5");
		gestures.put("turnDown", "tp @s ~ ~ ~ ~ ~5");

		startListening();
	}

	public static void startListening() {
		new Thread(() -> {
			try (ServerSocket serverSocket = new ServerSocket(PORT)) {
				while (true) {
					try (Socket clientSocket = serverSocket.accept(); BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
						String message;
						while ((message = reader.readLine()) != null) {

							String command = gestures.get(message);

							if (command != null && !command.isEmpty()) {
								MinecraftClient.getInstance().player.networkHandler.sendCommand(command);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}
