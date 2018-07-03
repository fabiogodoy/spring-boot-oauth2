package br.com.fabiogodoy.oauth2.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api-v1")
public class PrincipalResource {

    @RequestMapping(method = RequestMethod.POST, path="/principal")
    public Principal oauth(Principal principal) {
        return principal;
    }
}
