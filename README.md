# backend-rest-swagger-buffer-log
This is a simple Java REST API example intended to run with Wildfly. It has Openapi 3.0 documentation (Swagger) and a HttpServlet(Request/Response) buffer implementation.

# OpenAPI 3.0 specification (formerly Swagger specification)
Provides a OpenAPI 3.0 specification written in YAML or JSON calling the following URL, respectivily:
> http[s]://host/context/base_application_path_for_all_rest_resources/openapi.yaml
> http[s]://host/context/base_application_path_for_all_rest_resources/openapi.json

# Swagger UI
Single page application that exposes API doumentation and provides a test suite based on openapi specification.

# HttpServlet(Request/Response) buffer
The need of a HttpServlet(Request/Response) is to log the inputstream and outputstream, after or before parsing time, respectivly. The inputstream of a HttpServletRequest and the outputstream of a HttpServletResponse can only be consumed once. If we try to consum inputstream after parser, it's gonna be empty. If we try to consum outputstream before parser, there will be no data to parse. Using a HttpServlet(Request/Response) buffer implementation, it's possible to log request's inputstream  and response's outputstream in the middle of the process.
