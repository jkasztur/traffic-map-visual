package cz.muni.fi.pb138.trafficmap.utils;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import cz.muni.fi.pb138.trafficmap.models.GpsCoords;

import java.io.IOException;

public class GeocodingUtils {

	private static final String GEO_API_KEY = "AIzaSyAgFM0vs-RoDGMqK_ND7G15acMEiweAj0U";

	public static GpsCoords getCoordsFromString(String request) throws InterruptedException, ApiException, IOException {
		GeoApiContext context = new GeoApiContext().setApiKey(GEO_API_KEY);
		GeocodingResult[] results = GeocodingApi.geocode(context, request).await();
		return new GpsCoords(results[0].geometry.location.lng, results[0].geometry.location.lat);
	}
}
