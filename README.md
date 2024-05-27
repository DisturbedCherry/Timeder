## Running the project locally

### 1. Clone repository: 
```
git clone https://github.com/DisturbedCherry/Timeder.git
```

### 2. Add your connection string for PostgreSQL database 
<br>
In <strong>src/main/resources/application.properties</strong> file 
<br>
<br>
In this line:

![image](https://github.com/DisturbedCherry/Timeder/assets/105653616/5490259f-c5b4-473d-a2f7-9f24bd874e20)

change 
<br><br> jdbc:postgresql://localhost:5432/timeder
<br><br> to your connection string
<br><br> if your postgresql server is running on port 5432, you dont need to do anything


### 3. Open src/main/java/com/example/timeder/TimederApplication.java file and run it from IDE

### 4. Enable lombok annotation
![lombok](https://github.com/DisturbedCherry/Timeder/assets/105653616/df93cbf5-8d56-473c-b6f8-179391aa5917)
