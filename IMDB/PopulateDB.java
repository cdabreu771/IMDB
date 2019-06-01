package IMDB;
import java.io.*;
import java.text.DecimalFormat;
import java.sql.*;

// movies.dat movie_genres.dat movie_countries.dat movie_locations.dat tags.dat 
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
	
        
        for(int m = 0; m < 1;m++){
            String inputString = "";
            String delimiter = "\\.";
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
	
            String location = args[m];
            String datTableNames[] = args[m].split(delimiter);
            String tableName = datTableNames[0];
            
            System.out.println(tableName);
            File file = new File(location);
                
            try {
                Statement statement = connection.createStatement();
                System.out.print("Deleting previous tuples ..."); 
                statement.executeUpdate("DELETE FROM " + tableName); 
                String query = "Select * FROM " + tableName;
                ResultSet resultSet = statement.executeQuery(query);
                
                inputString = "?, ?, ?, ?, ?";
                
                
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line = "";
                    bufferedReader.readLine();

                    while ((line = bufferedReader.readLine()) != null) {
                    
                        String [] lineInfo;
                        lineInfo=line.split("\\t",-1); 
                        String avgRatings = "";
                        String avgReviews = "";
                        double ac_ratings;
                        double rt_ratings;
                        double aud_ratings;
                        double ac_reviews;
                        double rt_reviews;
                        double aud_reviews;
                  
                        for (int y = 0; y <lineInfo.length; y++){
                            System.out.print(lineInfo[y] + " ");
                        }
                        if(lineInfo[7].equals("\\N")){
                            lineInfo[7] = "0";
                            ac_ratings = 0;
                        }
                       
                        if(lineInfo[12].equals("\\N")){
                        
                            lineInfo[12] = "0";
                            rt_ratings = 0;
                        } 
                        
                        if(lineInfo[17].equals("\\N")){
                       
                            lineInfo[17] = "0";
                            aud_ratings = 0;
                        }
                        
                        
                            ac_ratings = Double.parseDouble(lineInfo[7]);
                            rt_ratings = Double.parseDouble(lineInfo[12]);
                            aud_ratings = Double.parseDouble(lineInfo[17]);
                            avgRatings = decimalFormat.format((ac_ratings + rt_ratings + aud_ratings)/3);
                        
                        
                        
                        if(lineInfo[8].equals("\\N")){
                            lineInfo[8]="0";
                            ac_reviews = 0;
                        }
                       
                       if(lineInfo[13].equals("\\N")){
                           lineInfo[13] = "0";
                           rt_ratings = 0;
                        }
                        if(lineInfo[18].equals("\\N")){
                        
                            lineInfo[18] = "0";
                            aud_ratings =0;
                        }
                                                 
                            ac_reviews = Double.parseDouble(lineInfo[8]);
                            rt_reviews = Double.parseDouble(lineInfo[13]);
                            aud_reviews = Double.parseDouble(lineInfo[18]);
                            avgReviews = decimalFormat.format((ac_reviews + rt_reviews + aud_reviews)/3);
                        
        
                        System.out.println("Inserting Data...");
                        PreparedStatement pstatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES(" + inputString + ")");
                        pstatement.setString(1, lineInfo[0]);
                        pstatement.setString(2, lineInfo[1]);
                        pstatement.setString(3, lineInfo[5]);
                        pstatement.setString(4, avgRatings);
                        pstatement.setString(5, avgReviews);
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
       
        
        for (int i = 1; i < args.length; i++){
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