package detector.gen.mutante.aws.function;

import java.io.IOException;
import java.util.function.Function;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.json.JsonParseException;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import detector.gen.mutante.model.Request;
import detector.gen.mutante.service.BuscadorGenomicoService;
import detector.gen.mutante.service.BuscadorGenomicoServiceImpl;
import detector.gen.mutante.validacion.Validacion;

@SpringBootConfiguration
public class SpringCloudFunctionAwsApplication implements ApplicationContextInitializer<GenericApplicationContext> {

	private GenericApplicationContext genericApplicationContext;

	public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> startProcess() {
		return input -> {

			APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
			try {
				Request request = null;
				System.out.println(input);
				System.out.println(input.getBody());
				request = getRequest(input, request);
				boolean esValido = validarRequest(request);
				if (!esValido) {
					response.setStatusCode(HttpStatus.BAD_REQUEST.value());
					response.setBody(HttpStatus.BAD_REQUEST.getReasonPhrase());
					return response;
				}
				BuscadorGenomicoService buscadorGenomicoService = genericApplicationContext
						.getBean(BuscadorGenomicoService.class);
				boolean esMutante = buscadorGenomicoService.isMutant(request.getDna());
				if (esMutante) {
					response.setStatusCode(HttpStatus.OK.value());
					response.setBody(HttpStatus.OK.getReasonPhrase());
				} else {
					response.setStatusCode(HttpStatus.FORBIDDEN.value());
					response.setBody(HttpStatus.FORBIDDEN.getReasonPhrase());
				}
			} catch (Exception e) {
				response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				response.setBody(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			}
			return response;
		};
	}
	/**
	 * 
	 * Metodo que valida el DNA ingresado en caso de no cumplir
	 * con alguna de las validaciones no se procesara
	 * 
	 * @param request
	 * @return
	 */
	private boolean validarRequest(Request request) {
		return Validacion.validacionTamanioDimension(request.getDna())
				? !Validacion.validacionFilaVacia(request.getDna())
						? Validacion.validacionNxN(request.getDna()) ? Validacion.validacionDominio(request.getDna())
								: Boolean.FALSE
						: Boolean.FALSE
				: Boolean.FALSE;

	}

	private Request getRequest(APIGatewayProxyRequestEvent input, Request request) throws IOException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			System.out.println("paseee");
			request = objectMapper.readValue(input.getBody(), Request.class);
			System.out.println("request.dna: " + request.getDna());
			for (int i = 0; i < request.getDna().length; i++) {
				System.out.println(request.getDna()[i]);
			}

		} catch (JsonParseException e) {
			e.printStackTrace();
			throw e;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return request;
	}

	@Override
	public void initialize(GenericApplicationContext genericApplicationContext) {
		this.genericApplicationContext = genericApplicationContext;
		genericApplicationContext.registerBean(BuscadorGenomicoService.class, BuscadorGenomicoServiceImpl::new);

		genericApplicationContext.registerBean("startProcess", FunctionRegistration.class,
				() -> new FunctionRegistration<>(startProcess()).type(FunctionType
						.from(APIGatewayProxyRequestEvent.class).to(APIGatewayProxyResponseEvent.class).getType()));

	}

}
