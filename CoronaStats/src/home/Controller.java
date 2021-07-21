package home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;




public class Controller implements Initializable{
	
    @FXML
    private AnchorPane country_data;

    @FXML
    private AnchorPane total_corona;

    @FXML
    private AnchorPane news;

    @FXML
    private AnchorPane continent;

    @FXML
    private JFXButton Countrie_btn;

    @FXML
    private JFXButton TotalData_btn;

    @FXML
    private JFXButton News_btn;

    @FXML
    private JFXButton Continent_btn;
    
    @FXML
    private Button CloseButton;
    
    @FXML
    private Label continent_lbl;

    @FXML
    private Label totalCases_lbl;

    @FXML
    private Label newCases_lbl;

    @FXML
    private Label totalDeaths_lbl;

    @FXML
    private Label newDeaths_lbl;

    @FXML
    private Label totalRecovered_lbl;

    @FXML
    private Label activeCases_lbl;
    
    @FXML
    private Label country_lbl;

    @FXML
    private Label totalCases_lbl_country;

    @FXML
    private Label newCases_lbl_country;

    @FXML
    private Label totalDeaths_lbl_country;

    @FXML
    private Label newDeaths_lbl_country;

    @FXML
    private Label totalRecovered_lbl_country;

    @FXML
    private Label activeCases_lbl_country;
    
    @FXML
    private ChoiceBox<String> continent_choice;
    
    @FXML
    private ChoiceBox<String> country_choice;
    
    @FXML
    private ScrollPane news_scroll;
    
    @FXML
    private VBox vbox_news;
    
    @FXML
    private Label totalcase;

    @FXML
    private Label totaldeaths;

    @FXML
    private Label totalrecovered;
    
    @FXML
    private PieChart totalDataChart;
    
    
    @FXML
    private void handleCliks(MouseEvent event)
    {
    	if(event.getSource() == Countrie_btn) {
    		country_data.toFront();
    		getCountrieData();
    	}
    	if(event.getSource() == TotalData_btn) {
    		total_corona.toFront();;
    		getTotal();
    	}
    	if(event.getSource() == News_btn) {
    		news_scroll.toFront();
    		get_news();
    	}
    	if(event.getSource() == Continent_btn) {
    		continent.toFront();
    		getContinentData();
    	}
    }
    
    private String[] continent_name = {};
    private String[] country_name = {};
    
    private JSONObject jsonObject = new JSONObject();
    private JSONArray json1 = new JSONArray();
    private JSONObject json2 = new JSONObject();
    private JSONObject jsontotaldata = new JSONObject();
    
	public void getContinentData() {
		
		 continent_lbl.setText(null);
	     totalCases_lbl.setText(null);
	     newCases_lbl.setText(null);
	     totalDeaths_lbl.setText(null);
	     newDeaths_lbl.setText(null);
	     totalRecovered_lbl.setText(null);
	     activeCases_lbl.setText(null);
	     
	     continent_choice.getItems().clear();
		
	  
		try {
					URL url = new URL("https://api.collectapi.com/corona/continentData");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					
					connection.setRequestProperty("Content-Type", "application/json");
					connection.setRequestProperty("authorization", "apikey #########");
					
					connection.setConnectTimeout(5000);
					connection.setReadTimeout(5000);
					
						StringBuilder content;
		
						try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
						    String line;
						    content = new StringBuilder();
						    while ((line = input.readLine()) != null) {
			
						        content.append(line);
						        content.append(System.lineSeparator());
						    }
						    
						} finally {
						    connection.disconnect();
						}
					
						try {
							
						     jsonObject = new JSONObject(content.toString());					     
						     json1 = jsonObject.getJSONArray("result");					     
						     
						     for (int i = 0; i < 6; i++) {
						    	 
						    	 json2 = json1.getJSONObject(i);
						    	 
						    	 //System.out.println(json2.getString("continent").toString());
						    	 
						    	 continent_name = addX(i, continent_name, json2.getString("continent"));
						    	 
						    	//System.out.println(continent_name[i]);
							}
						     
			
						}catch (JSONException err){
						     System.out.println(err);
						}
				
			} catch (MalformedURLException e) {
	
				e.printStackTrace();
			} catch (IOException e) {
	
				e.printStackTrace();
			}
	 
	  
		continent_choice.getItems().addAll(continent_name);

	}

	public void get_continent_result(ActionEvent event) {
		
		for (int i = 0; i < 6; i++) {
			
			json2 = json1.getJSONObject(i);
			
			if( continent_choice.getValue() == json2.getString("continent")) {
				
			     continent_lbl.setText(json2.getString("continent").toString());
			     totalCases_lbl.setText(json2.getString("totalCases").toString());
			     newCases_lbl.setText(json2.getString("newCases").toString());
			     totalDeaths_lbl.setText(json2.getString("totalDeaths").toString());
			     newDeaths_lbl.setText(json2.getString("newDeaths").toString());
			     totalRecovered_lbl.setText(json2.getString("totalRecovered").toString());
			     activeCases_lbl.setText(json2.getString("activeCases").toString());
				
			}
		}
	}
	
	public void getCountrieData() {
		
	     country_lbl.setText(null);
	     totalCases_lbl_country.setText(null);
	     newCases_lbl_country.setText(null);
	     totalDeaths_lbl_country.setText(null);
	     newDeaths_lbl_country.setText(null);
	     totalRecovered_lbl_country.setText(null);
	     activeCases_lbl_country.setText(null);
	     
	     country_choice.getItems().clear();
		
		
		try {
				URL url = new URL("https://api.collectapi.com/corona/countriesData");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("authorization", "apikey ########");
				
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				
					StringBuilder content;
	
					try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					    String line;
					    content = new StringBuilder();
					    while ((line = input.readLine()) != null) {
		
					        content.append(line);
					        content.append(System.lineSeparator());
					    }
					    
					} finally {
					    connection.disconnect();
					}
				
					try {
						
					     jsonObject = new JSONObject(content.toString());					     
					     json1 = jsonObject.getJSONArray("result");					     
					     
					     for (int i = 0; i < 200; i++) {
					    	 
					    	 json2 = json1.getJSONObject(i);
					    	 
					    	 //System.out.println(json2.getString("continent").toString());
					    	 
					    	 country_name = addX(i, country_name, json2.getString("country"));
					    	 
					    	//System.out.println(country_name[i]);
						}
					     
		
					}catch (JSONException err){
					     System.out.println(err);
					}
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		country_choice.getItems().addAll(country_name);

	}
	
	public void get_country_result(ActionEvent event) {
		
		for (int i = 0; i < 200; i++) {
			
			json2 = json1.getJSONObject(i);
			
			if( country_choice.getValue() == json2.getString("country")) {
				
			     country_lbl.setText(json2.getString("country").toString());
			     totalCases_lbl_country.setText(json2.getString("totalCases").toString());
			     newCases_lbl_country.setText(json2.getString("newCases").toString());
			     totalDeaths_lbl_country.setText(json2.getString("totalDeaths").toString());
			     newDeaths_lbl_country.setText(json2.getString("newDeaths").toString());
			     totalRecovered_lbl_country.setText(json2.getString("totalRecovered").toString());
			     activeCases_lbl_country.setText(json2.getString("activeCases").toString());
				
			     System.out.println(json2.getString("country").toString());
			     System.out.println(json2.getString("totalCases").toString());
			     System.out.println(json2.getString("newCases").toString());
			     System.out.println(json2.getString("totalDeaths").toString());
			     System.out.println(json2.getString("newDeaths").toString());
			     System.out.println(json2.getString("totalRecovered").toString());
			     System.out.println(json2.getString("activeCases").toString());
			     
			}
		}
	}

	public void get_news() {
		
		vbox_news.getChildren().clear();
		
		try {
				URL url = new URL("https://api.collectapi.com/corona/coronaNews");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("authorization", "apikey ########");
				
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				
					StringBuilder content;
	
					try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					    String line;
					    content = new StringBuilder();
					    while ((line = input.readLine()) != null) {
		
					        content.append(line);
					        content.append(System.lineSeparator());
					    }
					    
					} finally {
					    connection.disconnect();
					}
				
					try {
						
					     jsonObject = new JSONObject(content.toString());					     
					     json1 = jsonObject.getJSONArray("result");		
					     
					     vbox_news.setFillWidth(true);
					     vbox_news.setSpacing(15);
					     
					     for (int i = 0; i < 15; i++) {
					    	 
					    	 json2 = json1.getJSONObject(i);
					    	 System.out.println(json2.get("name"));
					    	 
					    	 Pane pane = new Pane();
					    	 VBox invbox = new VBox();
					    	 Label lbl = new Label();
					    	 Label lbl_descripton = new Label();
					    	 Label lbl_src = new Label();
					    	 
					         String imageSource = json2.getString("image");
					    	 System.out.println(json2.getString("image"));
					         ImageView imageView = new ImageView();
					         imageView.setImage(new Image(imageSource));
					         imageView.setFitWidth(300);
					         imageView.setFitHeight(170);
					         imageView.setLayoutX(15);
					         pane.getChildren().add(imageView);
					    	 
					    	 lbl.setText(json2.getString("name"));
					    	 lbl.setWrapText(true);
					    	 //lbl.setLayoutX(320);
					    	 //lbl.setLayoutY(15);
					    	 lbl.setMinWidth(600);
					    	 lbl.setMaxWidth(600);
					    	 lbl.setStyle(
					    			  "-fx-font-size: 15px;"
					    	 		+ "-fx-text-fill: #fff;"
					    			+ "-fx-font-weight: bold;");

					    	 lbl_descripton.setText(json2.getString("description"));
					    	 lbl_descripton.setWrapText(true);
					    	 //lbl_descripton.setLayoutX(225);
					    	 //lbl_descripton.setLayoutY(100);
					    	 lbl_descripton.setMinWidth(600);
					    	 lbl_descripton.setMaxWidth(600);
					    	 lbl_descripton.setStyle(
					    			  "-fx-font-size: 14px;"
						    	 	+ "-fx-text-fill: #fff;");
					    	 
					    	 lbl_src.setText(json2.getString("source"));
					    	 lbl_src.setWrapText(true);
					    	 lbl.setLayoutX(550);
					    	 //lbl.setLayoutY(15);
					    	 lbl_src.setMinWidth(600);
					    	 lbl_src.setMaxWidth(600);
					    	 lbl_src.setStyle(
					    			 "-fx-font-size: 15px;"
					    	 		+ "-fx-text-fill: #fff;"
					    			+ "-fx-font-weight: bold;");
					    	 
					    	 invbox.setLayoutX(320);
					    	 invbox.setLayoutY(15);
					    	 invbox.setSpacing(5);
					    	 
					    	 invbox.getChildren().add(lbl);
					    	 invbox.getChildren().add(lbl_descripton);
					    	 invbox.getChildren().add(lbl_src);
					    	 pane.getChildren().add(invbox);
					    	 
					    	 //pane.prefHeight(200);
					    	 //pane.setMinWidth(760);
					    	 //pane.setMinSize(760, 113);
					    	 pane.setStyle("-fx-background-color: #0A52CC;"
					    	 		+ "-fx-background-radius: 1em;");
					    	 
					    	 
					    	 vbox_news.getChildren().add(new Pane(pane));
					    
						}
					     
					}catch (JSONException err){
					     System.out.println(err);
					}
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	public void getTotal() {
		
		totalDataChart.getData().clear();
		
		try {
				URL url = new URL("https://api.collectapi.com/corona/totalData");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("authorization", "apikey ########");
				
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				
					StringBuilder content;
	
					try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					    String line;
					    content = new StringBuilder();
					    while ((line = input.readLine()) != null) {
		
					        content.append(line);
					        content.append(System.lineSeparator());
					    }
					    
					} finally {
					    connection.disconnect();
					}
				
					try {
						
					     jsonObject = new JSONObject(content.toString());					     
					     jsontotaldata = (JSONObject) jsonObject.get("result");					     
					     
					    	 
					    System.out.println(jsontotaldata);
					    	 
		
					}catch (JSONException err){
					     System.out.println(err);
					}
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
		System.out.println(jsontotaldata.get("totalCases"));
		System.out.println(jsontotaldata.getString("totalDeaths"));
		System.out.println(jsontotaldata.getString("totalRecovered"));
		
		BigDecimal value1 = new BigDecimal(jsontotaldata.getString("totalDeaths").replace(",", ""));
		BigDecimal value2 = new BigDecimal(jsontotaldata.getString("totalRecovered").replace(",", ""));
		
		PieChart.Data totaldeath = new PieChart.Data ("Total Deaths", value1.doubleValue());
		PieChart.Data totalrev = new PieChart.Data ("Total Recovered", value2.doubleValue());

		totalDataChart.getData().add(totaldeath);
		totalDataChart.getData().add(totalrev);
		
		totalcase.setText(jsontotaldata.getString("totalCases"));
		totaldeaths.setText(jsontotaldata.getString("totalDeaths"));
		totalrecovered.setText(jsontotaldata.getString("totalRecovered"));

	}
		
    public static String[] addX(int n, String arr[], String x)
    {
        int i;
  
        // create a new array of size n+1
        String newarr[] = new String[n + 1];
  
        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];
  
        newarr[n] = x;
  
        return newarr;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		getCountrieData();
		//getContinentData();
		
		continent_choice.setOnAction(this::get_continent_result);
		country_choice.setOnAction(this::get_country_result);
	}
}
