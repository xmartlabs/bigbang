package com.xmartlabs.base.retrofit.exception;

import com.google.gson.JsonParseException;

/** Exception thrown when a required field does not have a value when deserialized with Gson */
public class JsonRequiredFieldException extends JsonParseException {
  public JsonRequiredFieldException(String msg) {
    super(msg);
  }
}
