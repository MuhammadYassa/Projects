package weather;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class WeatherApp extends JFrame implements ActionListener, KeyListener{
	
	//background components.
	public Image background;
	public MyPanel panel;
	
	//inner components.
	public ImageIcon searchIcon;
	public JTextField textField;
	public JButton searchButton;
	public JLabel WeatherIconLabel;
	public JLabel conditionsLabel;
	public JLabel feelsLikeLabel;
	public JLabel tempLabel;
	public JLabel humidity;
	public JLabel windSpeed;
	public JLabel windSpeedTitle;
	public JLabel humidityTitle;
	
	//Variables
	public static String location;
	public weatherTranscript transcript;
	public WeatherIcon icon;
	public static Color accentColor = new Color(0, 99, 178);
	
	
	public WeatherApp() {
		// initialize initial Transcript for New York.
		try {
			transcript = apiCall("NewYork,USA");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// images.
		ImageIcon frameIcon = new ImageIcon("weather-icon.png");
		background = new ImageIcon("background1.png").getImage();	
		searchIcon = new ImageIcon("search.png");
		icon = new WeatherIcon(transcript.currentConditions.getIcon());
		
		//windSpeedLabel.
		windSpeedTitle = new JLabel();
		windSpeedTitle.setBounds(228,480,150,30);
		windSpeedTitle.setText("Wind Speed");
		windSpeedTitle.setFont(new Font("Helvetica", Font.PLAIN, 18));
		windSpeedTitle.setForeground(accentColor);
		windSpeed = new JLabel();
		windSpeed.setBounds(160, 450, 200, 100);
		windSpeed.setText(String.valueOf(transcript.currentConditions.getWindSpeed()) + "mph");
		windSpeed.setFont(new Font("Helvetica", Font.ITALIC, 15));
		windSpeed.setForeground(new Color(56, 97, 130));
		windSpeed.setIcon(new ImageIcon("windSpeed.png"));
		windSpeed.setVerticalTextPosition(JLabel.BOTTOM);
		
		//humidityLabel.
		humidityTitle = new JLabel();
		humidityTitle.setBounds(78,479,150,30);
		humidityTitle.setText("Humidity");
		humidityTitle.setFont(new Font("Helvetica", Font.PLAIN, 18));
		humidityTitle.setForeground(accentColor);
		humidity = new JLabel();
		humidity.setBounds(10, 449, 200, 100);
		humidity.setText(String.valueOf(transcript.currentConditions.getHumidity()) + "%");
		humidity.setFont(new Font("Helvetica", Font.ITALIC, 15));
		humidity.setForeground(new Color(56, 97, 130));
		humidity.setIcon(new ImageIcon("humidity.png"));
		humidity.setVerticalTextPosition(JLabel.BOTTOM);
		
		//tempLabel.
		tempLabel = new JLabel();
		tempLabel.setBounds(0, 370, 325, 30);
		tempLabel.setText(String.valueOf(transcript.currentConditions.getTemp()) + "°F");
		tempLabel.setFont(new Font("HelveticaNeue", Font.BOLD, 30));
		tempLabel.setForeground(accentColor);
		tempLabel.setHorizontalAlignment(JLabel.CENTER);
		
		
		//conditionsLabel.
		conditionsLabel = new JLabel();
		conditionsLabel.setBounds(0, 400, 325, 30);
		conditionsLabel.setText(transcript.currentConditions.getConditions());
		conditionsLabel.setFont(new Font("HelveticaNeue", Font.PLAIN, 25));
		conditionsLabel.setForeground(accentColor);
		conditionsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		//Weather Icon Label
		WeatherIconLabel = new JLabel();
		WeatherIconLabel.setBounds (35,110,256,256);
		WeatherIconLabel.setIcon(icon.icon);
		
		// Text Box.
		textField = new JTextField();
		textField.setBounds(45, 45,285, 30);
		textField.setFont(new Font("Dialog",Font.PLAIN,15));
		textField.setText("City/State");
		textField.addKeyListener(this);
		
		// Search Button.
		searchButton = new JButton();
		searchButton.setBounds(5,45, 35, 30);
		searchButton.setFocusable(false);
		searchButton.setBorder(BorderFactory.createEtchedBorder());
		searchButton.addActionListener(this);
		searchButton.setIcon(searchIcon);
		searchButton.setBackground(accentColor);
		searchButton.setForeground(Color.white);
		searchButton.addKeyListener(this);
		
		// My Panel.
		panel = new MyPanel();
		
		// Frame
		this.setTitle("Weather");
		this.setResizable(false);
		this.setSize(350,625);
		this.setLayout(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(frameIcon.getImage());
		this.add(panel);
	}

	// MyPanel class.
	public class MyPanel extends JPanel{
		
		public MyPanel() {
			this.setBounds(0,0, 350,625);
			this.setLayout(null);
			this.setBackground(new Color(141, 161, 165));
			this.add(textField);
			this.add(searchButton);
			this.add(WeatherIconLabel);
			this.add(conditionsLabel);
			this.add(tempLabel);
			this.add(humidity);
			this.add(windSpeed);
			this.add(windSpeedTitle);
			this.add(humidityTitle);
		}
	}
	
	// WeatherIcons class.
	public class WeatherIcon {
		public String iconId;
		public ImageIcon icon;
		
		public WeatherIcon(String s) {
			iconId = s;
			this.icon = new ImageIcon (iconId + ".png");
		}	
	}
	
	// updates all weather info Displayed on screen.
	public void updateWeatherData() {
		icon = new WeatherIcon(transcript.currentConditions.getIcon());
		WeatherIconLabel.setIcon(icon.icon);
		WeatherIconLabel.repaint();
		conditionsLabel.setText(transcript.currentConditions.getConditions());
		tempLabel.setText(String.valueOf(transcript.currentConditions.getTemp()) + "°F");
		humidity.setText(String.valueOf(transcript.currentConditions.getHumidity()) + "%");
		windSpeed.setText(String.valueOf(transcript.currentConditions.getWindSpeed()) + "mph");
	}
	
	// Action & Key Listener methods.
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			location = textField.getText().replaceAll("\\s", "");
			try {
				transcript = apiCall(location);
				updateWeatherData();
			} catch (Exception e1) {
				textField.setText("Cant Find Location");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton) {
			location = textField.getText().replaceAll("\\s", "");
			try {
				transcript = apiCall(location);
				updateWeatherData();
			} catch (Exception e1) {
				textField.setText("Cant Find Location");
			}
		}
	}
	
	//apiCall
	public static weatherTranscript apiCall(String location) throws Exception {
		String apiKey = "B34X367Y3QTJ8AF49JHGCNNM2";
		weatherTranscript weather = new weatherTranscript();
		Gson gson = new Gson();
		
		HttpRequest getRequest = HttpRequest.newBuilder()
				.uri(new URI("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + location + "?key=B34X367Y3QTJ8AF49JHGCNNM2"))
				.header("Authorization", apiKey)
				.GET ()
				.build();
		
		HttpClient httpClient = HttpClient.newHttpClient();
		
		HttpResponse <String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());
		
		weather = gson.fromJson (getResponse.body(), weatherTranscript.class);
		
		return weather;
	}
	
	//Transcript class
	public static class weatherTranscript {
		public CurrentConditions currentConditions;
		
		public static class CurrentConditions{
			@SerializedName("humidity")
			public float humidity;
			
			@SerializedName("windspeed")
			public float windSpeed;
			
			@SerializedName("conditions")
			public String conditions;
			
			@SerializedName("temp")
			public float temp;
			
			@SerializedName("icon")
			public String icon;

			public String getConditions() {
				return conditions;
			}

			public void setConditions(String conditions) {
				this.conditions = conditions;
			}

			public float getTemp() {
				return temp;
			}

			public void setTemp(float temp) {
				this.temp = temp;
			}

			public String getIcon() {
				return icon;
			}

			public void setIcon(String icon) {
				this.icon = icon;
			}

			public float getHumidity() {
				return humidity;
			}

			public void setHumidity(float humidity) {
				this.humidity = humidity;
			}

			public float getWindSpeed() {
				return windSpeed;
			}

			public void setWindSpeed(float windSpeed) {
				this.windSpeed = windSpeed;
			}
		}
	}
	
	//main method.
	public static void main (String [] args) {
		new WeatherApp();
	}
}