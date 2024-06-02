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

### 3. Open pgAdmin and create empty "timeder" database 

After logging in to your database engine, press right click on "Databases" and choose Create > Database
<br>
![baza1](https://github.com/DisturbedCherry/Timeder/assets/105653616/2de0a07f-e745-40b7-b9c4-2ead58ec07db)
<br><br>
Add name "timeder" in "Database" input field
<br>
![baza2](https://github.com/DisturbedCherry/Timeder/assets/105653616/8aa390f0-f1b9-45cd-ba66-d50c352bceec)
<br><br> 
Press "Save" button

### 4. Open src/main/java/com/example/timeder/TimederApplication.java file and run it from IDE

### 5. Enable lombok annotation in the lower right corner
![lombok](https://github.com/DisturbedCherry/Timeder/assets/105653616/df93cbf5-8d56-473c-b6f8-179391aa5917)
