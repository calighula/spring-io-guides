package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "user", description = "Rest API for user operations", tags = "User API")
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/greeting")
	@ApiOperation(value = "Display greeting message to non-admin user", response = Greeting.class)
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "OK"),
	            @ApiResponse(code = 404, message = "The resource not found")
	    }
	)
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
	}
	
}
