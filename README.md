# Chatop
## Context
Develop the back-end (API) of a website that allows tenants to contact the owners of the various properties they wish to rent.

## Technology
- Java 17
  
## Software and Tools
- IDE (IntelliJ)
- MySql Workbench or a MySql database installed
- VsCode ( install the **Live Server** plugin)

## Follow the procedure step by step
1. Clone the project `git clone https://github.com/Yoan-c/Chatop.git` . In the same directory, create a folder with the name `serverImage`. if you want to change the name of the folder, you'll have to change the name in the .env file.
2. Connect to MySql Workbench and import the `database.sql` file in the `Chatop` folder.
3. Execute the sql script to create the database (*chatop*) and tables *user,rental,message*
4. Open the **serverImage** folder with VsCode and click on the **Go Live** button at the bottom right of the editor to run the local server.
5. Open the Chatop project with IntelliJ
6. Modify the `DB_USERNAME` and `DB_PASSWORD` variables in the .env file. These are the variables that allow you to connect to Mysql. Check the other variables and adapt them to your environment if necessary.
7. Run the application with intellij

You can now use software like Postman or a front-end app (Angular, React etc...) to communicate with the back-end.

## API documentation
  Documentation is available with swagger at `http://127.0.0.1:3001/documentation` and in json format at `http://127.0.0.1:3001/documentation-api`. Please note that the documentation is only available if the API is running.

## WARNING

The application is configured to create and delete data from the database once shut down, if you want to **KEEP** the data then change this line `spring.jpa.hibernate.ddl-auto=create-drop` to `spring.jpa.hibernate.ddl-auto=update` in the application.properties file in Chatop/src/main/resources

## Author
Yoan-c

