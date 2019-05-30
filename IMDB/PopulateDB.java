package IMDB;
import java.io.*;
import java.sql.*;

// movies.dat movie_genres.dat movie_directors.dat movie_actors.dat movie_countries.dat movie_locations.dat tags.dat movie_tags.dat user_taggedmovies.dat USER_TAGGEDMOVIES_TIMESTAMPS.dat USER_RATEDMOVIES.dat USER_RATEDMOVIES_TIMESTAMPS.dat
public class PopulateDB {
    
    private static String host = "localhost";
    private static String DBName = "CAMILLE";
    private static String userName = "Scott";
    private static String password = "tiger";
    private static String port = "1521";

    public static void main(String[] args) {
        Connection connection = null;
    	System.out.println("Attempting Oracle JDBC Connection Configuration");
    	try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String DBUrl = "jdbc:oracle:thin:@" + host + ":" + port + "/" + DBName;
            connection = DriverManager.getConnection(DBUrl, userName, password);
    	} catch (SQLException sqlException) {
            System.out.println("Failed : Connection failed ");
            sqlException.printStackTrace();
    	} catch (ClassNotFoundException notFoundException) {
            System.out.println("Failed : ClassNotFound Exception Occurred");
            notFoundException.printStackTrace();
    	}
    
    	if (connection != null) {
            System.out.println("SUCCESS : Connection successful!");
    	} 
    	else {
            System.out.println("FAILED : Unable to make connection!");
    	}
	
        for (int i = 0; i < args.length; i++){
            String inputString = "";
            String delimiter = "\\.";
             
	
            String location = args[i];
            String datTableNames[] = args[i].split(delimiter);
            String tableName = datTableNames[0];
            
            System.out.println(tableName);
                
            File file = new File(location);
                
            try {
                Statement statement = connection.createStatement();
                System.out.print("Deleting previous tuples ..."); 
                statement.executeUpdate("DELETE FROM " + tableName); 
                String query = "Select * FROM " + tableName;
                ResultSet resultSet = statement.executeQuery(query);
                ResultSetMetaData resultsMetaData = resultSet.getMetaData();
                int columnCount = resultsMetaData.getColumnCount();

                for (int j = 0; j < columnCount; j++) {
                    if(j < columnCount-1) {
                        inputString = inputString + "?, ";
                    }
                    else{
                        inputString = inputString + "?";
                    }
                }
                
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line = "";
                    bufferedReader.readLine();
                 

                    while ((line = bufferedReader.readLine()) != null) {
                    
                        String [] lineInfo = new String[columnCount];
                        lineInfo=line.split("\\t",-1); 
                        for (int y = 0; y <lineInfo.length; y++){
                            System.out.print(lineInfo[y] + " ");
                        }
                   
                        System.out.println("Inserting Data...");
                        PreparedStatement pstatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES(" + inputString + ")");
                        for(int k = 0; k <columnCount;k++){
                            pstatement.setString(k+1,lineInfo[k]);
                        }
                        pstatement.executeUpdate();
                        pstatement.close();
                        System.out.println("SUCCESSFUL READ IN");
                        System.out.println(lineInfo);

                    }
           
		}catch(FileNotFoundException ex) {
                    System.out.println("FileNotFoundException Occurred");
		}catch(IOException ex) {
                    System.out.println("IOException Occurred");
		}
            }catch (SQLException e) {
                while (e != null) {
                System.out.println("Message: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("Vendor Error: " + e.getErrorCode());

                e = e.getNextException();
                }
            }
        }
        
        
    }
}