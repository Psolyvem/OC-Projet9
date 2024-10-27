package com.medilabo.webclient.config;

import feign.Response;
import feign.codec.ErrorDecoder;

import java.util.NoSuchElementException;

public class FeignErrorDecoder implements ErrorDecoder
{
	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String s, Response response)
	{
		switch (response.status())
		{
			case 404 :
				return new NoSuchElementException("Not found");
			case 400 :
				return new IllegalArgumentException("Bad Request");
		}
		return defaultErrorDecoder.decode(s, response);
	}
}
