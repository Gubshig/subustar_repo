import serial

arduino = serial.Serial(port='COM11', baudrate=9600)

while True :
          if arduino.readable() :
                    data = arduino.readline().decode()
                    if 'dec' in data :
                              print(data[9:-1])
                    
