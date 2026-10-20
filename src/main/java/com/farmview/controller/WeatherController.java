package com.farmview.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Controller
public class WeatherController {
	
	@GetMapping("/weather")
	public String weather(Model model) {
		
		String url = "https://api.open-meteo.com/v1/forecast?latitude=36.15841&longitude=137.905011&hourly=precipitation_probability,wind_speed_10m&current=temperature_2m,relative_humidity_2m,precipitation,wind_speed_10m&timezone=Asia/Tokyo&utm_source=chatgpt.com";
		
		RestClient client = RestClient.create();
		
		String response = client.get()
				.uri(url)
				.retrieve()
				.body(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode json = mapper.readTree(response);
		
		double temperature = json.get("current")
								.get("temperature_2m")
								.asDouble();
		
		int humidity = json.get("current")
							.get("relative_humidity_2m")
							.asInt();
		
		double precipitation = json.get("current")
									.get("precipitation")
									.asDouble();
		
		double windSpeed = json.get("current")
								.get("wind_speed_10m")
								.asDouble();
		
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime currentHour = now.withMinute(0)
										.withSecond(0)
										.withNano(0);
		
		JsonNode hourly = json.get("hourly");
		
		List<String> forecastTimes = new ArrayList<>();
		List<Integer> rainProbabilities = new ArrayList<>();
		List<Double> windSpeeds = new ArrayList<>();
		
		int count = 0;
		
		for (int i = 0; i < hourly.get("time").size(); i++) {

			String time = hourly.get("time").get(i).stringValue();
			
			LocalDateTime forecastTime = LocalDateTime.parse(time);
			String displayTime = forecastTime.format(
					DateTimeFormatter.ofPattern("HH:mm")
					);
			
			if (forecastTime.equals(currentHour) || forecastTime.isAfter(currentHour)) {
				
				int rainProbability = hourly.get("precipitation_probability")
											.get(i)
											.asInt();
				
				double hourlyWindSpeed = hourly.get("wind_speed_10m")
												.get(i)
												.asDouble();
				
				forecastTimes.add(displayTime);
				rainProbabilities.add(rainProbability);
				windSpeeds.add(hourlyWindSpeed);
				
				System.out.println("現在の時間：" + time + "降水確率：" + rainProbability + "%");
				
				count++;
				
				if (count == 5) {
					break;
				}
			}
		}
		
		if(!rainProbabilities.isEmpty()) {
			model.addAttribute("rainProbability", rainProbabilities.get(0));
		}
		
		System.out.println("時刻リスト：" + forecastTimes);
		System.out.println("降水確率リスト：" + rainProbabilities);
		System.out.println("風速リスト：" + windSpeeds);
		
		model.addAttribute("forecastTimes", forecastTimes);
		model.addAttribute("rainProbabilities", rainProbabilities);
		model.addAttribute("windSpeeds", windSpeeds);
		
		model.addAttribute("temperature", temperature);
		model.addAttribute("humidity", humidity);
		model.addAttribute("precipitation", precipitation);
		model.addAttribute("windSpeed", windSpeed);
		
		System.out.println("現在の気温：" + temperature + "℃");
		
		return "weather";
	}
}
