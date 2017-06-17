package cz.muni.fi.pb138.trafficmap.utils;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import cz.muni.fi.pb138.trafficmap.models.GpsCoords;

import java.io.IOException;

/**
 * Utility class for working with locations.
 *
 * @author jkasztur, mato5
 */
public class GeocodingUtils {

    private static final String GEO_API_KEY = "AIzaSyAgFM0vs-RoDGMqK_ND7G15acMEiweAj0U";

    private static GeoApiContext context = null;

    /**
     * Uses google search to get location from given string.
     * @param request string to search
     * @return GPS coordinates
     * @throws InterruptedException
     * @throws ApiException
     * @throws IOException
     */
    public static GpsCoords getCoordsFromString(String request) throws InterruptedException, ApiException, IOException {
        setContext();
        GeocodingResult[] results = GeocodingApi.geocode(context, request).await();
        return new GpsCoords(results[0].geometry.location.lng, results[0].geometry.location.lat);
    }

    /**
     * Returns district from the given location.
     * @param lon longitude
     * @param lat latitude
     * @return found district
     * @throws InterruptedException
     * @throws ApiException
     * @throws IOException
     */
    public static String getDistrictFromCoords(double lon, double lat) throws InterruptedException, ApiException, IOException {
        String result = null;
        setContext();
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

    /**
     * Returns region from the given location.
     * @param lon longitude
     * @param lat latitude
     * @return found region
     * @throws InterruptedException
     * @throws ApiException
     * @throws IOException
     */
    public static String getRegionFromCoords(double lon, double lat) throws InterruptedException, ApiException, IOException {
        String result = null;
        setContext();
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

    private static void setContext() {
        if(context == null) {
            context = new GeoApiContext().setApiKey(GEO_API_KEY);
        }
    }
}
