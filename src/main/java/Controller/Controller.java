package Controller;

import Response.Response;

public interface Controller {
    Response invoke() throws Exception;
}