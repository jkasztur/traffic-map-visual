package cz.muni.fi.pb138.trafficmap.utils;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import cz.muni.fi.pb138.trafficmap.models.GpsCoords;

import java.io.IOException;

public class GeocodingUtils {

    private static final String GEO_API_KEY = "AIzaSyAgFM0vs-RoDGMqK_ND7G15acMEiweAj0U";

    public static GpsCoords getCoordsFromString(String request) throws InterruptedException, ApiException, IOException {
        GeoApiContext context = new GeoApiContext().setApiKey(GEO_API_KEY);
        GeocodingResult[] results = GeocodingApi.geocode(context, request).await();
        return new GpsCoords(results[0].geometry.location.lng, results[0].geometry.location.lat);
    }

    public static String getDistrictFromCoords(double lon, double lat) throws InterruptedException, ApiException, IOException {
        String result = null;
        GeoApiContext context = new GeoApiContext().setApiKey(GEO_API_KEY);
        GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(lat, lon)).language("cs").await();
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < results[i].addressComponents.length; j++) {
                for (int k = 0; k < results[i].addressComponents[j].types.length; k++) {

                    if (results[i].addressComponents[j].types[k] == AddressComponentType.valueOf("ADMINISTRATIVE_AREA_LEVEL_2")) {
                        result = results[i].addressComponents[j].longName;
                    }
                }
            }
        }
        return result;
    }

    public static String getRegionFromCoords(double lon, double lat) throws InterruptedException, ApiException, IOException {
        String result = null;
        GeoApiContext context = new GeoApiContext().setApiKey(GEO_API_KEY);
        GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(lat, lon)).language("cs").await();
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < results[i].addressComponents.length; j++) {
                for (int k = 0; k < results[i].addressComponents[j].types.length; k++) {

                    if (results[i].addressComponents[j].types[k] == AddressComponentType.valueOf("ADMINISTRATIVE_AREA_LEVEL_1")) {
                        result = results[i].addressComponents[j].longName;
                    }
                }
            }
        }
        return result;
    }


}
