package com.nowak01011111.damian.bunchoftools.api_client;

/**
 * Created by utche on 13.01.2017.
 */

public interface IApiResultParser<T> {
    T parseResult(String result);
}
