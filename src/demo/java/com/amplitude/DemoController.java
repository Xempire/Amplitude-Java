package com.amplitude;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amplitude.Amplitude;

@RestController
public class DemoController {
	@RequestMapping("/")
	public String index() {
		Amplitude amplitude = Amplitude.getInstance("INSTANCE_NAME");
		amplitude.init("8e07b9d451a7d07bd33f6e9ba5870f21");
		amplitude.logEvent(new Event("Test Event", "test_user_id"));
		final Identification identification = new Identification("test_user_id");
		identification.set = new JSONObject().put("test user property", "test value");
		amplitude.identify(identification);
		amplitude.setLogMode(AmplitudeLog.LogMode.DEBUG);
		return "Amplitude Java SDK Demo: sending test event.";
	}
}
