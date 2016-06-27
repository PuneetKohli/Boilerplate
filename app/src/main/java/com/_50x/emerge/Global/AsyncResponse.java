package com._50x.emerge.Global;

/**
 * Created by Puneet on 1/30/2015.
 * A simple interface to be used when any event processing is finished
 */
public interface AsyncResponse
{
    void processFinish(String result, int type);
}
