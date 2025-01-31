import socket

gestures = ["look left", "look right", "look up", "look down"]
message = input(f"What gesture do you want to send?\n{gestures}\n>>> ")

if message == "look left" or message == "look right" or message == "look up" or message == "look down":
    with socket.create_connection(("localhost", 50000)) as s:
        s.sendall(message.encode())
else:   
    print("Enter a valid gesture")
