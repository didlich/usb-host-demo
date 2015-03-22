# usb-host-demo
demonstrate usb host access in android

# Setup

You can use your USB-Stick to run the demo. Find out which vendor and product id the USB-Stick has, 
i.e.: in Linux the /var/log/syslog provides this information if the USB-Stick is attached.

modify 

    app/src/main/res/xml/device_filter.xml

for your USB-Storage device and set 
    vendor-id
    product-id

NOTE: vendor-id and product-id have to be in decimal notation, convert them before insert in devvice_filter.xml
i.e: idVendor=14cd, idProduct=8123 -> idVendor=5325, idProduct=33059

# Build

Trigger this command to initialize the gradle build system:

    ./gradlew wrapper
    
To build the project type:

    ./gradlew build