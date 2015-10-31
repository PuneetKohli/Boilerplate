package com.coep.puneet.boilerplate.ParseObjects;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

/**
 * Created by Arun on 31-Oct-15.
 */
@ParseClassName("Events")
public class Events extends ParseObject
{

    public String getEventName()
    {
        return getString("event_name");
    }

    public String getEventLocation()
    {
        return getString("locationName");
    }


    public ParseGeoPoint getCoordinates() {
        return getParseGeoPoint("location");
    }

    public Date getStartDate() {
        return getDate("startDate");
    }
    public Date getEndDate() {
        return getDate("endDate");
    }

    public static ParseQuery<Events> getQuery()
    {
        return ParseQuery.getQuery(Events.class);
    }

    public static boolean isSelected;

}
