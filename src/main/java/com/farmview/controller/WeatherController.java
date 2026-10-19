package com.farmview.controller;

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
		
		JsonNode hourly = json.get("hourly");
		
		
		for (int i = 0; i < 5; i++) {

			String time = hourly.get("time").get(i).stringValue();
		
			int rainProbability = hourly.get("precipitation_probability")
										.get(i)
										.asInt();
		
			System.out.println(time + "降水確率：" + rainProbability + "％");
		}
		
		
		model.addAttribute("temperature", temperature);
		model.addAttribute("humidity", humidity);
		model.addAttribute("precipitation", precipitation);
		model.addAttribute("windSpeed", windSpeed);
		
		System.out.println("現在の気温：" + temperature + "℃");
		
		return "weather";
	}
}
