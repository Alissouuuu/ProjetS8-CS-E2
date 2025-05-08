package fr.esigelec;

public class CalculRayon {

	// Retourne la distance entre 2 communes en prenant en compte
	// leurs latitudes et leurs longitudes
	
    public double calculRayon(double lat1, double lon1, double lat2, double lon2) {
    	
        final int R = 6371; // Rayon de la Terre en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
